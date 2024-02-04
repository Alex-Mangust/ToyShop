package Model.Toys;

/** Абстрактный класс, описывающий поведение игрушки. Имеет интерфейс Comparable */
public abstract class Toy implements Comparable<Toy> {
    private int id; // id игрушки
    private String name; // имя игрушки
    private static int count; // Количество созданных экземпляров

    static {
        count = 0;
    }

    /**
     * Конструктор класса
     * @param name - имя игрушки
     */
    public Toy(String name) {
        this.id = count;
        count++;
        this.name = name; 
    }

    /** Статический метод, необходимый для получения количества созданных экземпляров */
    public static void resetCount() {
        count = 0;
    }


    /** Метод, необходимый для получения id игрушки */
    public int getId() {
        return id;
    }

    /** Метод, необходимый для получения имени игрушки */
    public String getName() {
        return name;
    }
    
    /** Переопределенный метод, сообщающий информацию об экземпляре класса */
    @Override
    public String toString() {
        return String.format("id: %d  Наименование: %s", this.id, this.name);
    }

    /** Метод, необходимый для сравнения экземпляров класса */
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
