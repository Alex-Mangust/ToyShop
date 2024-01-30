package Model.Toys;

public abstract class Toy implements Comparable<Toy> {
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

    public static void resetCount() {
        count = 0;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return String.format("id: %d  Наименование: %s", this.id, this.name);
    }

    @Override
    public int compareTo(Toy toy) {
        if (toy == null) {
            return 1;
        }
        if (this.id < toy.id) {
            return -1;
        } else if (this.id > toy.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
