package Model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

import Controller.Interfaces.iShop;
import Model.FabricToys.ToyFabric;
import Model.Toys.Toy;

public class Shop implements iShop {
    private List<ToyFabric> toyFabrics = new ArrayList<>();
    private PriorityQueue<Toy> queueToys = new PriorityQueue<>();

    private static Random rand;

    static {
        rand = new Random();
    }

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
        return copiesNames + "\n";
    }

    @Override
    public boolean createToy(int criteria, int probability) {
        if (!toyFabrics.isEmpty()) {
            PriorityQueue<Toy> newQueueToys = new PriorityQueue<>();
            int countCopies = 0;
            if (!queueToys.isEmpty()) {
                if (countCopies < probability) {
                    deleteToys(toyFabrics.get(criteria).getNameCopies());
                    Toy.resetCount();
                }
                for (Toy toy : queueToys) {
                    newQueueToys.add(toy);
                    String countCopiesToys = getCountCopiesToys(toyFabrics.get(criteria).getNameCopies());
                    if (!countCopiesToys.isEmpty()) {
                        String[] countCopiesToysArray = countCopiesToys.split(" = ");
                        try {
                            countCopies = Integer.parseInt(countCopiesToysArray[1]);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        if (probability > countCopies) {
                            probability -= countCopies;
                        }
                    }
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
            int countToys = 0;
            String bestMatchName = new String();
            for (Toy toy : queueToys) {
                if (toyName.toLowerCase().equals(toy.getName().toLowerCase())) {
                    bestMatchName = toy.getName();
                    countToys++;
                }
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
                char[] toyNameArray = copiesName.toLowerCase().toCharArray();
                int index = 0;
                String toyName = toy.getName().toLowerCase();
                for (int i = 0; i <= toyName.length(); i++) {
                    if (index < toyNameArray.length) {
                        // String a = Character.toString(toyName.charAt(i));
                        if ((i != toyName.length() && toyNameArray[index] == toyName.charAt(i))) {
                            index++;
                        } else if (index != 0)
                            break;
                    } else {
                        assortiment.add(toy.getName());
                        break;
                    }
                }

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
    public String get(String nameToy) {
        int chance = rand.nextInt(0, 100);
        String countryCopies = getCountCopiesToys(nameToy);
        if (!countryCopies.isEmpty()) {
            String[] countryToys = countryCopies.split(" = ");
            try {
                int countElement = Integer.parseInt(countryToys[1]);
                if (countElement >= chance) {
                    PriorityQueue<Toy> newQueueToys = queueToys;
                    for (Toy toy : queueToys) {
                        if (toy.getName().equals(nameToy)) {
                            newQueueToys.remove(toy);
                            setQueueToys(newQueueToys);
                            return toy.toString();
                        }
                    }
                    return new String();
                } else
                    return new String();
            } catch (Exception e) {
                return e.getMessage();
            }
        } else
            return "-1";
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

    private void deleteToys(String toyName) {
        PriorityQueue<Toy> newQueueToys = new PriorityQueue<>(queueToys);
        for (Toy toy : queueToys) {
            if (toyName.equals(toy.getName())) {
                newQueueToys.remove(toy);
            }
        }
        setQueueToys(newQueueToys);
    }

    private boolean convertToNumber(Character number) {
        return Character.isDigit(number);
    }
}
