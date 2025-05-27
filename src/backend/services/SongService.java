package backend.services;

import backend.*;
import backend.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;

public class SongService {
    private static SongService instance = null;
    private static Audit audit = new Audit();


    private SongService() {}

    public static SongService getInstance() {
        if (instance == null)
            instance = new SongService();
        return instance;
    }

    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        String sql = "SELECT id, name, genre, duration_in_seconds, performer_id FROM songs";

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Genre genre = Genre.valueOf(rs.getString("genre"));
                int durationInSeconds = rs.getInt("duration_in_seconds");
                int performerId = rs.getInt("performer_id");
                Performer performer = PerformerService.getInstance().findPerformer(performerId);

                Song song = new Song(performer, name, genre, durationInSeconds);
                song.setId(id);
                songs.add(song);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea melodiilor: " + e.getMessage());
        }

        return songs;
    }

    public void addSong(Song song) {
        String sql = "INSERT INTO songs (name, genre, duration_in_seconds, performer_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, song.getName());
            stmt.setString(2, song.getGenre().toString());
            stmt.setInt(3, song.getDurationInSeconds());
            stmt.setInt(4, song.getPerformer().getId());

            stmt.executeUpdate();
            audit.log("Melodie adaugata: " + song.getName());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea melodiei: " + e.getMessage());
        }
    }

    public Song findSong(int id) {
        String sql = "SELECT id, name, genre, duration_in_seconds, performer_id FROM songs WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                Genre genre = Genre.valueOf(rs.getString("genre"));
                int durationInSeconds = rs.getInt("duration_in_seconds");
                int performerId = rs.getInt("performer_id");
                Performer performer = PerformerService.getInstance().findPerformer(performerId);

                Song song = new Song(performer, name, genre, durationInSeconds);
                song.setId(id);
                return song;
            } else {
                throw new UserNotFoundException("Melodia cu id-ul " + id + " nu a fost gasita.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea melodiei in baza de date: " + e.getMessage(), e);
        }
    }
}
