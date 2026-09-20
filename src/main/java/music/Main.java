package music;

import music.band.AcousticBandFactory;
import music.band.BandInstrumentFactory;
import music.band.BandSetupClient;
import music.band.ElectricBandFactory;
import music.player.AudioPlayerFactory;
import music.player.Mp3PlayerFactory;
import music.player.WavPlayerFactory;

public class Main {

    public static void main(String[] args) {
        demonstrateFactoryMethod();
        System.out.println();
        demonstrateAbstractFactory();
    }

    private static void demonstrateFactoryMethod() {
        System.out.println("--- Part A: Factory Method ---");
        AudioPlayerFactory mp3Factory = new Mp3PlayerFactory();
        mp3Factory.playTrack("Neon Skyline (Deluxe Edition)");

        AudioPlayerFactory wavFactory = new WavPlayerFactory();
        wavFactory.playTrack("Midnight Static");
    }

    private static void demonstrateAbstractFactory() {
        System.out.println("--- Part B: Abstract Factory ---");
        BandInstrumentFactory acousticFactory = new AcousticBandFactory();
        BandSetupClient acousticBand = new BandSetupClient(acousticFactory);
        acousticBand.rehearse();

        BandInstrumentFactory electricFactory = new ElectricBandFactory();
        BandSetupClient electricBand = new BandSetupClient(electricFactory);
        electricBand.rehearse();
    }
}
