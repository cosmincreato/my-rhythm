package backend;

import java.util.*;

public class Playlist {
    private String name;
    private User user;
    private int id;

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Playlist(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", user=" + user +
                ", id=" + id +
                '}';
    }
}
