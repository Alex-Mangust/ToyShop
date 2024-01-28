package Model.Toys;

public abstract class Toy {
    private int id;
    private String name;
    private static int count;

    static {
        count = 0;
    }
    
    public Toy(String name) {
        this.id = count;
        count++;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
