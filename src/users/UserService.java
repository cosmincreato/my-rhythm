package users;

import java.util.ArrayList;

public class UserService {
    private ArrayList<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        System.out.println("Utilizator adaugat.");
    }

    public User findUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new UserNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit.");
    }

    public void removeUser(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                users.remove(user);
                System.out.println("Utilizatorul cu id-ul " + id + " a fost sters.");
                return;
            }
        }
        throw new UserNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit.");
    }

    @Override
    public String toString() {
        String us = "";
        for (User user : users)
            us += user + "\n";
        return us;
    }
}
