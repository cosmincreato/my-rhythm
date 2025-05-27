package backend;

public class Song {
    private Performer performer;
    private String name;
    private Genre genre;
    private int durationInSeconds;
    private int id;


    public Song(Performer performer, String name, Genre genre, int durationInSeconds) {
        this.performer = performer;
        this.name = name;
        this.genre = genre;
        this.durationInSeconds = durationInSeconds;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public Performer getPerformer() {
        return performer;
    }

    public Genre getGenre() {
        return genre;
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
