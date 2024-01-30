package Model.FabricToys;

import Model.Toys.Toy;

public abstract class ToyFabric {

    public void openReward() {
        Toy gameItem = createToy();
        System.out.println(gameItem);
    }

    public abstract Toy createToy();
    public abstract String getNameCopies();
}
