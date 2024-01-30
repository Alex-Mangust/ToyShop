package Controller.Interfaces;

import java.util.List;
import java.util.PriorityQueue;

import Model.FabricToys.ToyFabric;
import Model.Toys.Toy;

public interface iShop {
    List<ToyFabric> getToyFabrics();
    String toyFabrics();
    boolean createToy(int criteria, int probability);
    PriorityQueue<Toy> getQueueToys();
    void setQueueToys(PriorityQueue<Toy> toys);
    String getCountCopiesToys(String toyName);
    String assortiment();
    String assortiment(String copiesName);
}
