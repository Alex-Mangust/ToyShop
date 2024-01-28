package Model.FabricToys;

import Controller.iToy;

public abstract class ToyFabric {
    protected static int numberName;

    public void openReward() {
        iToy gameItem = createToy();
    }

    static {
        numberName = 0;
    }

    public abstract iToy createToy();
}
