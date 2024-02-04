package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.SpiderMan;
import Model.Toys.Toy;

/** Класс, описывающий поведение фабрики по созданию игрушки типа Black Spider-Man */
public class BlackSpiderManFabric extends ToyFabric {

    /** Переопределенный метод, необходимый для создания игрушки типа Black Spider-Man */
    @Override
    public Toy createToy() {
        return new SpiderMan("Black Spider-Man");
    }

    /** Переопределенный метод, необходимый для получения имени созданной игрушки типа Black Spider-Man */
    @Override
    public String getNameCopies() {
        return "Black Spider-Man";
    }
    
}
