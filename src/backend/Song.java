package backend;

public class Song {
    private Performer performer;
    private String name;
    private Genre genre;
    private int durationInSeconds;
    private static int id_counter = 0;
    private int id;

    {id_counter++;}

    public Song(Performer performer, String name, Genre genre, int durationInSeconds) {
        this.id = id_counter;
        this.performer = performer;
        this.name = name;
        this.genre = genre;
        this.durationInSeconds = durationInSeconds;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        int minutes = durationInSeconds / 60;
        int seconds = durationInSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return "Song{" +
                "performer=" + performer +
                ", name='" + name + '\'' +
                ", genre=" + genre +
                ", durationInSeconds=" + durationInSeconds +
                ", id=" + id +
                '}';
    }
}
