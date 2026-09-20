package music.band;

public class ElectricGuitar implements Guitar {

    private static final int DEFAULT_GAIN_LEVEL = 7;

    @Override
    public void strum() {
        System.out.println("Electric guitar: pickups send signal to the amp at gain " + DEFAULT_GAIN_LEVEL + ".");
    }
}
