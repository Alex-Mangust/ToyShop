package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.TaddyBear;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Vinni Puh */
public class VinniPuhFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Vinni Puh */
    @Override
    public Toy createToy() {
        return new TaddyBear("Vinni Puh");
    }

    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Vinni Puh */
    @Override
    public String getNameCopies() {
        return "Vinni Puh";
    }
    
}
