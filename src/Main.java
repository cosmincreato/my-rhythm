import backend.database.DatabaseConnector;
import frontend.UILauncher;

public class Main {

    public static void main(String[] args) {
        new DatabaseConnector();
        UILauncher.launch();
    }
}