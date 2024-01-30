package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.SpiderMan;
import Model.Toys.Toy;

public class SpiderManFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new SpiderMan("Spider-Man");
    }

    @Override
    public String getNameCopies() {
        return "Spider-Man";
    }

}
