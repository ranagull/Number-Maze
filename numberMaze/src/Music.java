import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
public class Music {
    AudioInputStream audioInput;
    Clip clip;
    public void playMusic(String path) {
        try {
            File music = new File(path);
            if (music.exists()) {
                audioInput = AudioSystem.getAudioInputStream(music);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void stop(){
        clip.close();
    }
}