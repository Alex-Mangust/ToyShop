package Model.FabricToys.CopiesFabricToys;

import Model.FabricToys.ToyFabric;
import Model.Toys.Cheburashka;
import Model.Toys.Toy;

public class CheburashkaFabric extends ToyFabric {

    @Override
    public Toy createToy() {
        return new Cheburashka("Cheburashka");
    }
    
    @Override
    public String getNameCopies() {
        return "Cheburashka";
    }
}
