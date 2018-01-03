package shootthemup.obstacle;

import shootthemup.interfaces.Item;
import shootthemup.interfaces.Movable;

/**
 * Class for squared movable helixes
 */
public abstract class SquaredMovableObstacle extends SquaredObstacle implements Movable {
    
    
    /**
     * Creates a new squared movable obstacle
     * @param x top-left corner x axis
     * @param y top-left corner y axis
     */
    public SquaredMovableObstacle(double x, double y) {
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
        setX(i.getMaxX());
    }
    
    @Override
    public void setUnder(Item i) {
        setY(i.getMaxY());
    }
    
    @Override
    public void moveToX(double x) {
        setX(x);
    }
    
    @Override    
    public void moveToY(double y) {
        setY(y);
    }
        
}
