package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

/** Класс, описывающий поведение декоратора, который расширяет функциональность класса Shop. Имеет интерфейс iShop */
public class Decorator implements iShop {

    private iShop shop; // Экземпляр класса shop
    private String pathFile; // Поле для хранения пути к файлу, где хранится ассортимент игрушек

    private static Random rand; // Статическое поле, необходимое для создания рандомного числа при розыгрыше

    static {
        rand = new Random();
    }

    /**
     * Конструктор класса
     * @param shop - экземпляр класса shop
     * @param pathFile - Путь к файлу, где храниться ассортимент игрушек
     */
    public Decorator(iShop shop, String pathFile) {
        this.shop = shop;
        this.pathFile = pathFile;
        List<String> toysInFile = readFile(pathFile); // Список, в котором храняться записи из файла ассортимента
        if (!toysInFile.isEmpty()) {
            PriorityQueue<Toy> temporaryQueuq = new PriorityQueue<>(); // Создается временная коллекция
            temporaryQueuq.add(new TaddyBearFabric().createToy()); // В коллекцию добавляется игрушка
            setQueueToys(temporaryQueuq); // Текущая коллекция заменяется на новую
            Toy.resetCount();
        }
    }

    /**
     * Переопределенный метод, необходимый для получения списка со всеми моделями
     * фабрик по созданию игрушек
     */
    @Override
    public List<ToyFabric> getToyFabrics() {
        return shop.getToyFabrics();
    }

    /**
     * Переопределенный метод, необходимый для получения списка со всеми моделями
     * фабрик по созданию игрушек в текстовом виде
     */
    @Override
    public String toyFabrics() {
        return shop.toyFabrics();
    }

    /**
     * Переопределенный метод, необходимый для создания игрушки
     * 
     * @param criteria  - номер фабрики по созданию игрушки
     * @param probality - вес (вероятность выпадения) игрушки
     */
    @Override
    public boolean createToy(int criteria, int probability) {
        shop.setQueueToys(new PriorityQueue<Toy>());
        boolean create = shop.createToy(criteria, probability); // Переменная, в которую записывается результат удалось ли добавить игрушку
        PriorityQueue<String> newQueueToys = new PriorityQueue<>(); // Создается новая коллекция
        String recordInFile = new String(); // Переменная для хранения записей в файл
        boolean doubleCopies = true, successfulReplacement = false; // Переменные, обозначающие - найденную копию / удачную замену
        List<String> replacement = new ArrayList<>(); // Список для хранения новой информации об игрушках, которая заменить данные в файле
        if (create) {
            for (Toy toy : shop.getQueueToys()) { // Цикл проходит по всей коллекции с игрушками
                List<String> toysInFile = readFile(pathFile); // Список, в котором хранятся данные из файла ассортимента
                String record = shop.getProbabilityCopiesToys(toy.getName()); // Переменная, в которой хранится вероятность выпадения рассматриваемой игрушки в виде строки "название игрушки = вероятность"
                String[] recordArray = record.split(" = "); // Строка record делится на массив. В качестве разделителя служит " = "
                recordInFile = String.format("%s - %s%%", toy.toString(), recordArray[1]); //В переменную recordInFile записывается информация о рассматриваемой игрушки и вероятность ее выпадения
                if (!toysInFile.isEmpty()) { // Проверяется, что список toysInFile не пуст
                    List<String> nameToyList = new ArrayList<>(); // Создается список для хранения названий игрушек
                    for (String line : toysInFile) { // Цикл проходит по всему списку с данными из файла
                        String[] toyLine = line.split("Наименование: "); // Создается массив. В качестве разделителя "Наименование: " Таким образом первый элемент хранит информацию об id, а второй о названии игрушки и вероятности ее выпадения
                        String[] toyNameLine = toyLine[1].split(" - "); // Создается массив. В качестве разделителя " - " Таким образом первый элемент хранит информцию об названии игрушки, а второй элемент о вероятности ее выпадения
                        nameToyList.add(toyNameLine[0]); // В список добавляется название игрушки
                    }
                    if (!nameToyList.contains(toy.getName())) { // Проверяется, не содержит ли список nameToyList название рассматриваемой игрушки
                        newQueueToys.add(recordInFile); // В коллекцию добавляется новая запись об игрушке
                        writeFile(newQueueToys, pathFile); // В файл записывается новая запись
                        doubleCopies = false; // Переменная, определяющая найденную копию, становится false
                    }
                } else { // Если список toysInFile пуст
                    newQueueToys.add(recordInFile); // В коллекцию добавляется новая запись об игрушке
                    writeFile(newQueueToys, pathFile); // В файл записывается новая запись
                    doubleCopies = false; // Переменная, определяющая найденную копию, становится false
                }
                if (doubleCopies) { // Проверяется, что в файле есть записи о текущей игрушке 
                    for (int i = 0; i < toysInFile.size(); i++) { // Цикл проходит по всему списку с данными из файла
                        if (!toysInFile.get(i).equals(recordInFile)) { // Проверяется, что текущий элемент не соответствует новой записи об игрушке
                            String[] toyLine = toysInFile.get(i).split(" - "); // Создается массив из текущего элемента. В качестве разделителя служит " - "
                            String[] newToyLine = recordInFile.split(" - "); // Создается массив из новой записи об игрушки. В качестве разделителя служит " - "
                            if (toyLine[0].equals(newToyLine[0])) { // Проверяется, что первый элемент массива toyLine, соответствует первому элементу массива newToyLine 
                                if (!toyLine[1].equals(newToyLine[1])) { // Проверяется, что второй элемент массива toyLine, не соответствует второму элементу массива newToyLine (второй элемент - вероятность выпадения)
                                    toysInFile.set(i, recordInFile); // Запись об игрушке в списке с данными из файла заменяется на новую
                                    successfulReplacement = true; // Переменная, определяющая удачную замену, становится true
                                }
                            }
                        }
                    }
                }
                if (successfulReplacement && replacement.isEmpty()) { // Проверяется, что замена произошла и список replacement пуст
                    replacement = toysInFile; // Список replacement получает все значения из toysInFile
                }
            }
            if (successfulReplacement) { // Проверяется, что замена произошла
                for (String recordToy : replacement) { // В новую коллекцию записываются значения из списка replacement
                    newQueueToys.add(recordToy);
                }
                rewriteFile(newQueueToys, pathFile); // Происходит перезапись файла
            }
        }

        List<String> toysInFile = readFile(pathFile); // Список, который хранит в себе данные из файла
        int index = 0; // Переменная с новым индексом
        for (int i = 0; i < toysInFile.size(); i++) { // Цикл проходит по всему списку с данными из файла
            String newLine = new String(); // Переменная, которая будет хранить измененную информацию об игрушке
            String[] lineArray = toysInFile.get(i).split("Наименование: "); // Создается массив строк. В качестве разделителя служит "Наименование: ";
            for (int j = 0; j < lineArray[0].length(); j++) { // Цикл проходит по длине текущей строки
                if (convertToNumber(lineArray[0].charAt(j))) { // Проверяется, что символ может быть преобразован в число
                    newLine += index; // В newLine, вместо текущего символа строки, записывается новое id
                    index++;
                } else { // Если символ не может быть преобразован в число, он записывается в newLine
                    newLine += lineArray[0].charAt(j);
                }
            }
            newLine += "Наименование: " + lineArray[1]; // Запись об игрушке возвращает первоначальный вид, но с новым id
            toysInFile.set(i, newLine); // Текущий элемент заменяется на newLine
        }
        newQueueToys = new PriorityQueue<>(); // Обновляется коллекция newQueueToys
        for (String record : toysInFile) { // В коллекцию записываются значения из списка toysInFile
            newQueueToys.add(record);
        }
        rewriteFile(newQueueToys, pathFile); // Происходит перезапись файла
        return create;
    }

    /** Переопределенный метод, необходимый для получения коллекция, в которой хранятся экземпляры игрушек */
    @Override
    public PriorityQueue<Toy> getQueueToys() {
        return shop.getQueueToys();
    }

    /**
     * Переопределенный метод, необходимый для изменения текущей коллекции, в которой хранятся экземпляры игрушек
     * @param toys - новая коллекция
     * */
    @Override
    public void setQueueToys(PriorityQueue<Toy> toys) {
        shop.setQueueToys(toys);
    }

    /**
     * Переопределенный метод, необходимый для получения текущей вероятности выпадения игрушки
     * @param toyName - название игрушки
     */
    @Override
    public String getProbabilityCopiesToys(String toyName) {
        List<String> toysInFile = readFile(pathFile); // Список, в котором хранятся данные из файла ассортимента
        for (String line : toysInFile) { // Цикл проходит по всему списку с данными из файла
            String[] lineArray = line.split("Наименование: "); // Из текущей строки создается массив из строк. В качестве разделителя служит "Наименование: "
            String[] lineToy = lineArray[1].split(" - "); // Из второго элемента массива lineArray создается еще один массив строк. В качестве разделителя служит " - ".
            if (lineToy[0].equals(toyName)) { // Проверяется, что первый элемент lineToy (название рассматриваемой игрушки) соответствует названию игрушки, вероятность выпадения которой необходимо рассчитать
                return String.format("%s = %s", lineToy[0], lineToy[1]);
            }
        }
        return new String();
    }

    /** Переопределенный метод, необходимый для получения всего текущего ассортимента */
    @Override
    public String assortiment() {
        List<String> toysInFile = readFile(pathFile); // Список, в котором хранятся данные из файла ассортимента
        String assortiment = new String(); // Строка, в которую будет записан ассортимент игрушек
        for (String line : toysInFile) { // Цикл проходит по всему списку с данными из файла
            String[] lineArray = line.split(" - "); // Из текущей строки создается массив из строк. В качестве разделителя служит " - "
            assortiment += lineArray[0]; // В ассортимент записывается запись о рассматриваемой игрушки без вероятности выпадения
            assortiment += "\n";
        }
        assortiment = assortiment.substring(0, assortiment.length() - 1); // Из строки assortiment удаляется последний символ ("\n")
        return assortiment;
    }

    /**
     * Переопределенный метод, необходимый для получения ассортимента игрушек, которые соответсвуют ключевому слову, введенному пользователем
     * @param copiesName - название игрушки/ключевое слово
     */
    @Override
    public String assortiment(String copiesName) {
        List<String> toysInFile = readFile(pathFile); // Список, в котором хранятся данные из файла ассортимента
        List<String> namesToys = new ArrayList<>(); // Список для хранения названий всех игрушек
        List<String> assortiment = new ArrayList<>(); // Список для хранения названий игрушек, подходящий под запрос
        for (String line : toysInFile) { // Цикл проходит по всему списку с данными из файла
            String[] lineArray = line.split("Наименование: "); // Из текущей строки создается массив из строк. В качестве разделителя служит "Наименование: "
            String[] toyName = lineArray[1].split(" - "); // Из второго элемента массива lineArray создается еще один массив строк. В качестве разделителя служит " - ".
            namesToys.add(toyName[0]); // В список добавляется название рассматриваемой игрушки
        }
        for (String name : namesToys) { // Цикл проходит по всему списку с названиями игрушек
            if (!assortiment.contains(name)) { // Проверяется, что в списке нет текущего названия игрушки
                char[] toyNameArray = copiesName.toLowerCase().toCharArray(); // Из введенного пользователем запроса создается массив символов
                int index = 0; // Переменная для хранения индекса элементов массива символов
                String toyName = name.toLowerCase(); // Строка, хранящая имя игрушки
                for (int i = 0; i <= toyName.length(); i++) { // Цикл проходит по все строке с именем
                    if (index < toyNameArray.length) { // Проверяется условие, что значение переменной index меньше, чем длина массива символов
                        if ((i != toyName.length() && toyNameArray[index] == toyName.charAt(i))) { // Проверяется условие, что i не равняетяс длине строки с именем, а также то, что элемент массива под индексом со значением переменной index соответсвует символу строки, который находится по индексом со значением i
                            index++;
                        } else if (index != 0) // Если условие выше не выполняется и значение переменной index не равняется нулю, то цикл завершает работу досрочно.
                            break;
                    } else {
                        assortiment.add(name); // Если цикл не завершил работу досрочно, то значит в названии игрушки имеется ключевое слово, введенное пользователем. Название игрушки добавляется в список assortiment
                        break;
                    }
                }

            }
        }
        String assortimentStr = new String(); // Строка, в которую будет записан ассортимент игрушек
        int newId = 0; // Переменная для нового id, которое будет отображено для пользователя
        for (String name : assortiment) { // Цикл проходит по всему ассортименту
            String recordToy = String.format("Id: %d Наименование: %s", newId, name); // Строка, в которую записывается новое id и название игрушки
            newId++;
            assortimentStr += recordToy + "\n";
        }
        return assortimentStr;
    }

    /**
     * Переопределенный метод, необходимый для розыгрыша игрушки
     * @param nameToy - название игрушки
     */
    @Override
    public String get(String nameToy) {
        int chance = rand.nextInt(0, 100); // Переменная, в которую записывается рандомное число от 0 до 100
        String probabilityCopies = getProbabilityCopiesToys(nameToy); // Переменная, в которую записывается текущаяя вероятность выпадения игрушки в виде строки "название игрушки = вероятность"
        List<String> toysInFile = readFile(pathFile); // Список, в котором хранятся данные из файла ассортимента
        if (!probabilityCopies.isEmpty()) { // Проверяется, что строка, хранящая вероятность выпадения, не пуста
            String[] probabilityToys = probabilityCopies.split(" = "); // Создается массив строк. В качестве разделителя служит " = "
            try {
                int probabilityElement = Integer.parseInt(probabilityToys[1].replace("%", "")); // В переменную записывается текущая вероятность выпадения игрушки
                if (probabilityElement >= chance) { // Проверяется, что вероятность выпадения больше или равна переменной chance
                    for (int i = 0; i < toysInFile.size(); i++) { // Цикл проходит по всему списку с данными из файла
                        String[] line = toysInFile.get(i).split("Наименование: "); // Из текущего элемента создается массив строк. В качестве разделителя служит "Наименование: "
                        String[] lineToy = line[1].split(" - "); // Из второго элемента массива line создается еще один массив строк. В качестве разделителя служит " - "
                        if (lineToy[0].equals(nameToy)) { // Проверяется что первый элемент массива lineToy соответствует названию игрушки, которую хочет выбить пользователь
                            lineToy[1] = Integer.toString(probabilityElement - 1); // Во второй элемент массива lineToy записывается новая вероятность выпадения
                            line[1] = lineToy[0] + " - " + lineToy[1] + "%"; // Во второй элемент массива записывается название игрушки и новая вероятность
                            String newRecordToy = line[0] + "Наименование: " + line[1]; // В переменную newRecordToy записывается обновленная запись об игрушке
                            toysInFile.set(i, newRecordToy); // Текущий элемент списка заменяется на newRecordToy
                        }
                    }
                    PriorityQueue<String> newQueueToys = new PriorityQueue<>(); // Создается новая коллекция
                    for (String line : toysInFile) { // В коллекцию добавлеются элементы из списка c данными из файла
                        newQueueToys.add(line);
                    }
                    rewriteFile(newQueueToys, pathFile); // Файл перезаписывается
                    List<String> winnignFile = readFile("winning.txt"); // Создается список, в котором хранятся данные из файла с выигрышами
                    PriorityQueue<String> winningQueue = new PriorityQueue<>(); // Создается новая коллекция
                    int numberWin = 1; // Переменная для номера записи выигрыша
                    if (winnignFile.isEmpty()) { // Проверяется, что список с данными из файла с выигрышами пуст
                        winningQueue.add("Дата: " + dataWinnings() + "\nСегодняшний выигрышь:\n"); // В список добавляется текущая дата
                        writeFile(winningQueue, "winning.txt"); // В файл записывается новая запись
                        winningQueue = new PriorityQueue<>(); // Коллекция обновляется
                    } else { // Если условие выше не выполняется
                        List<String> dateInFile = convertToDateFile(winnignFile); // Создается список для хранений дат из файла с выигрышами (Только даты)
                        List<String> firstLineInFile = new ArrayList<>(); // Создается список для хранения строк с датами из файла с выигрышами
                        for (String date : dateInFile) { // В список добавляются даты
                            firstLineInFile.add("Дата: " + date);
                        }
                        if (!firstLineInFile.contains("Дата: " + dataWinnings())) { // Проверяется, что список не содержит текущую датуу
                            winningQueue.add("\n\nДата: " + dataWinnings() + "\nСегодняшний выигрышь:\n"); // В список добавляется текущая дата
                            writeFile(winningQueue, "winning.txt"); // В файл записывается новая запись
                            winningQueue = new PriorityQueue<>(); // Коллекция обновляется
                        }
                        String today = dataWinnings(); // Переменная, которая хранит текущую дату
                        if (!winnignFile.isEmpty()) { // Проверяется, что список с данными из файла с выигрышами не пуст
                            List<String> todayWinnigs = new ArrayList<>(); // Создается список для хранения сегодняшних выигрышей
                            boolean findToday = false; // Переменная, определяющая были ли уже выигрыши сегодня
                            for (String line : winnignFile) { // Цикл проходит по всему списку с данными из файла с выигрышами
                                if (findToday) { // Если переменная findToday имеет значение true, то в список с сегодняшними выигрышами добавляется запись
                                    todayWinnigs.add(line);
                                }
                                if (line.equals("Дата: " + today)) { // Проверяется, что строка в файле соответствует сегодняшней дате
                                    findToday = true;
                                }
                            }
                            for (String line : todayWinnigs) { // Цикл проходит по всему списку с сегодняшними выигрышами
                                String[] lineArray = line.split("  Наименование:"); // Из строки создается массив строк. В качестве разделителя служит "  Наименование:"
                                String[] fileId = lineArray[0].split(": "); // Из первого элемента массива создается еще один массив строк. В качестве разделителя служит ": "
                                try {
                                    numberWin = Integer.parseInt(fileId[1]) + 1; // В переменную numberWin записывается номер последнего сегодняшнего выигрыша
                                } catch (Exception e) {
                                    e.getMessage();
                                }
                            }
                        }
                    }
                    String winning = String.format("№: %s  Наименование: %s", numberWin, nameToy); // Запись выигрыша
                    winningQueue.add(winning); // В коллекцию записывается выигрышь
                    writeFile(winningQueue, "Winning.txt"); // В файл записывается новая запись
                    return winning;
                } else
                    return new String();
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "-1";
    }

    /**
     * Приватный метод, необходимый для перезаписи файла
     * @param getQueueToys - коллекция игрушек
     * @param path - путь к файлу
     */
    private void rewriteFile(PriorityQueue<String> getQueueToys, String path) {
        try (FileWriter fw = new FileWriter(path, false)) { // Создаю экземпляр класса FileWriter для записи в файл. Открываю файл в режиме перезаписи
            for (String toy : getQueueToys) { // Цикл проходит по всей коллекции
                fw.write(toy); // В файл записывается элемент коллекции
                fw.append('\n'); // В конец файла добавляется символ новой строки
            }
            fw.flush(); // Сбрасывается буфер записи данных
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Приватный метод, необходимый для записи в файл
     * @param getQueueToys - коллекция игрушек
     * @param path - путь к файлу
     */
    private void writeFile(PriorityQueue<String> getQueueToys, String path) {
        try (FileWriter fw = new FileWriter(path, true)) { // Создаю экземпляр класса FileWriter для записи в файл. Открываю файл в режиме добавления
            for (String toy : getQueueToys) { // Цикл проходит по всей коллекции
                fw.write(toy); // В файл записывается элемент коллекции
                fw.append('\n'); // В конец файла добавляется символ новой строки
            }
            fw.flush(); // Сбрасывается буфер записи данных
        } catch (IOException e) {
            e.getMessage();
        }
    }

    /**
     * Приватный метод для чтения из файла
     * @param path - путь к файлу
     * @return
     */
    private List<String> readFile(String path) {
        List<String> toys = new ArrayList<>(); // Список для хранения данных из файла
        try (FileReader notesReader = new FileReader(path);) { // Создаю экземпляр класса FileReader для чтения из файла
            try (BufferedReader reader = new BufferedReader(notesReader)) { // Создаю экземпляр класса BufferedReader для более эффективного чтения данных из файла
                String line = reader.readLine(); // Создаю переменную, в которую записываю строку из файла
                Toy.resetCount(); // Обнуляю количество созданных экземпляров игрушки 
                while (line != null) {
                    toys.add(line); // Добавляю строку из файла в список
                    line = reader.readLine(); // В переменную line записываю новую строку файла
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return toys;
    }

    /**
     * Приватный метод, необходимый для проверки, можно ли конвертировать символ в число
     * @param number - символ для конвертации
     */
    private boolean convertToNumber(Character number) {
        return Character.isDigit(number);
    }

    /** Приватный метод, необходимый для получения текущей даты в нужном формате */
    private String dataWinnings() {
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yy");
        return format.format(new Date());
    }

    /**
     * Приватный метод, необходимый для нахождения в списке с данными из файла дат и конвертации их в нужный формат
     * @param lineFile - список, в котором имеются даты
     * @return
     */
    private List<String> convertToDateFile(List<String> lineFile) {
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yy"); // Экземпляр класса SimpleDateFormat (шаблот, в котором должна будет записана дата)
        List<String> dateInFile = new ArrayList<>(); // Список для хранения найденных дат
        for (String line : lineFile) { // Цикл проходит по всему списку с данными из файла
            try {
                String[] date = line.split(": "); // Из строки создается массив строк. В качестве разделителя служит ": "
                Date newDate = format.parse(date[1]); // Из второго элемента массива создается дата
                dateInFile.add(format.format(newDate)); // Дата конвертируется в нужный формат и записывается в список для хранения найденных дат
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return dateInFile;
    }

}
