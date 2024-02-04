package Model.FabricToys;

import Model.Toys.Toy;

/** Абстрактный класс, описывающий поведение фабрики игрушек */
public abstract class ToyFabric {

    /** Абрстрактный метод, необходимый для создания игрушки */
    public abstract Toy createToy();

    /** Абстрактный метод, необходимый для получения имени созданной игрушки */
    public abstract String getNameCopies();
}
