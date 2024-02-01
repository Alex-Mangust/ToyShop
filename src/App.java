// Необходимо написать проект, для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

// Напишите класс-конструктор у которого принимает минимум 3 строки, содержащие три поля id игрушки, текстовое название и частоту выпадения игрушки

// Из принятой строки id и частоты выпадения(веса) заполнить минимум три массива.

// Используя API коллекцию: java.util.PriorityQueue добавить элементы в коллекцию

// Организовать общую очередь

// Вызвать Get 10 раз и записать результат в файл

import Controller.Controller;
import Controller.Interfaces.iShop;
import Controller.Interfaces.iView;
import Model.Shop;
import Views.View;

public class App {
    public static void main(String[] args) throws Exception {

        iShop toyShop = new Shop();
        iView view = new View();

        Controller controller = new Controller(toyShop, view);
        controller.run();
    }
}
