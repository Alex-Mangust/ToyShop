package Model;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import Model.FabricToys.ToyFabric;
import Model.Toys.Toy;

public class Shop {
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

    public String getToyFabrics() {
        int number = 1;
        String copiesNames = new String();
        for (ToyFabric fabric : toyFabrics) {
            copiesNames += String.format("%d - %s;  ", number, fabric.getNameCopies());
            if (number % 5 == 0) copiesNames += "\n";
            number++;
        }
        return copiesNames;
    }

    public boolean createToy(int criteria, int probability) {
        if (!toyFabrics.isEmpty()) {
            PriorityQueue<Toy> newQueueToys = new PriorityQueue<>();
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
        } else return false; 
        // System.out.println("У вас нет моделий для добавления игрушек! Пожалуйста убедитесь, что путь к моделям игрушек указан верно!");
    }

    public PriorityQueue<Toy> getQueueToys() {
        return queueToys;
    }

    public String getCountCopiesToys(String toyName) {
        boolean find = false;
        int countToys = 0;
        String bestMatchName = new String();
        for (Toy toy : queueToys) {
            if (toyName == toy.getName()) {
                bestMatchName = toy.getName();
                countToys++;
                find = true;
            }
        }
        if (!find) {
            char[] toyNameArray = toyName.toCharArray();
            int countMatch;
            int bestMatch = 0;
            for (Toy toy : queueToys) {
                countMatch = 0;
                char[] copiesNameArray = toy.getName().toCharArray();
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
                    bestMatchName = toy.getName();
                }
            }
            if (countToys == 0) return getCountCopiesToys(bestMatchName);
        }
        return String.format("%s-%d", bestMatchName, countToys);
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
}
