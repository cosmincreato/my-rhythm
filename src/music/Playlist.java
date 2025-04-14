package music;

import java.util.*;

public class Playlist {
    private Set<Song> songs;

    public Playlist() {
        songs = new TreeSet<>(Comparator.comparing(Song::getName));
    }

    public void addSong(Song song) {
        this.songs.add(song);
    }

    public Set<Song> getSongs() {
        return songs;
    }
}
