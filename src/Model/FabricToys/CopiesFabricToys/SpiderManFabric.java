package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.SpiderMan;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Spider-Man */
public class SpiderManFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Spider-Man */
    @Override
    public Toy createToy() {
        return new SpiderMan("Spider-Man");
    }

    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Spider-Man */
    @Override
    public String getNameCopies() {
        return "Spider-Man";
    }

}
