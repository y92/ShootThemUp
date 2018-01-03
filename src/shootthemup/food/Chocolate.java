package shootthemup.food;

import shootthemup.interfaces.Food;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import shootthemup.Graphical;
import shootthemup.interfaces.GameCharacter;
import shootthemup.obstacle.SquaredObstacle;

/**
 * Class for chocolate bars (which can be eaten by characters of the arena)
 */
public final class Chocolate extends SquaredObstacle implements Food {
    
    /**
     * All possible colors for chocolate
     */
    public static enum ChocolateColor { 
        /**
         * Milk chocolate
         */
        MILK, 
        /**
         * Dark chocolate
         */
        DARK,
        /**
         * White chocolate
         */
        WHITE}
    
    /**
     * Indicates if this chocolate has been eaten by a character
     */
    private boolean has_been_eaten;
    
    /**
     * ImagePattern which represents milk chocolate
     */
    private static final ImagePattern MILK_CHOCOLATE = new ImagePattern(new Image(FOOD_DIRECTORY+"milk_chocolate.png"));
    
    /**
     * ImagePattern which represents dark chocolate
     */
    private static final ImagePattern DARK_CHOCOLATE = new ImagePattern(new Image(FOOD_DIRECTORY+"dark_chocolate.png"));
    
    /**
     * ImagePattern which represents white chocolate
     */
    private static final ImagePattern WHITE_CHOCOLATE = new ImagePattern(new Image(FOOD_DIRECTORY+"white_chocolate.png"));
    
    /**
     * Create a new instance of Chocolate
     * @param x x axis
     * @param y y axis
     * @param cc chocolate color
     */
    public Chocolate(int x, int y, ChocolateColor cc) {
        super(x,y);
        switch (cc) {
            case DARK:
                setFill(DARK_CHOCOLATE);
                break;
            case WHITE:
                setFill(WHITE_CHOCOLATE);
                break;
            default:
                setFill(MILK_CHOCOLATE);
                break;
        }
    }
    
    /**
     * Get a random color of chocolate
     * @return randomized color
     */
    public static ChocolateColor randomColor() {
        double tmp = 3*Math.random();
        if (tmp < 1) return ChocolateColor.MILK;
        if (tmp < 2) return ChocolateColor.DARK;
        return ChocolateColor.WHITE;
    }
    
    @Override
    public void setEatenBy(GameCharacter c) {
        
        if (has_been_eaten) return;
        has_been_eaten = true;
                
        FadeTransition ft = new FadeTransition(Duration.millis(500),this);
        ft.setToValue(0);
        
        ScaleTransition st = new ScaleTransition(Duration.millis(500),this);
        st.setToX(0);
        st.setToY(0);

        ParallelTransition pt = new ParallelTransition(ft,st);
        
        pt.setOnFinished(f -> {
            Graphical.remove(Chocolate.this);
            c.regenerate();
        });
        
        pt.play();
    }
    
    
}
