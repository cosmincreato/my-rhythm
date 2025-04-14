package users;

import com.sun.source.doctree.SeeTree;
import music.Performer;
import music.Playlist;
import music.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class User {
    private String username;
    private String password;
    private ArrayList<Playlist> playlists;
    private Set<Song> favoriteSongs;
    private Set<Performer> favoritePerformers;
    private static int id_counter = 0;
    private int id;

    {id_counter++;}

    public User(String username, String password) {
        this.id = id_counter;
        this.username = username;
        this.password = password;
        this.playlists = new ArrayList<>();
        this.favoriteSongs = new TreeSet<>(Comparator.comparing(Song::getName));
        this.favoritePerformers = new TreeSet<>(Comparator.comparing(Performer::getName));
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void addFavoriteSong(Song song) {
        this.favoriteSongs.add(song);
    }

    public void addFavoritePerformer(Performer performer) {
        this.favoritePerformers.add(performer);
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

    public Set<Performer> getFavoritePerformers() {
        return favoritePerformers;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + "Username: " + username;
    }
}
