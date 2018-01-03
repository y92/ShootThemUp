package shootthemup.interfaces;


/**
 * Items which can be modified through transitions
 */
public interface TransitionModifiable extends Item {

    
    /**
     * Play transition
     */
    public void playTransition();
    
    /**
     * Pause transition
     */
    public void pauseTransition();
    
    /**
     * Stop transition
     */
    public void stopTransition();
    
}
