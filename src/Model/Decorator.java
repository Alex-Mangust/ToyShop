package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import Controller.Interfaces.iShop;
import Model.FabricToys.ToyFabric;
import Model.FabricToys.CopiesFabricToys.TaddyBearFabric;
import Model.Toys.Toy;

public class Decorator implements iShop {

    private iShop shop;
    private String pathFile;
    private PriorityQueue<String> queueToysName = new PriorityQueue<>();

    private static Random rand;

    static {
        rand = new Random();
    }

    public Decorator(iShop shop, String pathFile) {
        this.shop = shop;
        this.pathFile = pathFile;
        List<String> toysInFile = readFile(pathFile);
        if (!toysInFile.isEmpty()) {
            PriorityQueue<Toy> temporaryQueuq = new PriorityQueue<>();
            temporaryQueuq.add(new TaddyBearFabric().createToy());
            setQueueToys(temporaryQueuq);
        }
    }

    @Override
    public List<ToyFabric> getToyFabrics() {
        return shop.getToyFabrics();
    }

    @Override
    public String toyFabrics() {
        return shop.toyFabrics();
    }

    @Override
    public boolean createToy(int criteria, int probability) {
        boolean create = shop.createToy(criteria, probability);
        PriorityQueue<String> newQueueToys = new PriorityQueue<>();
        String recordInFile = new String();
        boolean doubleCopies = true, successfulReplacement = false;
        List<String> replacemeent = new ArrayList<>();
        if (create) {
            for (Toy toy : shop.getQueueToys()) {
                List<String> toysInFile = readFile(pathFile);
                String record = shop.getCountCopiesToys(toy.getName());
                String[] recordArray = record.split(" = ");
                recordInFile = String.format("%s - %s%%", toy.toString(), recordArray[1]);
                if (!toysInFile.isEmpty()) {
                    List<String> nameToyList = new ArrayList<>();
                    for (String line : toysInFile) {
                        String[] toyLine = line.split("Наименование: ");
                        String[] toyNameLine = toyLine[1].split(" - ");
                        nameToyList.add(toyNameLine[0]);
                    }
                    if (!nameToyList.contains(toy.getName())) {
                        newQueueToys.add(recordInFile);
                        writeFile(newQueueToys, pathFile);
                        doubleCopies = false;
                    }
                } else {
                    newQueueToys.add(recordInFile);
                    writeFile(newQueueToys, pathFile);
                    doubleCopies = false;
                }
                if (doubleCopies) {
                    for (int i = 0; i < toysInFile.size(); i++) {
                        if (!toysInFile.get(i).equals(recordInFile)) {
                            String[] toyLine = toysInFile.get(i).split(" - ");
                            String[] newToyLine = recordInFile.split(" - ");
                            if (toyLine[0].equals(newToyLine[0])) {
                                if (!toyLine[1].equals(newToyLine[1])) {
                                    toysInFile.set(i, recordInFile);
                                    successfulReplacement = true;
                                }
                            }
                        }
                    }
                }
                if (successfulReplacement && replacemeent.isEmpty()) {
                    replacemeent = toysInFile;
                }
            }
            if (successfulReplacement) {
                for (String recordToy : replacemeent) {
                    newQueueToys.add(recordToy);
                }
                rewriteFile(newQueueToys, pathFile);
            }
        }

        List<String> toysInFile = readFile(pathFile);
        int index = 0;
        for (int i = 0; i < toysInFile.size(); i++) {
            String newLine = new String();
            String[] lineArray = toysInFile.get(i).split("Наименование: ");
            for (int j = 0; j < lineArray[0].length(); j++) {
                if (convertToNumber(lineArray[0].charAt(j))) {
                    newLine += index;
                    index++;
                } else {
                    newLine += lineArray[0].charAt(j);
                }
            }
            newLine += "Наименование: " + lineArray[1];
            toysInFile.set(i, newLine);
        }
        newQueueToys = new PriorityQueue<>();
        for (String record : toysInFile) {
            newQueueToys.add(record);
        }
        rewriteFile(newQueueToys, pathFile);
        return create;
    }

    @Override
    public PriorityQueue<Toy> getQueueToys() {
        return shop.getQueueToys();
    }

    @Override
    public void setQueueToys(PriorityQueue<Toy> toys) {
        shop.setQueueToys(toys);
    }

    @Override
    public String getCountCopiesToys(String toyName) {
        List<String> toysInFile = readFile(pathFile);
        for (String line : toysInFile) {
            String[] lineArray = line.split("Наименование: ");
            String[] lineToy = lineArray[1].split(" - ");
            if (lineToy[0].equals(toyName)) {
                return String.format("%s = %s", lineToy[0], lineToy[1]);
            }
        }

        return new String();
    }

    @Override
    public String assortiment() {
        List<String> toysInFile = readFile(pathFile);
        String assortiment = new String();
        for (String line : toysInFile) {
            assortiment += line.substring(0, line.length() - 5);
            assortiment += "\n";
        }
        assortiment = assortiment.substring(0, assortiment.length() - 1);
        return assortiment;
    }

    @Override
    public String assortiment(String copiesName) {
        List<String> toysInFile = readFile(pathFile);
        List<String> queueToys = new ArrayList<>();
        List<String> assortiment = new ArrayList<>();
        for (String line : toysInFile) {
            String[] lineArray = line.split("Наименование: ");
            String[] toyName = lineArray[1].split(" - ");
            queueToys.add(toyName[0]);
        }
        for (String name : queueToys) {
            if (!assortiment.contains(name)) {
                char[] toyNameArray = copiesName.toLowerCase().toCharArray();
                int index = 0;
                String toyName = name.toLowerCase();
                for (int i = 0; i <= toyName.length(); i++) {
                    if (index < toyNameArray.length) {
                        if ((i != toyName.length() && toyNameArray[index] == toyName.charAt(i))) {
                            index++;
                        } else if (index != 0)
                            break;
                    } else {
                        assortiment.add(name);
                        break;
                    }
                }

            }
        }
        String assortimentStr = new String();
        int newId = 0;
        for (String name : assortiment) {
            String recordToy = String.format("Id: %d Наименование: %s", newId, name);
            newId++;
            assortimentStr += recordToy + "\n";
        }
        return assortimentStr;
    }

    @Override
    public String get(String nameToy) {
        int chance = rand.nextInt(0, 100);
        String countryCopies = getCountCopiesToys(nameToy);
        List<String> toysInFile = readFile(pathFile);
        if (!countryCopies.isEmpty()) {
            String[] countryToys = countryCopies.split(" = ");
            try {
                int countElement = Integer.parseInt(countryToys[1].replace("%", ""));
                if (countElement >= chance) {
                    for (int i = 0; i < toysInFile.size(); i++) {
                        String[] line = toysInFile.get(i).split("Наименование: ");
                        String[] lineToy = line[1].split(" - ");
                        if (lineToy[0].equals(nameToy)) {
                            lineToy[1] = Integer.toString(countElement - 1);
                            line[1] = lineToy[0] + " - " + lineToy[1];
                            String newRecordToy = line[0] + "Наименование: " + line[1];
                            toysInFile.set(i, newRecordToy);
                        }
                    }
                    PriorityQueue<String> newQueueToys = new PriorityQueue<>();
                    for (String line : toysInFile) {
                        newQueueToys.add(line);
                    }
                    rewriteFile(newQueueToys, pathFile);
                    List<String> winnignFile = readFile("winning.txt");
                    PriorityQueue<String> winningQueue = new PriorityQueue<>();
                    int id = 0;
                    if (winnignFile.isEmpty()) {
                        winningQueue.add("Дата: " + dataWinnings() + "\nСегодняшний выигрышь:\n");
                        writeFile(winningQueue, "winning.txt");
                        winningQueue = new PriorityQueue<>();
                    } else {
                        List<String> dateInFile = convertToDateFile(winnignFile);
                        List<String> firstLineInFile = new ArrayList<>();
                        for (String date : dateInFile) {
                            firstLineInFile.add("Дата: " + date);
                        }
                        if (!firstLineInFile.contains("Дата: " + dataWinnings())) {
                            winningQueue.add("Дата: " + dataWinnings() + "\n\nСегодняшний выигрышь:\n");
                            writeFile(winningQueue, "winning.txt");
                            winningQueue = new PriorityQueue<>();
                        }
                        if (!winnignFile.isEmpty()) {
                            for (String line : winnignFile) {
                                String[] lineArray = line.split("  Наименование:");
                                String[] fileId = lineArray[0].split(": ");
                                try {
                                    id = Integer.parseInt(fileId[1]) + 1;
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }

                        }
                    }
                    String winning = String.format("№: %s  Наименование: %s", id, nameToy);
                    winningQueue.add(winning);
                    writeFile(winningQueue, "winning.txt");
                    return winning;
                } else
                    return new String();
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "-1";
    }

    private void rewriteFile(PriorityQueue<String> getQueueToys, String path) {
        try (FileWriter fw = new FileWriter(path, false)) {
            for (String toy : getQueueToys) {
                fw.write(toy.toString());
                fw.append('\n');
            }
            fw.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void writeFile(PriorityQueue<String> getQueueToys, String path) {
        if (getQueueToys.isEmpty()) {
            System.out.println("Очередь пуста, нет данных для записи в файл.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
            for (String toy : getQueueToys) {
                writer.write(toy);
                writer.newLine(); // Добавляем перевод строки после каждой записи
            }
            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private List<String> readFile(String path) {
        List<String> toys = new ArrayList<>();
        try (FileReader notesReader = new FileReader(path);) {
            try (BufferedReader reader = new BufferedReader(notesReader)) {
                String line = reader.readLine();
                Toy.resetCount();
                while (line != null) {
                    toys.add(line);
                    line = reader.readLine();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return toys;
    }

    private boolean convertToNumber(Character number) {
        return Character.isDigit(number);
    }

    private String dataWinnings() {
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yy");
        return format.format(new Date());
    }

    private List<String> convertToDateFile(List<String> lineFile) {
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yy");
        List<String> dateInFile = new ArrayList<>();
        for (String line : lineFile) {
            try {
                String[] date = line.split(": ");
                Date newDate = format.parse(date[1]);
                dateInFile.add(format.format(newDate));
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return dateInFile;
    }

}
