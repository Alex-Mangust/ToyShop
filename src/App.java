// Необходимо написать проект, для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

// Напишите класс-конструктор у которого принимает минимум 3 строки, содержащие три поля id игрушки, текстовое название и частоту выпадения игрушки

// Из принятой строки id и частоты выпадения(веса) заполнить минимум три массива.

// Используя API коллекцию: java.util.PriorityQueue добавить элементы в коллекцию

// Организовать общую очередь

// Вызвать Get 10 раз и записать результат в файл

import Model.Shop;
import Model.Toys.Toy;

public class App {
    public static void main(String[] args) throws Exception {

        Shop toyShop = new Shop();
        System.out.println(toyShop.getToyFabrics());;

        toyShop.createToy(0, 2);
        toyShop.createToy(4, 2);
        // toyShop.createToy(3,3);
        // toyShop.createToy(2, 2);
        // toyShop.createToy(1, 1);
        // toyShop.createToy(0, 1);

        for (Toy toy : toyShop.getQueueToys()) {
            System.out.println(toy);
        }

        System.out.println(toyShop.getCountCopiesToys("Black"));;
    
    }
}

