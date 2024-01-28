package Model.FabricToys;

import Controller.iToy;
import Model.Toys.Kinder;

public class KinderFabric extends ToyFabric {

    @Override
    public iToy createToy() {
        numberName++;
        return new Kinder(String.format("Kinder_%d", numberName));
    }
    
}
