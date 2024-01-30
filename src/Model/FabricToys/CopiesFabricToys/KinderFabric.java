package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.Kinder;
import Model.Toys.Toy;

public class KinderFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new Kinder("Kinder");
    }

    @Override
    public String getNameCopies() {
        return "Kinder";
    }
    
    
}
