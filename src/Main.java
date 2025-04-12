import ui.UILauncher;
import users.UserService;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        UILauncher.launch(userService);
    }
}