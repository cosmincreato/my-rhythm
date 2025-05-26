import backend.database.DatabaseConnector;
import frontend.UILauncher;

public class Main {

    public static void main(String[] args) {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        UILauncher.launch();
    }
}