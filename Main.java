/** Client uses only the facade and public request/result records. */
public final class Main {
    public static void main(String[] args) {
        MusicPlaylistFacade musicService = new MusicPlaylistFacade();
        PlaylistRequest request = new PlaylistRequest(
                "Night Drive Mix", "Electronic", "energetic", 18, true
        );

        try {
            PlaylistResult result = musicService.createPlaylist(request);
            System.out.println("PLAYLIST: " + result.name());
            System.out.println("Tracks: " + result.trackCount());
            System.out.printf("Duration: %d min %d sec%n",
                    result.totalDurationSeconds() / 60, result.totalDurationSeconds() % 60);
            System.out.printf("Average BPM: %.1f | Average BPM jump: %.1f%n",
                    result.averageBpm(), result.averageBpmJump());
            for (int i = 0; i < result.songs().size(); i++) {
                System.out.printf("%d. %s%n", i + 1, result.songs().get(i));
            }
            System.out.println("CSV saved: " + result.csvFile());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            System.err.println("Could not create playlist: " + exception.getMessage());
            System.exit(1);
        }
    }
}
