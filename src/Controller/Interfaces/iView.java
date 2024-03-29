package Controller.Interfaces;

/** Интерфейс, объявляющий работу пользовательского интерфейса. */
public interface iView {
    void titleProgram(); // Объявленный метод, необходимый для вывода в консоль названия программы
    String prompt(String msg); // Объявленный метод, необходимый для получения данных от пользователя
    String inputMessage(); // Объявленный метод, необходимый для получения сообщения для пользователя, в котором предлагается ввести команду 
    void exitMessage(); // Объявленный метод, необходимый для вывода в консоль сообщения о том, что работа программы завершена
    void showMenu(); // Объявленный метод, необходимый для вывода в консоль списка всех команд для работы с программой
    int choiceNewToy(String msg); // Объявленный метод, необходимый для работы интерфейса добавления новой игрушки
    void errorChoiceNewToy(); // Объявленный метод, необходимый для вывода в консоль ошибки, если пользователь при создании игрушки выберет не предусмотренный вариант
    int chanceToy(); // Объявленный метод, необходимый для работы интерфейса по указанию вероятности выпадения игрушки
    void toyAddedSuccessfully(); // Объявленный метод, необходимый для вывода в консоль сообщения об успешном добавлении игрушки
    void errorPathToDirectoryFabric(); // Объявленный метод, необходимый для вывода в консоль ошибки в случае, если у программы нет ни одной модели по созданию игрушек
    void impossibleChance(); // Объявленный метод, необходимый для вывода в консоль ошибки в случае, если введена некорректная вероятность выпадения игрушки
    int allOrNotAllAssortiment(); // Объявленный метод, необходимый для работы с интерфейсом по получению ассортимента игрушек
    String nameToys(); // Объявленный метод, необходимый для работы с интерфейсом по полуучения ассортимента игрушек
    void notFound(); // Объявленный метод, необходимый для вывода в консоль ошибки, в случае если по ключевому слову, введенному пользователем, не удалось найти ни одну игрушку
    int countGame(); // Объявленный метод, необходимый для работы с интерфейсом по розыгрышу игрушек
    int choiceGetToy(String msg); // Объявленный метод, необходимый для работы с интерфейсом по розыгрышу игрушек
    void congratulations(); // Объявленный метод, необходимый для вывода сообщающения пользователю о его выигрыше
    void unluckyWin(); // Объявленный метод, необходимый для вывода сообщающения пользователю о том, что выиграть ему не удалось
    void outOfAssortiment(); // Объявленный метод, необходимый для вывода сообщения пользователю, если игрушки, которую он желает выбить, нет в ассортименте
    String calculateChance(String msg); // Объявленный метод, необходимый для работы с интерфейсом по расчету вероятности выпадения игрушки
    String resultChancee(String msg); // Объявленный метод, необходимый для работы с интерфейсом по расчету вероятности выпадения игрушки
    void assortimentIsEmpty(); // Объявленный метод, необходимый для вывода в консоль ошибки, если ассортимент пуст
    void commandNotFound(); // Объявленный метод, необходимый для вывода в консоль ошибки, если пользователь ввел некорректную команду
}
