package backend;

import java.util.List;

public class Artist extends Performer {

    public Artist(String name) {
        super(name);
    }

    @Override
    public List<Artist> getMembers() {
        return List.of(this);
    }

    @Override
    public String getType() {
        return "artist";
    }

    @Override
    public String toString() {
        return "Artist: " + getName();
    }
}
