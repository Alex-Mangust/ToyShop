package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.Kinder;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Kinder */
public class KinderFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Kinder */
    @Override
    public Toy createToy() {
        return new Kinder("Kinder");
    }

    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Kinder */
    @Override
    public String getNameCopies() {
        return "Kinder";
    }
    
    
}
