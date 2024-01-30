// Необходимо написать проект, для розыгрыша в магазине игрушек. Функционал должен содержать добавление новых игрушек и задания веса для выпадения игрушек.

// Напишите класс-конструктор у которого принимает минимум 3 строки, содержащие три поля id игрушки, текстовое название и частоту выпадения игрушки

// Из принятой строки id и частоты выпадения(веса) заполнить минимум три массива.

// Используя API коллекцию: java.util.PriorityQueue добавить элементы в коллекцию

// Организовать общую очередь

// Вызвать Get 10 раз и записать результат в файл

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

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




        System.out.println(toyShop.toyFabrics());

        // toyShop.createToy(4, 2);
        // toyShop.createToy(3, 3);
        // toyShop.createToy(2, 2);
        // toyShop.createToy(1, 1);
        // toyShop.createToy(0, 9);

        // for (Toy toy : toyShop.getQueueToys()) {
        //     System.out.println(toy);
        // }

        // Random rand = new Random();
        // int chance = rand.nextInt(0, 100);
        // int numberElement = 0;
        // PriorityQueue<Toy> queueToys = toyShop.getQueueToys();
        // Iterator<Toy> iterator = queueToys.iterator();
        // while (iterator.hasNext()) {
        //     Toy toy = iterator.next();
        //     if (chance == numberElement) {
        //         System.out.println(toy);
        //         iterator.remove();
        //         toyShop.setQueueToys(queueToys);
        //         break; 
        //     }
        //     numberElement++;
        // }

        // System.out.println(queueToys.size());
        // System.out.println("К сожалению выбить игрушку не удалось.");

        // System.out.println(toyShop.getCountCopiesToys("Black"));

    }
}
