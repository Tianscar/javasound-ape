package davaguine.jmac.test;

import davaguine.jmac.cli.Compress;
import davaguine.jmac.cli.Decompress;
import davaguine.jmac.cli.JavaSoundSimpleAudioPlayer;
import davaguine.jmac.cli.Verify;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CLITest {

    @Test
    @DisplayName("wav -> ape")
    public void compress() {
        Compress.main(new String[] {"src/test/resources/fbodemo1.wav", "fbodemo1.ape"});
    }

    @Test
    @DisplayName("ape -> wav")
    public void decompress() {
        Decompress.main(new String[] {"src/test/resources/fbodemo1.ape", "fbodemo1.wav"});
    }

    @Test
    @DisplayName("verify ape")
    public void verify() {
        Verify.main(new String[] {"src/test/resources/fbodemo1.ape"});
    }

    @Test
    @DisplayName("play ape")
    public void play() {
        JavaSoundSimpleAudioPlayer.main(new String[] {"src/test/resources/fbodemo1.ape"});
    }

}
