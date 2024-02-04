package Controller.Interfaces;

import java.util.List;
import java.util.PriorityQueue;

import Model.FabricToys.ToyFabric;
import Model.Toys.Toy;

/** Интерфейс, объявляющий поведение розыгрыша в магазине игрушек. */
public interface iShop {
    List<ToyFabric> getToyFabrics(); // Объявленный метод, необходимый для получения списка со всеми моделями фабрик по созданию игрушек
    String toyFabrics(); // Объявленный метод, необходимый для получения списка со всеми моделями фабрик по созданию игрушек в текстовом виде
    boolean createToy(int criteria, int probability); // Объявленный метод, необходимый для создания игрушки
    PriorityQueue<Toy> getQueueToys(); // Объявленный метод, необходимый для получения колекция, в которой хранятся экземпляры игрушек
    void setQueueToys(PriorityQueue<Toy> toys); // Объявленный метод, необходимый для изменения текущей колекции, в которой хранятся экземпляры игрушек
    String getProbabilityCopiesToys(String toyName); // Объявленный метод, необходимый для получения текущей вероятности выпадения игрушки
    String assortiment(); // Объявленный метод, необходимый для получения всего текущего ассортимента
    String assortiment(String copiesName); // Объявленный метод, необходимый для получения ассортимента игрушек, которые соответсвуют ключевому слову, введенному пользователем
    String get(String nameToyId); // Объявленный метод, необходимый для розыгрыша игрушки
}
