package music;

import java.util.*;

public class Playlist {
    private String name;
    private Set<Song> songs;
    private static int id_counter = 0;
    private int id;

    {id_counter++;}

    public Playlist(String name) {
        this.id = id_counter;
        this.name = name;
        songs = new TreeSet<>(Comparator.comparing(Song::getName));
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public Set<Song> getSongs() {
        return songs;
    }
}
