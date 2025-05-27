package backend;

import backend.services.PerformerService;

import java.util.ArrayList;

public class Band extends Performer {
    private ArrayList<Artist> members;

    public Band(String name) {
        super(name);
        this.members = new ArrayList<>();
    }

    public Band(String name, ArrayList<Artist> members) {
        super(name);
        this.members = members;
    }

    @Override
    public ArrayList<Artist> getMembers() {
        return members;
    }

    @Override
    public String getType() {
        return "band";
    }

    @Override
    public String toString() {
        ArrayList<Artist> bandMembers = PerformerService.getInstance().getBandMembers(this.getId());
        String s = "Band: " + this.getName() + " (";
        for (Artist member : bandMembers) {
            s += member + ", ";
        }
        return s.substring(0, s.length() - 2) + ")";
    }
}