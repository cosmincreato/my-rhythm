# MyRhythm

MyRhythm is a minimalist backend tracking app built in Java with Swing.

## Backend

### Classes
- `User`
- `UserService` — *Manages backend.services*
- `Performer` - *Abstract class for singers*
- `Artist` - *Extends `Performer`, only one singer*
- `Band` - *Extends `Performer`, has more than one singer*
- `PerformerService` - *Manages performers*
- `Playlist` - *List of songs, created by the user*
- `Song`
- `SongService` - *Manages songs*

### Exceptions
- `UserNotFoundException`

## Frontend

### Classes
- `UILauncher`— *Initializes the UI*
- `Header` - Panel header text
- `MainFrame`
- `ProfileFrame`
- `ProfilePanel`
- `TopPanel`
- `UsersPanel`

### Interfaces
- `UserAddedListener` - *Observer Design Pattern*
- `UserRemovedListener` - *Observer Design Pattern*

### Enums
- `Colors` — *Application color palette*
- `Genre` - *List of possible song genres*