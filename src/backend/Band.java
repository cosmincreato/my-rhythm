package backend;

import java.util.ArrayList;

public class Band extends Performer {
    private ArrayList<Artist> members;

    public Band(String name) {
        super(name);
        this.members = new ArrayList<>();
    }

    public void addMember(Artist artist) {
        members.add(artist);
    }

    @Override
    public ArrayList<Artist> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        String s = this.getName() + " (";
        for (Artist member : getMembers()) {
            s += member + ", ";
        }
        return s.substring(0, s.length() - 2) + ")";
    }
}
