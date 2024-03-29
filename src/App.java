// Необходимо написать проект, для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

// Напишите класс-конструктор у которого принимает минимум 3 строки, содержащие три поля id игрушки, текстовое название и частоту выпадения игрушки

// Из принятой строки id и частоты выпадения(веса) заполнить минимум три массива.

// Используя API коллекцию: java.util.PriorityQueue добавить элементы в коллекцию

// Организовать общую очередь

// Вызвать Get 10 раз и записать результат в файл

import Controller.Controller;
import Controller.Interfaces.iShop;
import Controller.Interfaces.iView;
import Model.Decorator;
import Model.Shop;
import Views.View;

public class App {
    public static void main(String[] args) throws Exception {

        iShop toyShop = new Shop();  // Создаю экземпляр класса Shop для работы с розыгрышом в магазине игрушек
        iShop decorator = new Decorator(toyShop, "Assortiment.txt"); // Создаю экземпляр класса decorator, который иметирует работу класса Shop, добавляя/переделывая функционал (добавляет возможность работы с файлами)
        iView view = new View(); // Создаю экземпляр класса View для работы с пользовательским интерфейсом

        Controller controller = new Controller(decorator, view); // Создаю экземпляр класса Controllers для работы с моделью и пользовательским интерфейсом, передаю, в качестве аргументов, созданные модель и пользовательский интерфейс
        controller.run(); // Вызываю метод для запуска программы
    }
}
