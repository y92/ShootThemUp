package shootthemup.interfaces;


/**
 * Represents any type of food
 */
public interface Food extends Obstacle {

    /**
     * URL Prefix of picture files which represent food
     */
    public static final String FOOD_DIRECTORY = "graphics/food/";
   
    
    /**
     * This function is called when this food is eaten by a character
     * @param c Character which eat this food
     */
    public void setEatenBy(GameCharacter c);
    
}
