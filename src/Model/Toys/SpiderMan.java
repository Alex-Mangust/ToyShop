package Model.Toys;

import Controller.iToy;

public class SpiderMan extends Toy implements iToy {
    private static int probability;
    
    static {
        probability = 20;
    }

    public SpiderMan(String name) {
        super(name);
    }

    @Override
    public int getProbability() {
        return probability;
    }
}
