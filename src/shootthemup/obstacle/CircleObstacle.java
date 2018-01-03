package shootthemup.obstacle;

import shootthemup.interfaces.Obstacle;
import javafx.geometry.Bounds;
import javafx.scene.shape.Circle;
import shootthemup.Graphical;

/**
 * Represents circle obstacles, such as helixes or vampires
 */
public abstract class CircleObstacle extends Circle implements Obstacle {
    
    /**
     * Creates a new circle obstacle
     * @param x center x axis
     * @param y center y axis
     */
    public CircleObstacle(double x, double y) {
        super(x+Graphical.getCircleElementsSize(),y+Graphical.getCircleElementsSize(),Graphical.getCircleElementsSize());
    }
    
                                            
    @Override
    public double getMinX() {
        return getCenterX()-getRadius();
    }
    
    @Override
    public double getMinY() {
        return getCenterY()-getRadius();
    }
        
    @Override
    public double getRefSize() {
        return getRadius();
    }
    
    @Override
    public double getRefX() {
        return getCenterX();
    }
    
    @Override
    public double getRefY() {
        return getCenterY();
    }
    
    @Override
    public Bounds getRefBounds() {
        return getBoundsInParent();
    }
    
}
