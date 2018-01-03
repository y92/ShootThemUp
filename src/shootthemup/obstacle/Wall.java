package shootthemup.obstacle;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import shootthemup.Graphical;
import shootthemup.box.LeftBox;

/**
 * Class for walls
 */
public final class Wall extends SquaredObstacle {
    
    
    /**
     * LinearGradient for blue walls
     */
    private final LinearGradient blue;
    
    /**
     * LinearGradient for red walls
     */
    private final LinearGradient red;

    /**
     * Creates a new instance of Wall
     * @param x X axis (top left corner)
     * @param y Y Axis (top left corner)
     */
    public Wall(double x, double y) {
        super(x,y);

        int SIZE = Graphical.getElementsSize();
        red = new LinearGradient(x,y,x+SIZE/2,y+SIZE/2,false,CycleMethod.REFLECT, new Stop[]{ new Stop(0, Color.BLACK), new Stop(1,Color.RED)});
        blue = new LinearGradient(x,y,x+SIZE/2,y+SIZE/2,false,CycleMethod.REFLECT, new Stop[]{ new Stop(0, Color.BLACK), new Stop(1,Color.BLUE)});
        
        fillProperty().bind(Bindings.when(LeftBox.redWallsSelectedProperty()).then(red).otherwise(blue));
        setStroke(Color.WHITE);
    }
    
    

    
    
}
