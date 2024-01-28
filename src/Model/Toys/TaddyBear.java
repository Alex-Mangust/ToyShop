package Model.Toys;

import Controller.iToy;

public class TaddyBear extends Toy implements iToy{
    private static int probability;

    static {
        probability = 40;
    }

    public TaddyBear(String name) {
        super(name);
    }

    @Override
    public int getProbability() {
        return probability;
    }
 
}
