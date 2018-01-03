package shootthemup.interfaces;

import java.util.ArrayList;
import shootthemup.Graphical;

/**
 * All items which can be pushed and pulled by pistoleros
 */
public interface Movable extends Obstacle {
    
    /**
     * push to the left
     * @param x width
     */
    public default void pushLeft(double x) {
        
        ArrayList<Obstacle> obstacles = Graphical.getObstacles();
        obstacles.addAll(Graphical.getVampires());
        obstacles.addAll(Graphical.getFeed());

        for (Obstacle o : obstacles) {
            
            if (o.collisionRight(this)) {
                setToRight(o);
                return;
            }
        }
        
        for (Movable m : Graphical.getMovables()) {
            
            if (m == this) continue;
            
            if (m.collisionRight(this)) {
                setToRight(m);
                m.pushLeft(x);
                return;
            }
            
        }
        
        moveToX(getRefX()-x);
    }
    
    
    /**
     * push to the right
     * @param x width
     */
    public default void pushRight(double x) {
        
        ArrayList<Obstacle> obstacles = Graphical.getObstacles();
        obstacles.addAll(Graphical.getVampires());
        obstacles.addAll(Graphical.getFeed());

        for (Obstacle o : obstacles) {
            
            if (o.collisionLeft(this)) {
                setToLeft(o);
                return;
            }
        }
                
        for (Movable m : Graphical.getMovables()) {
            
            if (m == this) continue;
            
            if (m.collisionLeft(this)) {
                setToLeft(m);
                m.pushRight(x);
                return;
            }
            
        }
        
        
        moveToX(getRefX()+x);

    }
    
    /**
     * push to the top
     * @param y height
     */
    public default void pushTop(double y) {
        
        ArrayList<Obstacle> obstacles = Graphical.getObstacles();
        obstacles.addAll(Graphical.getVampires());
        obstacles.addAll(Graphical.getFeed());
        
        for (Obstacle o : obstacles) {    
            
            if (o.collisionBottom(this)) {
                setUnder(o);
                return;
            }
        }
                       
        for (Movable m : Graphical.getMovables()) {
            
            if (m == this) continue;
            
            if (m.collisionBottom(this)) {
                setUnder(m);
                m.pushTop(y);
                return;
            }
            
        }
        
        moveToY(getRefY()-y);
    }
    

    /**
     * push to the bottom
     * @param y height
     */
    public default void pushBottom(double y) {
        
        ArrayList<Obstacle> obstacles = Graphical.getObstacles();
        obstacles.addAll(Graphical.getVampires());
        obstacles.addAll(Graphical.getFeed());
        
        for (Obstacle o : obstacles) {
                        
            if (o.collisionTop(this)) {
                setOver(o);
                return;
            }    
        }
                
        for (Movable m : Graphical.getMovables()) {
            
            if (m == this) continue;
            
            if (m.collisionTop(this)) {
                setOver(m);
                m.pushBottom(y);
                return;
            }   
        }

        moveToY(getRefY()+y);
    }
    
    /**
     * pull horizontally
     * @param x width
     */
    public void pullX(double x);
    
    /**
     * pull vertically
     * @param y height
     */
    public void pullY(double y);
    
    /**
     * set this Movable to the left of the Item given as argument
     * @param i item
     */
    public default void setToLeft(Item i) {
        moveToX(i.getMinX()-getRefSize());
    }
    
    /**
     * set this Movable to the right of the Item given as argument
     * @param i item
     */
    public void setToRight(Item i);
    
    /**
     * set this Movable over the Item given as argument
     * @param i item
     */    
    public default void setOver(Item i) {
        moveToY(i.getMinY()-getRefSize());
    }
    

    /**
     * set this Movable under the Item given as argument
     * @param i item
     */
    public void setUnder(Item i);
    
    /**
     * move this Movable
     * @param x new x axis
     */
    public void moveToX(double x);
    
    /**
     * move this Movable
     * @param y new y axis
     */
    public void moveToY(double y);
}
