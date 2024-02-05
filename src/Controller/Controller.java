package Controller;

import java.util.concurrent.TimeUnit;

import Controller.Interfaces.iShop;
import Controller.Interfaces.iView;

/** Класс, описывающий работу модели с пользовательским интерфейсом */
public class Controller {
    private iShop toyShop; // Модель, с которой работает конроллер
    private iView view; // Пользовательский интерфейс, с которым работает контроллер

    /**
     * Конструктор класса
     * 
     * @param toyShop - модель
     * @param view  - пользовательский интерфейс
     */
    public Controller(iShop toyShop, iView view) {
        this.toyShop = toyShop;
        this.view = view;
    }

    /**
     * Приватный метод, проверяющий наличие моделей по созданию игрушек в директории
     * 
     * @return возвращает имеет ли список какие-либо значения
     */
    private boolean testFabrics() {
        if (toyShop.getToyFabrics().isEmpty())
            return false;
        return true;
    }

    /**
     * Приватный метод, проверяющий наличие игрушек в ассортименте
     * 
     * @return возвращает имеет ли коллекция какие-либо значения
     */
    private boolean testToys() {
        if (toyShop.getQueueToys().isEmpty())
            return false;
        return true;
    }

    /**
     * Приватный метод, необходимый для приостановления программы в процессе работы
     * @param second - время паузы в секундах
     */
    private void timeSleep(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Приватный метод, необходимый для конвертирования текста в число
     * @param number - текст, который нужно конвертировать
     */
    private boolean convertToNumber(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Метод, описывающий запуск и работу программы */
    public void run() {
        view.titleProgram(); // Выводится название программы
        Command com = Command.NONE; // Инициализируется экземпляр класса перечисления со значением NONE
        boolean nextRun = true; // Переменная, необходимая для продолжения работы программы, пока пользователь не введет команду о завершении
        view.showMenu(); // В консоль выводится список всех команд для работы с программой
        while (nextRun) {
            String command = view.prompt(view.inputMessage()); // Происходит запрос пользователю, чтобы он ввел команду.
            try {
                com = Command.valueOf(command.toUpperCase()); // Экземпляру класса перечисления записывается значение, которое ввел пользователь
                switch (com) {
                    case EXIT: // Завершение работы программы
                        nextRun = false;
                        view.exitMessage();
                        break;
                    case MENU: // Вывод в консоль списка всех команд для работы с программой
                        view.showMenu();
                        break;
                    case ADD: // Добавление новой игрушки
                        if (testFabrics()) { // Проверяется наличие моделий по созданию игрушек
                            boolean availableOption = false; // Переменная для определения, ввел ли пользователь один из предложенных вариантов или нет
                            while (!availableOption) {
                                int choiceFabrics = view.choiceNewToy(toyShop.toyFabrics()) - 1; // Выбор, какую игрушку добавить
                                if (choiceFabrics >= 0 && choiceFabrics < toyShop.getToyFabrics().size()) { // Проверяется, что пользователь сделал выбор из предложенных вариантов
                                    availableOption = true;
                                    boolean inputChancePossible = false; // Переменная для определения корректности, введенной пользователем, вероятности выпадения игрушки
                                    while (!inputChancePossible) {
                                        int inputChance = view.chanceToy(); // Определение вероятности выпадения
                                        if (inputChance > 0 && inputChance <= 100) { // Проверяется корректоность введенной вероятности выпадения
                                            inputChancePossible = true;
                                            boolean create = toyShop.createToy(choiceFabrics, inputChance); // Игрушка добавляется в ассортимент
                                            if (create)
                                                view.toyAddedSuccessfully(); // Пользователь получает сообщение об успешном добавлении
                                            else
                                                view.errorPathToDirectoryFabric(); // Пользователь получает сообщение о том, что игрушку не удалось добавить
                                            timeSleep(1); // Пауза работы программы 1 секунда
                                        } else {
                                            view.impossibleChance(); // Пользователь получает сообщение о том, что он ввел неккоректную вероятность выпадения игрушки
                                            timeSleep(1); // Пауза работы программы 1 секунда
                                        }
                                    }
                                } else {
                                    view.errorChoiceNewToy(); // Пользователь получает сообщение о том, что выбранного им варианта создании игрушки нет
                                    timeSleep(1); // Пауза работы программы 1 секунда
                                }
                            }
                        }
                        break;
                    case ASSORTIMENT: // Вывод ассортимента игрушек
                        if (testToys()) { // Проверяется, что ассортимент не пуст
                            String nameToy = view.nameToys(); // Пользователь получает сообщение, где ему предстоит сделать выбор - вывести весь ассортимент или только игрушки, которые будут соответсвовать ключевому слову, которое он введет
                            if (nameToy.toLowerCase().equals("all*")) {
                                if (toyShop.assortiment().isEmpty())
                                    view.notFound(); // Пользователь получает сообщение, что не удалось найти ни одну игрушку
                                else
                                    System.out.println(toyShop.assortiment()); // Выводится весь ассортимент
                            } else {
                                String copiesToyAssortiment = toyShop.assortiment(nameToy); 
                                if (copiesToyAssortiment.isEmpty())
                                    view.notFound(); // Пользователь получает сообщение, что не удалось найти ни одну игрушку
                                else
                                    System.out.println(copiesToyAssortiment); // Выводятся только те игрушки, которые соответсвуют введенному ключевому слову
                            }
                        } else
                            view.assortimentIsEmpty(); // Пользователь получает сообщение, что ассортимент пуст
                        break;
                    case GET: // Розыгрыш игрушки
                        if (testToys()) { // Проверяется, что ассортимент не пуст
                            int countGame = view.countGame(); // Пользователь выберает сколько раз он будет играть
                            for (int i = 0; i < countGame; i++) {
                                String nameToy = toyShop.toyFabrics(); // Пользователю предлагается выбрать игрушку, которую он может выбить
                                int yourChoice = view.choiceGetToy(nameToy); // Пользователь выбирает игрушку, которую хочет получить
                                if (yourChoice > 0 && yourChoice <= toyShop.getToyFabrics().size()) { // Проверяется, что пользователь сделал выбор из предложенных вариантов
                                    String getToy = toyShop.get(toyShop.getToyFabrics().get(yourChoice - 1).getNameCopies()); // В переменную getToy записывается игрушка, если ее удалось выбить
                                    if (getToy.isEmpty())
                                        view.unluckyWin(); // Пользователь получает сообщение о том, что выбить игрушку не удалось
                                    else if (getToy.equals("-1"))
                                        view.outOfAssortiment(); // Пользователь получает сообщение о том, что игрушки, которую он желает выбить, нет в ассортименте
                                    else {
                                        view.congratulations(); // Пользователь получает сообщение о том, что он выиграл
                                        System.out.println(getToy); // Пользователь получает выигрышь
                                        timeSleep(1); // Пауза работы программы 1 секунда
                                    }
                                    System.out.println();
                                }
                            }
                        } else
                            view.assortimentIsEmpty(); // Пользователь получает сообщение, что ассортимент пуст
                        timeSleep(1); // Пауза работы программы 1 секунда
                        break;
                    case CHANCE: // Расчет вероятности выпадения игрушки
                        if (testToys()) { // Проверяется, что ассортимент не пуст
                            String fabrics = toyShop.toyFabrics(); // В переменную записывается список имеющихся фабрик по созданию игруушек
                            String choiceToy = view.calculateChance(fabrics);  // Пользователь делает выбор игрушки, для которой хочет узнать вероятность выпадения
                            String nameToy = new String(); 
                            String[] fabricsArray = fabrics.split(";"); // Создается массив строк из списка фабрик по созданию игрушек. В качестве разделителя служит ";"
                            for (int i = 0; i < fabricsArray.length; i++) {
                                String[] fabricElement = fabricsArray[i].split(" - "); // Из текущего элемента массива создается еще один массив строк. В качестве разделителя служит " - "
                                for (int j = 0; j < fabricElement[0].length(); j++) { // Цикл проходит по всем символам первого элемента нового массива (в первом элементе хранится id)
                                    if (convertToNumber((Character.toString(fabricElement[0].charAt(j))))) //Проверяется, что символ можно конвертировать в число 
                                        if (choiceToy.equals(Character.toString(fabricElement[0].charAt(j)))) // Сравнивается то, что выбрал пользователь с id фабрики по созданию игрушек
                                            nameToy = fabricElement[1]; // В переменную записывается название игрушек, которые может создать текущая фабрика
                                }
                            }
                            if (!nameToy.isEmpty()) {
                                System.out.println(view.resultChancee(toyShop.getProbabilityCopiesToys(nameToy))); // Пользователь получает сообщение с вероятностью выпадения выбранной игрушки
                            }
                        } else
                            view.assortimentIsEmpty(); // Пользователь получает сообщение, что ассортимент пуст
                        break;
                    default: // Ввод неккоректной прогрыммы
                        view.commandNotFound(); // Пользователь получает сообщение о том, что команды, которую он ввел, нет в списке команд для работы с программой
                }
            } catch (Exception e) {
                view.commandNotFound(); // Пользователь получает сообщение о том, что команды, которую он ввел, нет в списке команд для работы с программой
            }
        }
    }

}
