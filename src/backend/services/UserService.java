package backend.services;

import backend.User;
import backend.UserNotFoundException;
import backend.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;

public class UserService {

    private static UserService instance = null;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null)
            instance = new UserService();
        return instance;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password FROM users";

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(username, password);
                user.setId(id);
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea utilizatorilor: " + e.getMessage());
        }

        return users;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());

            stmt.executeUpdate();
            System.out.println("Utilizator adaugat: " + user.getUsername());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea utilizatorului: " + e.getMessage());
        }
    }


    public User findUser(int id) {
        String sql = "SELECT id, username, password FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(username, password);
                user.setId(id);
                return user;
            } else {
                throw new UserNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea utilizatorului in baza de date: " + e.getMessage(), e);
        }
    }
    public void removeUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilizatorul cu id-ul " + id + " a fost sters.");
            } else {
                throw new UserNotFoundException("Utilizatorul cu id-ul " + id + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea utilizatorului din baza de date: " + e.getMessage(), e);
        }
    }

    @Override
    public String toString() {
        String us = "";
        for (User user : getUsers())
            us += user + "\n";
        return us;
    }
}
