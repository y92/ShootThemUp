package shootthemup.obstacle;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.StrokeTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import shootthemup.interfaces.TransitionModifiable;

/**
 * Class for circle stones
 */
public final class CircleStone extends CircleMovableObstacle implements TransitionModifiable {
    
    /**
     * Transition which modifies this stone
     */
    private final ParallelTransition pt;
    
    /**
     * Create a new instance of CircleStone
     * @param x center x axis
     * @param y center y axis
     */
    public CircleStone(double x, double y) {
        super(x,y);
        
        
        Color fromStroke = Color.color(Math.random(), Math.random(), Math.random());
        Color toStroke = Color.color(Math.random(), Math.random(), Math.random());
        
        Color fromFill = Color.color(Math.random(), Math.random(), Math.random());
        Color toFill = Color.color(Math.random(), Math.random(), Math.random());
        
        
        
        setStroke(fromStroke);
        setFill(fromFill);
                
        

        
        StrokeTransition st = new StrokeTransition(Duration.millis(500),this);
        st.setFromValue(fromStroke);
        st.setToValue(toStroke);
        st.setAutoReverse(true);
        st.setCycleCount(Animation.INDEFINITE);
        
               
        
        FillTransition ft = new FillTransition(Duration.millis(500),this);
        ft.setFromValue(fromFill);
        ft.setToValue(toFill);
        ft.setAutoReverse(true);
        ft.setCycleCount(Animation.INDEFINITE);
        
        pt = new ParallelTransition(st,ft);

    }
    
    @Override
    public void stopTransition() {
        pt.stop();
    }
    
    @Override
    public void playTransition() {
        pt.play();
    }
    
    @Override 
    public void pauseTransition() {
        pt.pause();
    }
    
    

    
}
