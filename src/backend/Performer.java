package backend;

import java.util.List;

public abstract class Performer {
    private String name;
    private static int id_counter = 0;
    private int id;

    {id_counter++;}

    public Performer(String name) {
        this.id = id_counter;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract String getType();

    public abstract List<Artist> getMembers();
}
