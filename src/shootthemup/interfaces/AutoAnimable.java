package shootthemup.interfaces;


/**
 * Objects animated automatically
 */
public interface AutoAnimable extends Item {
      
    /**
     * start motion of object
     */
    public void startMotion();
    
    /**
     * stop motion of object
     */
    public void stopMotion();
}
