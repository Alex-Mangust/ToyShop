package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.TaddyBear;
import Model.Toys.Toy;

public class TaddyBearFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new TaddyBear("Taddy Bear");
    }

    @Override
    public String getNameCopies() {
        return "Taddy Bear";
    }
    
}
