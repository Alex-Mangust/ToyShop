package Model.FabricToys;

import Controller.iToy;
import Model.Toys.TaddyBear;

public class TaddyBearFabric extends ToyFabric {

    @Override
    public iToy createToy() {
        numberName++;
        return new TaddyBear(String.format("Taddy_%d", numberName));
    }
    
}
