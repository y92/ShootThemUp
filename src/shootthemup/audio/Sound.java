package shootthemup.audio;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import shootthemup.box.LeftBox;

/**
 * Class which contains all possible sounds (such as shots or vampire shouts)
 */
public class Sound {
    
    /**
     * Path of directory which contains sound files
     */
    private static final String SOUNDS_DIRECTORY = "src/sounds/";
    
    /**
     * Sound played when a pistolero shoots
     */
    public static final String SHOT = SOUNDS_DIRECTORY+"Shot_2.mp3";
    
    /**
     * Sound played when a vampired is killed by a ball
     */
    public static final String VAMPIRE_KILLED = SOUNDS_DIRECTORY+"Vampire_Killed_3.wav";
    
    /**
     * Sound played when a vampires collides with a pistolero
     */
    public static final String VAMPIRE_PISTOLERO_COLLISION = SOUNDS_DIRECTORY+"Vampire-Pistolero_Collision.mp3";
    
    /**
     * Sound played when pistolero balls are reloaded
     */
    public static final String RELOAD_BALLS = SOUNDS_DIRECTORY+"Reload_Balls.mp3";
    
    /**
     * Sound played when all vampires are killed
     */
    public static final String APPLAUSE = SOUNDS_DIRECTORY+"Applause.mp3";
    
    /**
     * Sound played when a pistolero dies after bein hit by a vampire
     */
    public static final String FAIL = SOUNDS_DIRECTORY+"Fail.mp3";
    
    /**
     * Sound played when a pistolero eats food
     */
    public static final String PISTOLERO_EAT = SOUNDS_DIRECTORY+"Pistolero_Eat.mp3";
    
    /**
     * Sound played when a vampire eats food
     */
    public static final String VAMPIRE_EAT = SOUNDS_DIRECTORY+"Vampire_Eat.mp3";
    
    /**
     * Create a new sound and play it
     * @param s name of the sound
     * @return sound
     */
    public static MediaPlayer createSound(String s) {
        MediaPlayer res;
        try {
            res = new MediaPlayer(new Media(new File(s).toURI().toString()));
            res.volumeProperty().bind(LeftBox.soundsVolumeProperty());
            res.muteProperty().bind(LeftBox.muteCheckedProperty());
        }
        catch (Exception exc) {
            System.err.println(exc);
            exc.printStackTrace();
            return null;
        }
        return res;
    }
    
    
    
}
