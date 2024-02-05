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

/**
 * Класс, описывающий поведение розыгрыша в магазине игрушек. Имеет интерфейс
 * iShop
 */
public class Shop implements iShop {
    private List<ToyFabric> toyFabrics = new ArrayList<>(); // Список моделей для создания игрушек
    private PriorityQueue<Toy> queueToys = new PriorityQueue<>(); // Коллекция для хранения экземпляров игрушек

    private static Random rand; // Статическое поле, необходимое для создания рандомного числа при розыгрыше

    static {
        rand = new Random();
    }

    /**
     * Конструктор класса
     */
    public Shop() throws ClassNotFoundException {
        List<String> listFabricFile = ListFabricFile(); // Список, в котором хранятся все модели фабрик по созданию игрушек, которые можно создать (Модели автоматически добавляются из директории, в которой хранятся)
        if (!listFabricFile.isEmpty()) { // Если список не пуст, выполняется следующее действие
            for (String fabricFileName : listFabricFile) { // Цикл проходит по всему списку и записывает в переменную fabricFileName полное имя класса модели
                try {
                    Class<?> toyFabricFile = Class.forName(fabricFileName); // Загружается класс с именем, указанным в переменной fabricFileName
                    Constructor<?> constructor; // Создается переменная, которая будет хранить информацию о конструкторе класса
                    try {
                        constructor = toyFabricFile.getConstructor(); // Происходит получение конструктора для загруженного класса
                        try {
                            Object toyFabric = constructor.newInstance(); // Создается класса экземпляр полученного класса
                            if (toyFabric instanceof ToyFabric) {
                                toyFabrics.add((ToyFabric) toyFabric); // Если класс является наследником ToyFabric, его экземпляр добавляется в список
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

    /**
     * Переопределенный метод, необходимый для получения списка со всеми моделями
     * фабрик по созданию игрушек
     */
    @Override
    public List<ToyFabric> getToyFabrics() {
        return toyFabrics;
    }

    /**
     * Переопределенный метод, необходимый для получения списка со всеми моделями
     * фабрик по созданию игрушек в текстовом виде
     */
    @Override
    public String toyFabrics() {
        int number = 1; // Переменная, которая задает номер записи
        String copiesNames = new String(); // Переменная, которая хранит список с моделями фабрик в текстовом виде
        for (ToyFabric fabric : toyFabrics) { // Цикл проходит по всему списку с моделями фабрик
            copiesNames += String.format("%d - %s;  ", number, fabric.getNameCopies());
            if (number % 5 == 0) // Если номер записи делится без остатка на 5, то происходит переход на новую строку (Необходимо для лучшего отображения)
                copiesNames += "\n";
            number++;
        }
        return copiesNames + "\n";
    }

    /**
     * Переопределенный метод, необходимый для создания игрушки
     * 
     * @param criteria  - номер фабрики по созданию игрушки
     * @param probality - вес (вероятность выпадения) игрушки
     */
    @Override
    public boolean createToy(int criteria, int probability) {
        if (!toyFabrics.isEmpty()) {
            PriorityQueue<Toy> newQueueToys = new PriorityQueue<>(); // Новая коллекция для хранения экземпляров игрушек
            int currentProbability = 0;  // Переменная, которая будет хранить текущую вероятность выпадения заданного типа игрушки
            if (!queueToys.isEmpty()) { 
                String countCopiesToys = getProbabilityCopiesToys(toyFabrics.get(criteria).getNameCopies()); // Полученная вероятность выпадения задонного типа игрушки
                if (!countCopiesToys.isEmpty()) {
                    String[] countCopiesToysArray = countCopiesToys.split(" = "); // Так как метод по подсчету вероятности выпадения игрушки возвращает строку в виде "название игрушки = вероятность выпадения", создается массив, в котором в качестве разделителя служит " = ". Таким образом во втором элементе массива находится вероятность.
                    try {
                        currentProbability = Integer.parseInt(countCopiesToysArray[1]); // В переменную currentProbability записывается вероятность, преобразованная в числовой вид
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (currentProbability != 0) { // Если вероятность выпадения игрушки, которые необходимо добавить не равняется 0, то удаляем игрушку из магазина (Необходимо, так как если эта игрушка уже ранее была добавлена, вероятность должна быть задана занова, а не прибавлена к уже имеющейся)
                    deleteToys(toyFabrics.get(criteria).getNameCopies());
                    Toy.resetCount();
                }
                for (Toy toy : queueToys) { // Цикл проходит по всем игрушкам, которые уже есть в ассортименте, и добавляет их в новую коллекцию
                    newQueueToys.add(toy);
                }
            }
            // Цикл добавляет новую игрушку в ассортимент
            while (probability > 0) {
                if (criteria >= 0 && criteria < toyFabrics.size())
                    newQueueToys.add(toyFabrics.get(criteria).createToy());
                else
                    newQueueToys.add(toyFabrics.get(toyFabrics.size() - 1).createToy());
                probability--;
            }
            queueToys = newQueueToys; // Новая коллекция заменяет старую
            return true;
        } else
            return false;
    }

    /** Переопределенный метод, необходимый для получения коллекция, в которой хранятся экземпляры игрушек */
    @Override
    public PriorityQueue<Toy> getQueueToys() {
        return queueToys;
    }

    /**
     * Переопределенный метод, необходимый для изменения текущей коллекции, в которой хранятся экземпляры игрушек
     * @param toys - новая коллекция
     * */
    @Override
    public void setQueueToys(PriorityQueue<Toy> toys) {
        queueToys = toys;
    }

    /**
     * Переопределенный метод, необходимый для получения текущей вероятности выпадения игрушки
     * @param toyName - название игрушки
     */
    @Override
    public String getProbabilityCopiesToys(String toyName) {
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

    /** Переопределенный метод, необходимый для получения всего текущего ассортимента */
    @Override
    public String assortiment() {
        List<String> assortiment = new ArrayList<>(); // Список для хранения названий игрушек
        for (Toy toy : getQueueToys()) { // Цикл проходит по всей коллекции с игрушками
            if (!assortiment.contains(toy.getName())) { // Проверяется, что в ассортименте уже нет названия рассматриваемой игрушки
                assortiment.add(toy.getName()); // В ассортимент добавляется название игрушки
            }
        }
        String assortimentStr = new String(); // Строка, в которую будет записан ассортимент игрушек
        int newId = 0; // Переменная для нового id, которое будет отображено для пользователя
        for (String name : assortiment) { // Цикл проходит по всему ассортименту
            for (Toy toy : getQueueToys()) { // Цикл проходит по всей коллекции с игрушками
                if (name.equals(toy.getName())) { //Проверяется, что текущее название соответствует названию рассматриваемой игрушки
                    String recordToy = resetId(toy, newId); // Строка, в которую записывается результат метода resetId, который принимает в качестве аргумента рассматриваемую игрушку и новый id
                    newId++;
                    assortimentStr += recordToy + "\n"; // Записывается строка, хранящая информацию об игрушки
                    break;
                }
            }
        }
        return assortimentStr;
    }

    /**
     * Переопределенный метод, необходимый для получения ассортимента игрушек, которые соответсвуют ключевому слову, введенному пользователем
     * @param copiesName - название игрушки/ключевое слово
     */
    @Override
    public String assortiment(String copiesName) {
        List<String> assortiment = new ArrayList<>(); // Список для хранения названий игрушек, подходящий под запрос
        for (Toy toy : getQueueToys()) { // Цикл проходит по всей коллекции с игрушками
            if (!assortiment.contains(toy.getName())) { // Проверяется, что в ассортименте уже нет названия рассматриваемой игрушки
                char[] toyNameArray = copiesName.toLowerCase().toCharArray(); // Из введенного пользователем запроса создается массив символов
                int index = 0; // Переменная для хранения индекса элементов массива символов
                String toyName = toy.getName().toLowerCase(); // Строка, хранящая название игрушки
                for (int i = 0; i <= toyName.length(); i++) { // Цикл проходит по все строке с именем
                    if (index < toyNameArray.length) { // Проверяется условие, что значение переменной index меньше, чем длина массива символов
                        if ((i != toyName.length() && toyNameArray[index] == toyName.charAt(i))) { // Проверяется условие, что i не равняетяс длине строки с именем, а также то, что элемент массива под индексом со значением переменной index соответсвует символу строки, который находится по индексом со значением i
                            index++;
                        } else if (index != 0) // Если условие выше не выполняется и значение переменной index не равняется нулю, то цикл завершает работу досрочно.
                            break;
                    } else {
                        assortiment.add(toy.getName()); // Если цикл не завершил работу досрочно, то значит в названии игрушки имеется ключевое слово, введенное пользователем. Название игрушки добавляется в список assortiment
                        break;
                    }
                }
            }
        }  
        String assortimentStr = new String(); // Строка, в которую будет записан ассортимент игрушек
        int newId = 0; // Переменная для нового id, которое будет отображено для пользователя
        for (String name : assortiment) { // Цикл проходит по всему ассортименту
            for (Toy toy : getQueueToys()) { // Цикл проходит по всей коллекции с игрушками
                if (name.equals(toy.getName())) { //Проверяется, что текущее название соответствует названию рассматриваемой игрушки
                    String recordToy = resetId(toy, newId); // Строка, в которую записывается результат метода resetId, который принимает в качестве аргумента рассматриваемую игрушку и новый id
                    newId++;
                    assortimentStr += recordToy + "\n"; // Записывается строка, хранящая информацию об игрушки
                    break;
                }
            }
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
        if (!probabilityCopies.isEmpty()) { // Проверяется, что строка, хранящая вероятность выпадения, не пуста
            String[] probabilityToys = probabilityCopies.split(" = "); // Создается массив строк. В качестве разделителя служит " = "
            try {
                int probabiliElement = Integer.parseInt(probabilityToys[1]); // В переменную записывается текущая вероятность выпадения игрушки
                if (probabiliElement >= chance) { // Проверяется, что вероятность выпадения больше или равна переменной chance
                    PriorityQueue<Toy> newQueueToys = new PriorityQueue<>(queueToys); // Создается новая коллекция с элементами из коллекции queueToys
                    for (Toy toy : queueToys) {  // Цикл проходит по всей коллекции с игрушками
                        if (toy.getName().equals(nameToy)) { // Проверяется, что название рассматриваемой игрушки соответствует названию игрушки, которую хочет выбить пользователь.
                            newQueueToys.remove(toy); // Игрушка удалается из коллекции
                            setQueueToys(newQueueToys); // Коллекция, хранящая игрушки в магазине заменяется на новую.
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

    /**
     * Приватный метод, необходимый для изменения id при выводе игрушки пользователю
     * @param toy - игрушка
     * @param id - новый id
     */
    private String resetId(Toy toy, int id) {
        boolean findNumber = false; // Переменная, определяющая найдено ли число в строке (Которая хранит информацию об игрушке)
        String recordToy = new String(); // Переменная, которая будет хранить измененную информацию об игрушке
        for (int i = 0; i < toy.toString().length(); i++) { // Цикл проходит по всей строке
            if (convertToNumber(toy.toString().charAt(i))) { // Проверяется, что символ может быть преобразован в число
                if (!findNumber) { // Проверяется, что число еще не найдено
                    recordToy += id; // В recordToy, вместо текущего символа строки, записывается новое id
                    findNumber = true;
                }
            } else { // Если символ не может быть преобразован в число, он записывается в recordToy
                recordToy += toy.toString().charAt(i);
            }
        }
        return recordToy;
    }

    /** Приватный метод, необходимый для получения списка всех фабрик из директории, в которой они хранятся */
    private List<String> ListFabricFile() {
        try {
            String path = "src\\Model\\FabricToys\\CopiesFabricToys"; // Переменная, которая хранит путь к директории
            String fullName = "Model.FabricToys.CopiesFabricToys."; // Переменна, необходимая для записи полного имени класса
            File folder = new File(path); // Экземпляр класса File с указанным путем path
            List<String> fileClassNames = new ArrayList<>(); // Список для хранения всех имен классов, которые находятся в директории
            if (folder.isDirectory()) { // Проверяется, что folder - директория
                File[] files = folder.listFiles(); // Создается массив File со всеми файлами из директории
                if (files != null) {
                    for (File file : files) { // Цикл проходит по всему массиву с файлами
                        String className = fullName + file.getName().replace(".java", ""); // Создается переменная, в которую записывается полное имя класса (значение переменной fullName + имя файла класса без расширения)
                        fileClassNames.add(className); // Полное имя класса добавляется в список
                    }
                }
            }
            return fileClassNames;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Приватный метод, необходимый для удаления игрушки
     * @param toyName - название игрушки
     */
    private void deleteToys(String toyName) {
        PriorityQueue<Toy> newQueueToys = new PriorityQueue<>(queueToys); // Создается новая коллекция с элементами из коллекции queueToys
        for (Toy toy : queueToys) { // Цикл проходит по всей коллекции с игрушками
            if (toyName.equals(toy.getName())) { // Проверяется, что название рассматриваемой игрушки, соответствует названию игрушки, которую необходима удалить
                newQueueToys.remove(toy); // Из новой колекции удалется рассматриваемая игрушка
            }
        }
        setQueueToys(newQueueToys); // Коллекция, хранящая игрушки в магазине заменяется на новую.
    }

    /**
     * Приватный метод, необходимый для проверки, можно ли конвертировать символ в число
     * @param number - символ для конвертации
     */
    private boolean convertToNumber(Character number) {
        return Character.isDigit(number);
    }
}
