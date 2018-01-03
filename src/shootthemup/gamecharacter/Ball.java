package shootthemup.gamecharacter;

import shootthemup.interfaces.AutoAnimable;
import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shootthemup.Graphical;
import shootthemup.interfaces.Food;
import shootthemup.interfaces.Obstacle;

/**
 * Represents balls shot by pistoleros
 */
public final class Ball extends Circle implements AutoAnimable {
    
    
    /**
     * Represents the direction of the ball
     */
    private final double angle;
    
    /**
     * Represents the motion of the ball
     */
    private final AnimationTimer motion;
    
    /**
     * Speed of the ball
     */
    private final double speed;
    
    /**
     * Pistolero which shot this ball
     */
    private final Pistolero shooter;
    
    /**
     * Starting X axis
     */
    private final double start_x;
    
    /**
     * Starting Y axis
     */
    private final double start_y;
    
    /**
     * Creates an instance of Ball
     * @param x X axis
     * @param y Y axix
     * @param angle direction of the ball
     * @param speed ball motion speed
     * @param pisto pistolero which shot this ball
     */
    public Ball(double x, double y, double angle, double speed, Pistolero pisto) {
        
        super(x,y,Graphical.getCircleElementsSize()/4);
        
        this.shooter = pisto;
        
        this.start_x = x;
        this.start_y = y;
        
        setStroke(Color.RED);
        setFill(Color.GOLD);
        this.angle = angle;
        this.speed = speed;
        
        motion=new AnimationTimer() {
        
            @Override
            public void handle(long l) {
            
                setCenterX(getCenterX()+speed*Math.cos(Math.toRadians(angle)));
                setCenterY(getCenterY()+speed*Math.sin(Math.toRadians(angle)));
                
                for (Obstacle o : Graphical.getObstaclesWithMovables()) {
                    o.collision(Ball.this);
                }
                
                for (Vampire v : Graphical.getVampires()) {
                    v.collision(Ball.this);
                }
                
                for (Food f : Graphical.getFeed()) {
                    f.collision(Ball.this);
                }
            }
        
        };
    }
    
    @Override
    public void startMotion() {
        motion.start();
    }
    
    @Override
    public void stopMotion() {
        motion.stop();
    }
    
    @Override
    public double getRefSize() {
        return getRadius();
    }
    
    @Override
    public double getRefX() {
        return getCenterX();
    }
    
    @Override
    public double getRefY() {
        return getCenterY();
    }
    
    @Override
    public Bounds getRefBounds() {
        return getBoundsInParent();
    }
    
    @Override
    public double getMinX() {
        return getCenterX()-getRadius();
    }
    
    @Override
    public double getMinY() {
        return getCenterY()-getRadius();
    }
        
    /**
     * Starting position of this ball
     * @return starting x axis
     */
    public double getStartX() {
        return start_x;
    }
    
    /**
     * Starting position of this ball
     * @return starting y axis
     */
    public double getStartY() {
        return start_y;
    }
    
    /**
     * Increment score of pistolero which shot this ball
     * @param n number of points to add
     */
    public void incrScorePistolero(long n) {
        shooter.incrScore(n);
    }
    
    /**
     * Increase number of male vampires killed by this pistolero
     */
    public void incrMaleVampiresKilled() {
        shooter.incrMaleVampiresKilled();
    }
    
    /**
     * Increase number of female vampires killed by this pistolero
     */
    public void incrFemaleVampiresKilled() {
        shooter.incrFemaleVampiresKilled();
    }
    
    
}
