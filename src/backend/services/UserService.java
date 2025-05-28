package backend.services;

import backend.*;
import backend.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;

public class UserService {
    private static UserService instance = null;
    private static Audit audit = new Audit();


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
            audit.log("Utilizator adaugat: " + user.getUsername());

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

    public void removeUser(User user) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                audit.log("Utilizatorul cu id-ul " + user.getId() + " a fost sters.");
            } else {
                throw new UserNotFoundException("Utilizatorul cu id-ul " + user.getId() + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea utilizatorului din baza de date: " + e.getMessage(), e);
        }
    }

    /// Favorite Performers

    public ArrayList<Performer> getFavoritePerformers(User user) {
        ArrayList<Performer> favoritePerformers = new ArrayList<>();
        String sql = "SELECT performer_id FROM favorite_performers WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int performerId = rs.getInt("performer_id");

                Performer performer = PerformerService.getInstance().findPerformer(performerId);
                if (performer != null) {
                    favoritePerformers.add(performer);
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea artistilor favoriți: " + e.getMessage());
        }

        return favoritePerformers;
    }

    public void addFavoritePerformer(User user, Performer performer) {
        try {
            Performer existing = findFavoritePerformer(user.getId(), performer.getId());
            audit.log("Artistul " + performer.getName() + " este deja in lista de favoriti pentru " + user.getUsername());
            return;

        } catch (RuntimeException e) {
            if (!e.getMessage().contains("nu este favorit")) {
                throw e;
            }
        }

        String sql = "INSERT INTO favorite_performers (user_id, performer_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            stmt.setInt(2, performer.getId());

            stmt.executeUpdate();
            audit.log("Artist favorit adaugat: " + performer.getName() + " la utilizatorul: " + user.getUsername());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea artistului favorit: " + e.getMessage());
        }
    }


    public Performer findFavoritePerformer(int userId, int performerId) {
        String sql = "SELECT performer_id FROM favorite_performers WHERE user_id = ? AND performer_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, performerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Performer performer = PerformerService.getInstance().findPerformer(performerId);
                if (performer != null) {
                    return performer;
                } else {
                    throw new RuntimeException("Artistul cu id-ul " + performerId + " nu a fost gasit.");
                }
            } else {
                throw new RuntimeException("Artistul cu id-ul " + performerId + " nu este favorit pentru utilizatorul cu id-ul " + userId + ".");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea artistului favorit: " + e.getMessage(), e);
        }
    }

    /// Favorite Songs

    public ArrayList<Song> getFavoriteSongs(User user) {
        ArrayList<Song> favoriteSongs = new ArrayList<>();
        String sql = "SELECT song_id FROM favorite_songs WHERE user_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int songId = rs.getInt("song_id");

                Song song = SongService.getInstance().findSong(songId);
                if (song != null) {
                    favoriteSongs.add(song);
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea artistilor favoriți: " + e.getMessage());
        }

        return favoriteSongs;
    }

    public void addFavoriteSong(User user, Song song) {
        try {
            Song existing = findFavoriteSong(user.getId(), song.getId());
            System.out.println("Artistul " + song.getName() + " este deja in lista de favoriti pentru " + user.getUsername());
            return;

        } catch (RuntimeException e) {
            if (!e.getMessage().contains("nu este favorit")) {
                throw e;
            }
        }

        String sql = "INSERT INTO favorite_songs (user_id, song_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, user.getId());
            stmt.setInt(2, song.getId());

            stmt.executeUpdate();
            audit.log("Artist favorit adaugat: " + song.getName() + " la utilizatorul: " + user.getUsername());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea artistului favorit: " + e.getMessage());
        }
    }


    public Song findFavoriteSong(int userId, int songId) {
        String sql = "SELECT song_id FROM favorite_songs WHERE user_id = ? AND song_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, songId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Song song = SongService.getInstance().findSong(songId);
                if (song != null) {
                    return song;
                } else {
                    throw new RuntimeException("Melodia cu id-ul " + songId + " nu a fost gasita.");
                }
            } else {
                throw new RuntimeException("Melodia cu id-ul " + songId + " nu este favorita pentru utilizatorul cu id-ul " + userId + ".");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea melodiei favorite: " + e.getMessage(), e);
        }
    }
}
