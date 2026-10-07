import java.util.List;

/* client composes refined abstractions with concrete implementors at runtime */
public class Main {
    public static void main(String[] args) {
        AudioOutput speakers = new SpeakersOutput();
        AudioOutput headphones = new HeadphonesOutput();

        System.out.println("=== SingleTrackPlayer ===");
        MusicPlayer singleTrackPlayer = new SingleTrackPlayer(
                speakers,
                "Midnight Drive"
        );
        singleTrackPlayer.play();

        System.out.println("\nSwitching implementation: Speakers -> Headphones");
        singleTrackPlayer.setAudioOutput(headphones);
        singleTrackPlayer.play();
        singleTrackPlayer.stop();

        System.out.println("\n=== PlaylistPlayer ===");
        MusicPlayer playlistPlayer = new PlaylistPlayer(
                speakers,
                "Study Mix",
                List.of("Focus Beat", "Quiet Keys", "Night Coding")
        );
        playlistPlayer.play();

        System.out.println("\nSwitching implementation: Speakers -> Headphones");
        playlistPlayer.setAudioOutput(headphones);
        playlistPlayer.play();
        playlistPlayer.stop();
    }
}
