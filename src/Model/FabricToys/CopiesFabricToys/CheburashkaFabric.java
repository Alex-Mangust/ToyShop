package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.Cheburashka;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Cheburashka */
public class CheburashkaFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Cheburashka */
    @Override
    public Toy createToy() {
        return new Cheburashka("Cheburashka");
    }
    
    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Cheburashka */
    @Override
    public String getNameCopies() {
        return "Cheburashka";
    }
}
