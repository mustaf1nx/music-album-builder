/** Input data for the facade; clients do not need to know any subsystem types. */
public record PlaylistRequest(
        String playlistName,
        String genre,
        String mood,
        int targetMinutes,
        boolean commercialUse
) { }
