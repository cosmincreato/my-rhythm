package backend;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class User {
    private String username;
    private String password;
    private ArrayList<Playlist> playlists;
    private Set<Song> favoriteSongs;
    private int id;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.playlists = new ArrayList<>();
        this.favoriteSongs = new TreeSet<>(Comparator.comparing(Song::getName));
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void addFavoriteSong(Song song) {
        this.favoriteSongs.add(song);
    }

    public void setId(int id) {
        this.id = id;
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

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public Set<Song> getFavoriteSongs() {
        return favoriteSongs;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + "Username: " + username;
    }
}
