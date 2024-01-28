package Model.FabricToys;

import Controller.iToy;
import Model.Toys.Cheburashka;

public class CheburashkaFabric extends ToyFabric {

    @Override
    public iToy createToy() {
        numberName++;
        return new Cheburashka(String.format("Cheburashka_%d", numberName));
    }
    
}
