package backend.services;

public class SongService {
    private static SongService instance = null;

    private SongService() {}

    public static SongService getInstance() {
        return instance;
    }
}
