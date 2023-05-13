package davaguine.jmac.test;

import davaguine.jmac.spi.APEAudioFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class APETest {

    @Test
    @DisplayName("unsupported exception")
    public void unsupported() {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fbodemo1_vorbis.ogg")) {
            assertThrows(UnsupportedAudioFileException.class, () -> {
                new APEAudioFileReader().getAudioInputStream(stream);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
