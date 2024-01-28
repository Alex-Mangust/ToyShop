package Model.FabricToys;

import Controller.iToy;
import Model.Toys.SpiderMan;

public class SpiderManFabric extends ToyFabric {

    @Override
    public iToy createToy() {
        numberName++;
        return new SpiderMan(String.format("Spider-Man_%d", numberName));
    }
    
}
