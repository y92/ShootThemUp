package shootthemup.interfaces;

import javafx.geometry.Bounds;

/**
 * Any graphical item present in the interface
 */
public interface Item {
    
        
    /**
     * Minimal current coordinates of the item
     * @return x axis
     */
    public double getMinX();
    
    /**
     * Minimal current coordinates of the item
     * @return y axis
     */
    public double getMinY();
    
    /**
     * Maximal coordinates of the item
     * @return x axis
     */
    public default double getMaxX() {
        return getRefX()+getRefSize();
    }
    
    /**
     * Maximal coordinates of the item
     * @return y axis
     */
    public default double getMaxY() {
        return getRefY()+getRefSize();
    }
    
        
    /**
     * Reference current coordinatex of the item
     * @return x axis
     */
    public double getRefX();

    /**
     * Reference current coordinates of the item
     * @return y axis
     */
    public double getRefY();
    
    
    /**
     * Reference size of the item
     * @return size
     */
    public double getRefSize();

    /**
     * Reference current bounds of the item
     * @return bounds of this obstacle
     */
    public Bounds getRefBounds();
}
