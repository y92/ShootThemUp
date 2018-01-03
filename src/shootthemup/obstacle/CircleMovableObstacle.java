package shootthemup.obstacle;

import shootthemup.interfaces.Item;
import shootthemup.interfaces.Movable;

/**
 * Class for circle movable obstacles
 */
public abstract class CircleMovableObstacle extends CircleObstacle implements Movable {
    
    /**
     * Create a new circle movable obstacle
     * @param x center x axis
     * @param y center y axis
     */
    public CircleMovableObstacle(double x, double y) {
        super(x,y);
    }
            
    
    @Override
    public void pullX(double x) {
        //TODO : implement the function
    }
    
    @Override
    public void pullY(double y) {
        //TODO : implement the function
    }
    
    @Override
    public void setToRight(Item i) {
        setCenterX(i.getMaxX()+getRadius());
    }
    
    @Override
    public void setUnder(Item i) {
        setCenterY(i.getMaxY()+getRadius());
    }
        
    
    @Override
    public void moveToX(double x) {
        setCenterX(x);
    }
    
    @Override
    public void moveToY(double y) {
        setCenterY(y);
    }
    
}
