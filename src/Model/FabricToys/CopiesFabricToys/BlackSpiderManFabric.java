package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.SpiderMan;
import Model.Toys.Toy;

public class BlackSpiderManFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new SpiderMan("Black Spider-Man");
    }

    @Override
    public String getNameCopies() {
        return "Black Spider-Man";
    }
    
}
