package backend.services;

import backend.*;
import backend.database.DatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerformerService {

    private static PerformerService instance = null;

    private PerformerService() {
    }

    public static PerformerService getInstance() {
        if (instance == null)
            instance = new PerformerService();
        return instance;
    }

    public ArrayList<Performer> getPerformers() {
        ArrayList<Performer> performers = new ArrayList<>();
        String sql = "SELECT id, name, type FROM performers";

        try (Connection conn = DatabaseConnector.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String performerType = rs.getString("type");
                Performer performer;

                if ("artist".equalsIgnoreCase(performerType))
                    performer = new Artist(name);
                else performer = new Band(name);
                performer.setId(id);
                performers.add(performer);
            }

        } catch (SQLException e) {
            System.err.println("Eroare la citirea artistilor: " + e.getMessage());
        }

        return performers;
    }

    public void addPerformer(Performer performer) {
        String sql = "INSERT INTO performers (name, type) VALUES (?, ?) RETURNING id";
        int id = 0;

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, performer.getName());
            stmt.setString(2, performer.getType());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("id");
                    System.out.println("Artist adaugat: " + performer.getName());
                }
            }

        } catch (SQLException e) {
            System.err.println("Eroare la adaugarea artistului: " + e.getMessage());
            return;
        }

        // Daca e trupa, adaugam membri
        if ("Band".equalsIgnoreCase(performer.getType())) {
            String memberSql = "INSERT INTO band_members (band_id, name) VALUES (?, ?)";
            List<Artist> bandMembers = performer.getMembers();

            for (Artist member : bandMembers) {
                try (Connection conn = DatabaseConnector.connect();
                     PreparedStatement stmt = conn.prepareStatement(memberSql)) {

                    stmt.setInt(1, id);
                    stmt.setString(2, member.getName());

                    stmt.executeUpdate();
                    System.out.println("Membrul trupei adaugat: " + member.getName());

                } catch (SQLException e) {
                    System.err.println("Eroare la adaugarea membrului trupei: " + e.getMessage());
                }
            }
        }
    }

    public Performer findPerformer(int id) {
        String sql = "SELECT id, name, type FROM performers WHERE id = ?";

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String performerType = rs.getString("type");

                Performer performer;
                if ("Artist".equalsIgnoreCase(performerType))
                        performer = new Artist(name);
                else performer = new Band(name);
                performer.setId(id);
                return performer;
            } else {
                throw new UserNotFoundException("Artistul cu id-ul " + id + " nu a fost gasit.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea artistului in baza de date: " + e.getMessage(), e);
        }
    }

    public ArrayList<Artist> getBandMembers(int bandId) {
        ArrayList<Artist> bandMembers = new ArrayList<>();
        String sql = "SELECT name FROM band_members WHERE band_id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bandId);
            ResultSet rs = stmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                String name = rs.getString("name");
                Artist artist = new Artist(name);
                bandMembers.add(artist);
            }

            if (!found) {
                throw new RuntimeException("Membri trupei cu id-ul " + bandId + " nu au fost gasiti.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea membrilor trupei in baza de date: " + e.getMessage(), e);
        }

        return bandMembers;
    }

}
