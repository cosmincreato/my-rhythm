package database;

import java.io.*;
import java.sql.*;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "cosmincreato";
    private static final String PASSWORD = "loganmohawkpuppy";

    public DatabaseConnector() {
        Connection conn = connect();
        if (conn != null) {
            System.out.println("Conexiunea la baza de date a fost realizata.");
            runSchema(conn);
        }
    }

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Conexiunea la baza de date nu s-a putut realiza: " + e.getMessage());
            return null;
        }
    }

    private void runSchema(Connection conn) {
        InputStream input = getClass().getResourceAsStream("schema.sql");

        if (input == null) {
            System.err.println("Nu s-a gasit schema.sql in acelasi pachet cu DatabaseConnector.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            StringBuilder sqlBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }

            String[] statements = sqlBuilder.toString().split(";");

            try (Statement stmt = conn.createStatement()) {
                for (String sql : statements) {
                    String trimmed = sql.trim();
                    if (!trimmed.isEmpty()) {
                        stmt.execute(trimmed);
                    }
                }
                System.out.println("schema.sql a fost executat.");
            }

        } catch (Exception e) {
            System.err.println("Eroare la citirea schema.sql: " + e.getMessage());
        }
    }
}
