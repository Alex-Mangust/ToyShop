package Model.Toys;

import Controller.iToy;

public class Cheburashka extends Toy implements iToy {
    private static int probability;
    
    static {
        probability = 10;
    }

    public Cheburashka(String name) {
        super(name);
    }

    @Override
    public int getProbability() {
        return probability;
    }
}
