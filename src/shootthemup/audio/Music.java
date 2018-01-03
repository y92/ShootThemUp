package shootthemup.audio;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import shootthemup.box.LeftBox;

/**
 * Class for music files
 */
public class Music {
    
    /**
     * Path of directory which contains music files
     */
    private static final String MUSIC_DIRECTORY = "src/musics/";
    
    /**
     * <p>Music used under license <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons</a></p> 
     * Arc North - <em>Heroic</em>
     */
    public static final String MUSIC_1 = MUSIC_DIRECTORY+"Music1.mp3";
    
    /**
     * Music used under license <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons</a>
     * Kasbo - <em>Horizon</em>
     */
    public static final String MUSIC_2 = MUSIC_DIRECTORY+"Music2.mp3";
    
    /**
     * Music used under license <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons</a>
     * Tobu - <em>Candyland</em>
     */
    public static final String MUSIC_3 = MUSIC_DIRECTORY+"Music3.mp3";
    
    /**
     * Music used under license <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons</a>
     * From the dust - <em>Supernova</em>
     */
    public static final String MUSIC_4 = MUSIC_DIRECTORY+"Music4.mp3";
    
    /**
     * Default music
     */
    public static final String MUSIC_DEFAULT = MUSIC_1;
    
    /**
     * Indicate current music
     */
    private static MediaPlayer currentMusic;
    
    
    /**
     * Creates a new media player for a music 
     * @param s music name
     * @return media player with properties initialized
     */
    private static MediaPlayer createMusic(String s) {
            
            MediaPlayer res;
            try {
                res = new MediaPlayer(new Media(new File(s).toURI().toString()));
            }
            catch (Exception exc) {
                System.err.println(exc);
                exc.printStackTrace();
                return null;
            }
            res.volumeProperty().bind(LeftBox.musicVolumeProperty());
            res.muteProperty().bind(LeftBox.muteCheckedProperty());
            return res;
    }

    
    /**
     * Plays a music
     * @param s music name
     */
    public static void playMusic(String s) {
        
        if (currentMusic != null) currentMusic.stop();
        
        
        try {
            currentMusic = createMusic(s);
            currentMusic.setCycleCount(MediaPlayer.INDEFINITE);
            currentMusic.play();
        }
        catch (Exception exc) {
            System.err.println(exc);
            exc.printStackTrace();           
        }
    }
    
    /**
     * Stops current music
     */
    public static void stopMusic() {
        if (currentMusic != null) currentMusic.stop();
    }
    
}
