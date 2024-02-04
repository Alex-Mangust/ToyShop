package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.TaddyBear;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Taddy Bear */
public class TaddyBearFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Taddy Bear */
    @Override
    public Toy createToy() {
        return new TaddyBear("Taddy Bear");
    }

    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Taddy Bear */
    @Override
    public String getNameCopies() {
        return "Taddy Bear";
    }
    
}
