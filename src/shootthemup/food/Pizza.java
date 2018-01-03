package shootthemup.food;

import shootthemup.interfaces.Food;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import shootthemup.Graphical;
import shootthemup.interfaces.GameCharacter;
import shootthemup.obstacle.CircleObstacle;

/**
 * Class for pizzas (which can be eaten by characters off the arena)
 */
public final class Pizza extends CircleObstacle implements Food {
    
    /**
     * ImagePattern which represents pizzas
     */
    private static final ImagePattern PIZZA = new ImagePattern(new Image(FOOD_DIRECTORY+"pizza.png"));
    
    /**
     * Indicates if this pizza has been eaten by a character
     */
    private boolean has_been_eaten;
    
    /**
     * Create a new instance of Pizza
     * @param x x axis
     * @param y y axis
     */
    public Pizza(double x, double y) {
        super(x,y);
        setFill(PIZZA);
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
            

        RotateTransition rt = new RotateTransition(Duration.millis(500),this);
        rt.setToAngle(360*(Math.random() < 0.5 ? 1 : -1));
            
        ParallelTransition pt = new ParallelTransition(ft,st,rt);
        
        pt.setOnFinished(f -> {
            Graphical.remove(Pizza.this);
            c.regenerate();
        });
        
        pt.play();

    }
    
}
