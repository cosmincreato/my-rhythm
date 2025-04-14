import music.PerformerService;
import music.SongService;
import ui.UILauncher;
import users.UserService;

public class Main {

    public static void main(String[] args) {
        UserService userService = new UserService();
        PerformerService performerService = new PerformerService();
        SongService songService = new SongService();
        UILauncher.launch(userService, performerService, songService);
    }
}