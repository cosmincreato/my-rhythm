# MyRhythm

**MyRhythm** is a minimalist full-stack desktop application written in Java. It allows administrators to manage users, artists, songs, and playlists. The backend uses **PostgreSQL**, with direct JDBC integration for data persistence, while the frontend is built with **Swing**.

### Backend Structure

- **`DatabaseConnector`** – Handles the database initialization and connection to the PostgreSQL server. The database schema is defined in `schema.sql`, including tables, relationships, and constraints.
- **`User`** – Represents a user of the application.
- **`Performer`** *(abstract)* – Base class for all performers.
    - **`Artist`** – A solo performer;
    - **`Band`** – A group of performers, contains multiple singers.
- **`Song`** – Represents a song entity.
- **`Genre`** - Enum of possible song genres.
- **`Playlist`** – A user-created collection of songs.
- **Services** - Manage operations and interactions with the database.
  - **`UserService`**
  - **`PerformerService`**
  - **`SongService`**
  - **`PlaylistService`**
- **`Audit`** – Logs all significant actions to `audit_log.csv` for monitoring and debugging.

### Frontend Structure

- **`UILauncher`** – Launches the graphical user interface.
- **`Header`** – A reusable component for displaying panel headers.
- **`MainFrame`** – The main window of the application containing a list of all users, artists and songs.
- **`ProfileFrame`** – Displays user profile details and allows editing.
- **`PlaylistFrame`** – Shows and manages the contents of a specific playlist.
- **`TopPanel`** – Top bar of the main frame.
- **`UsersPanel`** – Displays all users and allows the admin to manage them.
- **`PerformersPanel`** – Lists all performers (artists and bands) with related actions.
- **`SongsPanel`** – Displays songs and provides control for adding.
- **`ProfilePanel`** – Shows details of a selected user profile.
- **`FavoritePerformersPanel`** – Lists a user’s favorite performers.
- **`FavoriteSongsPanel`** – Lists a user’s favorite songs.
- **`UserPlaylistsPanel`** – Displays all playlists created by a user.
- **`PlaylistPanel`** – Displays and manages a single playlist's songs.
- **`Colors`** — Enum for the application color palette.
- **`UserRemovedListener`** - Interface used to observe user removal events, following the *Observer Design Pattern*.