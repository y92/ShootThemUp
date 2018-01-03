package shootthemup.obstacle;

import shootthemup.interfaces.AutoAnimable;
import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import shootthemup.box.LeftBox;

/**
 * Class for helixes
 */
public final class Helix extends CircleObstacle implements AutoAnimable {
    
    
    /**
     * Prefix used to indicate where graphical elements are located
     */
    private static final String URL_PREFIX = "graphics/helix/";
    
    /**
     * Pattern used to represent a black helix
     */
    private static final ImagePattern BLACK_HELIX = new ImagePattern(new Image(URL_PREFIX+"black.png"));
    
    /**
     * Pattern used to represent a blue helix
     */
    private static final ImagePattern BLUE_HELIX = new ImagePattern(new Image(URL_PREFIX+"blue.png"));
    
    /**
     * Pattern used to represent a fuchsia helix
     */
    private static final ImagePattern FUCHSIA_HELIX = new ImagePattern(new Image(URL_PREFIX+"fuchsia.png"));
    
    /**
     * Pattern used to represent a green helix
     */
    private static final ImagePattern GREEN_HELIX = new ImagePattern(new Image(URL_PREFIX+"green.png"));
    
    /**
     * Pattern used to represent an orange helix
     */
    private static final ImagePattern ORANGE_HELIX = new ImagePattern(new Image(URL_PREFIX+"orange.png"));
    
    /**
     * Pattern used to represent a red helix
     */
    private static final ImagePattern RED_HELIX = new ImagePattern(new Image(URL_PREFIX+"red.png"));
    
    /**
     * Pattern used to represent a white helix
     */
    private static final ImagePattern WHITE_HELIX = new ImagePattern(new Image(URL_PREFIX+"white.png"));
    
    /**
     * Pattern used to represent a yellow helix
     */
    private static final ImagePattern YELLOW_HELIX = new ImagePattern(new Image(URL_PREFIX+"yellow.png"));
    
    
    /**
     * Array with all possible colors
     */
    private static final ImagePattern[] PATTERNS = new ImagePattern[]{ BLACK_HELIX, BLUE_HELIX, FUCHSIA_HELIX, GREEN_HELIX, ORANGE_HELIX,
                                                                        RED_HELIX, WHITE_HELIX, YELLOW_HELIX };
    
    /**
     * Motion of the helix
     */
    private final AnimationTimer motion;
    
    /**
     * Rotation speed
     */
    private final DoubleProperty rotationSpeedProperty;
    
    
    /**
     * Creates a new instance of Helix
     * @param x X axis
     * @param y Y axis
     */
    public Helix(double x, double y) {
        super(x,y);
        setFill(PATTERNS[(int) (PATTERNS.length*Math.random())]);
        
        rotationSpeedProperty = new SimpleDoubleProperty();
        rotationSpeedProperty.bind(Bindings.multiply(Math.random() < 0.5 ? 1 : -1, LeftBox.multiplyHelixesRotationSpeedProperty()));
        
        motion = new AnimationTimer() {
          
            @Override
            public void handle(long l) {
                setRotate(getRotate()+rotationSpeedProperty.get());
            }
        };
    }
    
       

    @Override
    public void startMotion() {
        motion.start();
    }
    
    @Override
    public void stopMotion() {
        motion.stop();
    }

    

    
}
