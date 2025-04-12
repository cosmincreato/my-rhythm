package users;

public class User {
    private String username;
    private String password;
    private static int id_counter = 0;
    private int id;

    {id_counter++;}

    public User(String username, String password) {
        this.id = id_counter;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + "Username: " + username + " | Password: " + password;
    }
}
