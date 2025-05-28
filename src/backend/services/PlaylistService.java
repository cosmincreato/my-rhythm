package backend.services;

import backend.*;
import backend.database.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaylistService {
    private static Audit audit = new Audit();
    private static PlaylistService instance = null;

    private PlaylistService() {}

    public static PlaylistService getInstance() {
        if (instance == null)
            instance = new PlaylistService();
        return instance;
    }

    public void addPlaylist(Playlist playlist) {
        String sql = "INSERT INTO playlists (name, user_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, playlist.getName());
            stmt.setInt(2, playlist.getUser().getId());

            stmt.executeUpdate();
            audit.log("Playlist adaugat: " + playlist.getName() + " la userul: " + playlist.getUser().getUsername());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea playlist-ului: " + e.getMessage());
        }

    }

    public Playlist findPlaylist(int playlistId) {
        String sql = "SELECT name, user_id FROM playlists WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                int userId = rs.getInt("user_id");

                User user = UserService.getInstance().findUser(userId);
                Playlist playlist = new Playlist(name, user);
                playlist.setId(playlistId);
                return playlist;
            } else {
                throw new UserNotFoundException("Playlist-ul cu id-ul " + playlistId + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea playlist-ului in baza de date: " + e.getMessage(), e);
        }
    }

    public Song findSongInPlaylist(int playlistId, int songId) {
        String sql = "SELECT song_id FROM playlist_songs WHERE playlist_id = ? AND song_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlistId);
            stmt.setInt(2, songId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Song song = SongService.getInstance().findSong(songId);
                if (song != null) {
                    return song;
                } else {
                    throw new RuntimeException("Melodia cu id-ul " + songId + " nu a fost gasita in baza de date.");
                }
            } else {
                throw new RuntimeException("Melodia cu id-ul " + songId + " nu se afla in playlist-ul cu id-ul " + playlistId + ".");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea melodiei in playlist: " + e.getMessage(), e);
        }
    }

    public void addSongToPlaylist(Playlist playlist, Song song) {
        try {
            Song existing = findSongInPlaylist(playlist.getId(), song.getId());
            System.out.println("Melodia " + song.getName() + " este deja in playlist-ul " + playlist.getName());
            return;

        } catch (RuntimeException e) {
            if (!e.getMessage().contains("nu se afla")) {
                throw e;
            }
        }

        String sql = "INSERT INTO playlist_songs (playlist_id, song_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlist.getId());
            stmt.setInt(2, song.getId());

            stmt.executeUpdate();
            audit.log("Melodie adaugata: " + song.getName() + " la playlist-ul: " + playlist.getName());

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea melodiei in playlist: " + e.getMessage());
        }
    }

    public ArrayList<Playlist> getUserPlaylists(User user) {
         ArrayList<Playlist> playlists = new ArrayList<>();
         String sql = "SELECT id, name FROM playlists WHERE user_id = ?";

         try (Connection conn = DatabaseConnector.connect();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

             stmt.setInt(1, user.getId());
             ResultSet rs = stmt.executeQuery();

             while (rs.next()) {
                 int playlistId = rs.getInt("id");

                 Playlist playlist = findPlaylist(playlistId);
                 if (playlist != null) {
                     playlists.add(playlist);
                 }
             }

         } catch (SQLException e) {
             System.err.println("Eroare la citirea playlist-urilor: " + e.getMessage());
         }

         return playlists;
    }

    public ArrayList<Song> getPlaylistSongs(Playlist playlist) {
        ArrayList<Song> playlistSongs = new ArrayList<>();
        String sql = "SELECT song_id FROM playlist_songs WHERE playlist_id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playlist.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int songId = rs.getInt("song_id");

                Song song = SongService.getInstance().findSong(songId);
                if (song != null) {
                    playlistSongs.add(song);
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea melodiilor: " + e.getMessage());
        }

        return playlistSongs;
    }

}
