package Model.Toys;

import Controller.iToy;

public class Kinder extends Toy implements iToy{
    private static int probability;

    static {
        probability = 30;
    }

    public Kinder(String name) {
        super(name);
    }

    @Override
    public int getProbability() {
        return probability;
    } 
}
