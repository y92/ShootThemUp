package shootthemup.interfaces;

import shootthemup.Graphical;
import shootthemup.gamecharacter.Ball;

/**
 * Any obstacle type which can be present in the arena
 */
public interface Obstacle  extends Item {
        
    /**
     * Handles balls collisions with this obstacle
     * @param b ball
     */
    public default void collision(Ball b) {    
        if (b.getRefBounds().intersects(getRefBounds())) {
            b.stopMotion();
            Graphical.remove(b);
        }
    }
    
    /**
     * Tests if a character collides with this obstacle on the left
     * @param c character
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionLeft(GameCharacter c) {
        if (c == null) return false;
        return c.getMaxX() >= getMinX() && c.getMinX() < getMinX() && c.getMaxY() > getMinY() && c.getMinY() < getMaxY();
    }
    
    /**
     * Tests if a character collides with this obstacle on the right
     * @param c character
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionRight(GameCharacter c) {    
        if (c == null) return false;
        return c.getMinX() <= getMaxX() && c.getMaxX() > getMaxX() && c.getMaxY() > getMinY() && c.getMinY() < getMaxY();        
    }
    
    /**
     * Tests if a character collides with this obstacle on the top
     * @param c character
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionTop(GameCharacter c) {    
        if (c == null) return false;
        return c.getMaxY() >= getMinY() && c.getMinY() < getMinY() && c.getMaxX() > getMinX() && c.getMinX() < getMaxX();
    }
    
    /**
     * Tests if a character collides with this obstacle on the bottom
     * @param c character
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionBottom(GameCharacter c) {    
        if (c == null) return false;
        return c.getMinY() <= getMaxY() && c.getMaxY() > getMaxY() && c.getMaxX() > getMinX() && c.getMinX() < getMaxX();
    }

    
    /**
     * Tests if a movable collides with this obstacle on the left
     * @param m movable
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionLeft(Movable m) {    
        if (m == null) return false;
        return m.getMaxX() >= getMinX() && m.getMinX() < getMinX() && m.getMaxY() > getMinY() && m.getMinY() < getMaxY();
    }
    
    /**
     * Tests if a movable collides with this obstacle on the right
     * @param m movable
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionRight(Movable m) {    
        if (m == null) return false;
        return m.getMinX() <= getMaxX() && m.getMaxX() > getMaxX() && m.getMaxY() > getMinY() && m.getMinY() < getMaxY();
    }
    
    /**
     * Tests if a movable collides with this obstacle on the top
     * @param m movable
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionTop(Movable m) {    
        if (m == null) return false;
        return m.getMaxY() >= getMinY() && m.getMinY() < getMinY() && m.getMaxX() > getMinX() && m.getMinX() < getMaxX();
    }
    
    /**
     * Tests if a movable collides with this obstacle on the bottom
     * @param m movable
     * @return true in case of collision, false otherwise
     */
    public default boolean collisionBottom(Movable m) {
        if (m == null) return false;
        return m.getMinY() <= getMaxY() && m.getMaxY() > getMaxY() && m.getMaxX() > getMinX() && m.getMinX() < getMaxX();    
    }
 

}
