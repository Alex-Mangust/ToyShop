package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.TaddyBear;
import Model.Toys.Toy;

public class VinniPuhFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new TaddyBear("Vinni Puh");
    }

    @Override
    public String getNameCopies() {
        return "Vinni Puh";
    }
    
}
