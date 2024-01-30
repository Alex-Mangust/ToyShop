package Model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Controller.Interfaces.iShop;
import Model.FabricToys.ToyFabric;
import Model.Toys.Toy;

public class Shop implements iShop {
    private List<ToyFabric> toyFabrics = new ArrayList<>();
    private PriorityQueue<Toy> queueToys = new PriorityQueue<>();

    public Shop() throws ClassNotFoundException {
        List<String> listFabricFile = ListFabricFile();
        if (!listFabricFile.isEmpty()) {
            for (String fabricFileName : listFabricFile) {
                try {
                    Class<?> toyFabricFile = Class.forName(fabricFileName);
                    Constructor<?> constructor;
                    try {
                        constructor = toyFabricFile.getConstructor();
                        try {
                            Object toyFabric = constructor.newInstance();
                            if (toyFabric instanceof ToyFabric) {
                                toyFabrics.add((ToyFabric) toyFabric);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    } catch (NoSuchMethodException | SecurityException e) {
                        System.out.println(e.getMessage());
                    }
                } catch (ClassNotFoundException e) {
                    throw new ClassNotFoundException();
                }
            }
        }
    }

    @Override
    public List<ToyFabric> getToyFabrics() {
        return toyFabrics;
    }

    @Override
    public String toyFabrics() {
        int number = 1;
        String copiesNames = new String();
        for (ToyFabric fabric : toyFabrics) {
            copiesNames += String.format("%d - %s;  ", number, fabric.getNameCopies());
            if (number % 5 == 0)
                copiesNames += "\n";
            number++;
        }
        return copiesNames;
    }

    @Override
    public boolean createToy(int criteria, int probability) {
        if (!toyFabrics.isEmpty()) {
            PriorityQueue<Toy> newQueueToys = new PriorityQueue<>();
            if (probability > 100 - queueToys.size()) {
                for (int i = 0; i < probability; i++) {
                    Toy.resetCount();
                    queueToys.poll();
                }
            }
            if (!queueToys.isEmpty()) {
                for (Toy toy : queueToys) {
                    newQueueToys.add(toy);
                }
            }
            while (probability > 0) {
                if (criteria >= 0 && criteria < toyFabrics.size())
                    newQueueToys.add(toyFabrics.get(criteria).createToy());
                else
                    newQueueToys.add(toyFabrics.get(toyFabrics.size() - 1).createToy());
                probability--;
            }
            queueToys = newQueueToys;
            return true;
        } else
            return false;
        // System.out.println("У вас нет моделий для добавления игрушек! Пожалуйста
        // убедитесь, что путь к моделям игрушек указан верно!");
    }

    @Override
    public PriorityQueue<Toy> getQueueToys() {
        return queueToys;
    }

    @Override
    public void setQueueToys(PriorityQueue<Toy> toys) {
        queueToys = toys;
    }

    @Override
    public String getCountCopiesToys(String toyName) {
        if (!toyName.isEmpty()) {
            boolean find = false;
            int countToys = 0;
            String bestMatchName = new String();
            for (Toy toy : queueToys) {
                if (toyName.toLowerCase().equals(toy.getName().toLowerCase())) {
                    bestMatchName = toy.getName();
                    countToys++;
                    find = true;
                }
            }
            if (!find) {
                char[] toyNameArray = toyName.toLowerCase().toCharArray();
                int countMatch;
                int bestMatch = 0;
                for (Toy toy : queueToys) {
                    if (!toy.getName().equals(bestMatchName)) {
                        countMatch = 0;
                        char[] copiesNameArray = toy.getName().toLowerCase().toCharArray();
                        int index = 0;
                        for (int i = 0; i < copiesNameArray.length; i++) {
                            if (index < toyNameArray.length) {
                                if (toyNameArray[index] == copiesNameArray[i]) {
                                    countMatch++;
                                    index++;
                                } else {
                                    index = 0;
                                    if (bestMatch < countMatch) {
                                        bestMatch = countMatch;
                                    }
                                }
                            } else {
                                break;
                            }
                        }

                        if (bestMatch < countMatch) {
                            bestMatch = countMatch;
                            bestMatchName = toy.getName();
                        }
                    }
                }
                if (countToys == 0)
                    return getCountCopiesToys(bestMatchName);

            }
            return String.format("%s = %d", bestMatchName, countToys);
        }
        return new String();
    }

    @Override
    public String assortiment() {
        List<String> assortiment = new ArrayList<>();
        for (Toy toy : getQueueToys()) {
            if (!assortiment.contains(toy.getName())) {
                assortiment.add(toy.getName());
            }
        }
        String assortimentStr = new String();
        int newId = 0;
        for (String name : assortiment) {
            for (Toy toy : getQueueToys()) {
                if (name.equals(toy.getName())) {
                    String recordToy = resetId(toy, newId);
                    newId++;
                    assortimentStr += recordToy + "\n";
                    break;
                }
            }
        }
        return assortimentStr;
    }

    @Override
    public String assortiment(String copiesName) {
        List<String> assortiment = new ArrayList<>();
        for (Toy toy : getQueueToys()) {
            if (!assortiment.contains(toy.getName())) {
                if (toy.getName().toLowerCase().equals(copiesName.toLowerCase()))
                    assortiment.add(toy.getName());
            }
        }
        String assortimentStr = new String();
        int newId = 0;
        for (String name : assortiment) {
            for (Toy toy : getQueueToys()) {
                if (name.equals(toy.getName())) {
                    String recordToy = resetId(toy, newId);
                    newId++;
                    assortimentStr += recordToy + "\n";
                    break;
                }
            }
        }
        if (assortiment.isEmpty()) {
            if (!copiesName.isEmpty()) {
                String[] copiesNameArray = copiesName.split(" = ");
                return assortiment(copiesNameArray[0]);
            }
        }
        return assortimentStr;
    }

    private String resetId(Toy toy, int id) {
        boolean findNumber = false;
        String recordToy = new String();
        for (int i = 0; i < toy.toString().length(); i++) {
            if (convertToNumber(toy.toString().charAt(i))) {
                if (!findNumber) {
                    recordToy += id;
                    id++;
                    findNumber = true;
                }
            } else {
                recordToy += toy.toString().charAt(i);
            }
        }
        return recordToy;
    }

    private List<String> ListFabricFile() {
        try {
            String path = "src\\Model\\FabricToys\\CopiesFabricToys";
            String fullName = "Model.FabricToys.CopiesFabricToys.";
            File folder = new File(path);
            List<String> fileNames = new ArrayList<>();
            if (folder.isDirectory()) {
                File[] files = folder.listFiles();
                if (files != null) {
                    for (File file : files) {
                        String className = fullName + file.getName().replace(".java", "");
                        fileNames.add(className);
                    }
                }
            }
            return fileNames;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    private boolean convertToNumber(Character number) {
        return Character.isDigit(number);
    }
}
