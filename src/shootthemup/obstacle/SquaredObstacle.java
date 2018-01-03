package shootthemup.obstacle;

import shootthemup.interfaces.Obstacle;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import shootthemup.Graphical;

/**
 * Represents squared obstacles in the arena (walls)
 */
public abstract class SquaredObstacle extends Rectangle implements Obstacle {
    

    /**
     * Creates a new squared obstacle
     * @param x top-left corner x axis
     * @param y top-left corner y axis
     */
    public SquaredObstacle(double x, double y) {
        super(x,y,Graphical.getElementsSize(),Graphical.getElementsSize());
    }
    
                        
    @Override
    public double getMinX() {
        return getX();
    }
    
    @Override
    public double getMinY() {
        return getY();
    }
        
    @Override
    public double getRefSize() {
        return getWidth(); // width = height, as Wall is squared
    }
    
    @Override
    public double getRefX() {
        return getX();
    }
    
    @Override
    public double getRefY() {
        return getY();
    }
    
    @Override
    public Bounds getRefBounds() {
        return getBoundsInParent();
    }
    
    
}
