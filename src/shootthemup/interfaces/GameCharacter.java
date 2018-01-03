package shootthemup.interfaces;

/**
 * Some common attribute of all characters of the game, to handle more easily collisions with obstacles
 */
public interface GameCharacter extends Item {
        
    /**
     * Reference speed of the character
     * @return speed of this character
     */
    public double getRefSpeed();
    
    /**
     * Enables this character to eat food
     * @param f food to eat
     */
    public default void eat(Food f){
        f.setEatenBy(this);
    }
    
    /**
     * Regenerates this character
     */
    public default void regenerate() {}
    
}
