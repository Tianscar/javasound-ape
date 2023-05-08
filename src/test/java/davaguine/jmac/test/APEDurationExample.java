package davaguine.jmac.test;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class APEDurationExample {

    public static void main(String[] args) {
        try {
            AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(Thread.currentThread().getContextClassLoader().getResourceAsStream("fbodemo1.ape"));
            System.out.println("APE audio duration: " + (long) fileFormat.properties().get("duration") / 1_000_000.0);
        } catch (IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

}
