package backend.services;

import backend.Artist;
import backend.Band;
import backend.Performer;

import java.util.ArrayList;

public class PerformerService {

    private static PerformerService instance = null;
    private ArrayList<Performer> performers;

    private PerformerService() {
        ///  Lista cu cateva trupe si artisti care vor fi adaugate in baza de date
        this.performers = new ArrayList<>();
        Band theKillers = new Band("The Killers");
        theKillers.addMember(new Artist("Brandon Flowers"));
        theKillers.addMember(new Artist("Dave Keuning"));
        theKillers.addMember(new Artist("Mark Stoermer"));
        theKillers.addMember(new Artist("Ronnie Vannucci Jr."));

        Band slipknot = new Band("Slipknot");
        slipknot.addMember(new Artist("Mick Thomson"));
        slipknot.addMember(new Artist("Corey Taylor"));

        Band oasis = new Band("Oasis");
        oasis.addMember(new Artist("Liam Gallagher"));
        oasis.addMember(new Artist("Noel Gallagher"));

        Band ratb = new Band("Robin and the Backstabbers");
        ratb.addMember(new Artist("Robin"));
        ratb.addMember(new Artist("Florentin Vasile"));
        ratb.addMember(new Artist("Vladimir Proca"));
        ratb.addMember(new Artist("Andrei Fântână"));

        this.performers.add(theKillers);
        this.performers.add(slipknot);
        this.performers.add(oasis);
        this.performers.add(ratb);
        this.performers.add(new Artist("Duffy"));
        this.performers.add(new Artist("Metro Boomin"));
        this.performers.add(new Artist("Avicii"));
    }

    public static PerformerService getInstance() {
        if (instance == null)
            instance = new PerformerService();
        return instance;
    }

    public void addPerformer(Performer performer) {
        this.performers.add(performer);
        System.out.println("Artist adaugat.");
    }

    public ArrayList<Performer> getPerformers() {
        return performers;
    }


}
