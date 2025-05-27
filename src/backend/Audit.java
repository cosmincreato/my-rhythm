package backend;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
    private static String filePath;
    static {
        filePath = "src/audit_log.csv";
    }
    public void log(String action) {
        LocalDateTime timestamp = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = timestamp.format(formatter);

        String logEntry = String.format("%s [%s]", action, formattedTime);

        try (FileWriter fileWriter = new FileWriter(filePath, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            printWriter.println(logEntry);

        } catch (IOException e) {
            System.err.println("Eroare la scriere: " + e.getMessage());
        }
    }

}