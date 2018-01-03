package shootthemup.gamecharacter;

import shootthemup.interfaces.GameCharacter;
import shootthemup.interfaces.AutoAnimable;
import shootthemup.audio.Sound;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.effect.Effect;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import shootthemup.Graphical;
import shootthemup.box.LeftBox;
import shootthemup.interfaces.Food;
import shootthemup.obstacle.CircleObstacle;
import shootthemup.interfaces.Obstacle;

/**
 * Class for vampires
 */
public final class Vampire extends CircleObstacle implements GameCharacter, AutoAnimable {
    
    /**
     * Number of vampires already created
     */
    private static int nb_vampires = 0;
    
    /**
     * ID of this vampire
     */
    private final int vampire_id;
    
    /**
     * Prefix used to indicate where graphics elements are located
     */
    private static final String URL_PREFIX = "graphics/vampire/";
    
    /**
     * Pattern used to represent neutral vampires
     */
    private static final ImagePattern NEUTRAL_VAMPIRE = new ImagePattern(new Image(URL_PREFIX+"neutral.png"));
    
    /**
     * Pattern used to represent female vampires
     */
    private static final ImagePattern FEMALE_VAMPIRE = new ImagePattern(new Image(URL_PREFIX+"female.png"));
    
    /**
     * Pattern used to represent male vampires
     */
    private static final ImagePattern MALE_VAMPIRE = new ImagePattern(new Image(URL_PREFIX+"male.png"));
    
    /**
     * Indicates the gender of the vampire (male, female or neutral)
     */
    private final ObjectProperty<Gender> genderProperty;
    
    /**
     * Indicates the speed of the vampire
     */
    private final DoubleProperty speedProperty;
    
    /**
     * Angle direction of the vampire (degrees)
     */
    private final DoubleProperty directionProperty;
    
    /**
     * Angle direction of the vampire (radians)
     */
    private final DoubleProperty radiansDirectionProperty;
    

    /**
     * Animation of the vampire
     */
    private final AnimationTimer motion;
    
    /**
     * Visual effect of a male vampire killed
     */
    private final Effect MALE_VAMPIRE_KILLED;
    
    /**
     * Visual effect of a female vampire killed
     */
    private final Effect FEMALE_VAMPIRE_KILLED;
    
    /**
     * Visual effect of a neutral vampired killed
     */
    private final Effect NEUTRAL_VAMPIRE_KILLED;
    

    /**
     * Indicates if this vampire is dead or not
     */
    private final BooleanProperty deadProperty;
    
    /**
     * Create a new instance of Vampire
     * @param x X axis of the vampire
     * @param y Y axis of the vampire
     * @param gender gender of the vampire
     */
    public Vampire(double x, double y, Gender gender) {
        
        super(x,y);

        int RADIUS = Graphical.getCircleElementsSize();
        NEUTRAL_VAMPIRE_KILLED = new ImageInput(new Image(URL_PREFIX+"neutral_killed.png",RADIUS,RADIUS,true,true));
        FEMALE_VAMPIRE_KILLED = new ImageInput(new Image(URL_PREFIX+"female_killed.png",RADIUS,RADIUS,true,true));
        MALE_VAMPIRE_KILLED = new ImageInput(new Image(URL_PREFIX+"male_killed.png",RADIUS,RADIUS,true,true));
        
        vampire_id = ++nb_vampires;
        genderProperty = new SimpleObjectProperty<>(gender);
        
        fillProperty().bind(Bindings.createObjectBinding(() -> {
            switch(genderProperty.get()) {
                case MALE:
                    return MALE_VAMPIRE;
                case FEMALE:
                    return FEMALE_VAMPIRE;
                default:
                    return NEUTRAL_VAMPIRE;
            }
        },
        genderProperty));
        
        speedProperty = new SimpleDoubleProperty(1+5*Math.random());
        speedProperty.bind(Bindings.multiply(1+5*Math.random(), LeftBox.multiplyVampiresSpeedProperty()));
        
        directionProperty = new SimpleDoubleProperty(0) {
        
            @Override
            public void set(double x) {
                synchronized(Vampire.this) {
                    double tmp = x;
                    while (tmp < 0) tmp += 360;
                    while (tmp >= 360) tmp -= 360;
                    super.set(tmp);
                }
            }
            
        };
        
        deadProperty = new SimpleBooleanProperty(false);
        
        radiansDirectionProperty = new SimpleDoubleProperty();
        radiansDirectionProperty.bind(Bindings.createDoubleBinding(() -> {
        
            return Math.toRadians(directionProperty.get());
        
        }, directionProperty));
        
        motion = new AnimationTimer() {
            
            @Override
            public void handle(long l) {
                
                for (Pistolero p : Graphical.getPistoleros()) {
                    if (collisionLeft(p)
                            || collisionRight(p) 
                            || collisionTop(p) 
                            || collisionBottom(p)) {
                            
                        p.incrLevel();
                        if (p.getLevel() >= 3) {
                            Graphical.remove(p);
                            p.setDisable(true);
                            Vampire v = new Vampire(p.getStartX()-RADIUS,p.getStartY()-RADIUS, (Math.random() < 0.5) ? Gender.MALE : Gender.FEMALE);
                            Graphical.add(v);
                            v.startMotion();
                            
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Pistolero dead");
                            alert.setHeaderText("Pistolero "+p.getPistoleroId()+" has been hit 3 consectuive times by a vampire without eating");
                            int s;
                            if ((s=Graphical.getPistoleros().size()) <= 0) {
                                alert.setContentText("All pistoleros are dead. Game Over");
                            }
                            else {
                                alert.setContentText("There are "+s+"pistoleros left in the arena");
                            }
                            MediaPlayer sound = Sound.createSound(Sound.FAIL);
                            if (sound != null) {
                                sound.play();
                                alert.setOnCloseRequest(f -> { sound.stop(); });
                            }
                            alert.show();
                        }
                        
                        else {

                            p.relocateStartPosition();
                            MediaPlayer sound = Sound.createSound(Sound.VAMPIRE_PISTOLERO_COLLISION);
                            if (sound != null) sound.play();
                        }
                        return;
                    }
                }
                
                for (Obstacle o : Graphical.getObstaclesWithMovables()) {
                    if (o.collisionBottom(Vampire.this) && directionProperty.get() == 270) {
                        setCenterY(o.getMaxY()+getRadius());
                        turn();
                        return;
                    }
                    
                    if (o.collisionTop(Vampire.this) && directionProperty.get() == 90) {
                        setCenterY(o.getMinY()-getRadius());
                        turn();
                        return;
                    }
                    
                    if (o.collisionLeft(Vampire.this) && directionProperty.get() == 0) {
                        setCenterX(o.getMinX()-getRadius());
                        turn();
                        return;
                    }
                    
                    if (o.collisionRight(Vampire.this) && directionProperty.get() == 180) {
                        setCenterX(o.getMaxX()+getRadius());
                        turn();
                        return;
                    }
                    
                }
                
                for (Vampire v : Graphical.getVampires()) {
                    if (v == Vampire.this) continue;
                    
                    if ((v.collisionLeft(Vampire.this) && directionProperty.get() == 0)
                         ||(v.collisionRight(Vampire.this) && directionProperty.get() == 180)
                         ||(v.collisionTop(Vampire.this) && directionProperty.get() == 90)
                         ||(v.collisionBottom(Vampire.this) && directionProperty.get() == 270)) {
                    
                        turnBack();
                        return;
                    
                    }
                    
                }
                
                for (Food f : Graphical.getFeed()) {

                    if ((f.collisionLeft(Vampire.this) && directionProperty.get() == 0)
                         ||(f.collisionRight(Vampire.this) && directionProperty.get() == 180)
                         ||(f.collisionTop(Vampire.this) && directionProperty.get() == 90)
                         ||(f.collisionBottom(Vampire.this) && directionProperty.get() == 270)) {
                    
                        eat(f);
                    
                    }

                    
                }
                
                setCenterX(getCenterX() + speedProperty.get()*Math.cos(radiansDirectionProperty.get()) );
                setCenterY(getCenterY() + speedProperty.get()*Math.sin(radiansDirectionProperty.get()) );

            }
            
        };
    }
    
    @Override
    public void eat(Food f) {
        f.setEatenBy(this);
        MediaPlayer sound = Sound.createSound(Sound.VAMPIRE_EAT);
        if (sound != null) sound.play();
    }
    
        
    @Override
    public void collision(Ball b) {

        int RADIUS = Graphical.getCircleElementsSize();

        
        if (b.getBoundsInParent().intersects(getBoundsInParent())) {
                   
            Effect tmp;
            
            stopMotion();
            b.stopMotion();
            Graphical.remove(b);
            
            if (deadProperty.get()) return;
            deadProperty.set(true);
            
        
            double tmp1 = Math.pow(getCenterX() - b.getStartX(), 2);
            double tmp2 = Math.pow(getCenterY() - b.getStartY(), 2);
            
            long dist = Math.round(Math.sqrt(tmp1 + tmp2));
            
            switch(genderProperty.get()) {
                case MALE:
                    tmp = new ImageInput(new Image(URL_PREFIX+"male_killed.png",RADIUS*2,RADIUS*2,true,true),getCenterX()-getRadius(),getCenterY()-getRadius());
                    break;
                case FEMALE:             
                    tmp = new ImageInput(new Image(URL_PREFIX+"female_killed.png",RADIUS*2,RADIUS*2,true,true),getCenterX()-getRadius(),getCenterY()-getRadius());
                    break;
                default:
                    tmp = new ImageInput(new Image(URL_PREFIX+"neutral_killed.png",RADIUS*2,RADIUS*2,true,true),getCenterX()-getRadius(),getCenterY()-getRadius());                    tmp = NEUTRAL_VAMPIRE_KILLED;
                    break;
            }
        
            setEffect(tmp);
            MediaPlayer sound = Sound.createSound(Sound.VAMPIRE_KILLED);
            if (sound != null) sound.play();
            
            FadeTransition ft = new FadeTransition(Duration.millis(500),this);
            ft.setToValue(0);
            
            ScaleTransition st = new ScaleTransition(Duration.millis(500),this);
            st.setToX(0);
            st.setToY(0);
            
            RotateTransition rt = new RotateTransition(Duration.millis(500),this);
            rt.setToAngle(360*(Math.random() < 0.5 ? 1 : -1));
            
            ParallelTransition pt = new ParallelTransition(ft,st,rt);
            pt.setOnFinished(e -> {
                b.incrScorePistolero(dist);
                Graphical.remove(this);
                switch(genderProperty.get()) {
                    case MALE:
                        b.incrMaleVampiresKilled();
                        break;
                    case FEMALE:
                        b.incrFemaleVampiresKilled();
                        break;
                    default:
                        break;
                }
                if (Graphical.getVampires().size() <=0) {
                    Alert al = new Alert(Alert.AlertType.INFORMATION);
                    al.setTitle("Congratulations !");
                    al.setHeaderText("All vampires have been killed");
                    String contentText="Scores :\n";
                    for (Pistolero p : Graphical.getPistoleros()) {
                        contentText+="[Pistolero "+p.getPistoleroId()+"] "+p.getScore()+"\n";
                    }
                    al.setContentText(contentText);
                    al.show();
                    MediaPlayer s = Sound.createSound(Sound.APPLAUSE);
                    if (s != null) {
                        s.play();
                        al.setOnCloseRequest(f -> { s.stop();});
                    }
                }
            }); 
            
            pt.play();
            
        }
                   
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
    public double getRefSpeed() {
        return speedProperty.get();
    }
    
    
   
    /**
     * Change direction of the vampire (+/- 90 degrees)
     */
    private void turn() {
       directionProperty.set(directionProperty.get()+(90 * (Math.random() < 0.5 ? 1 : -1)));
    }
    
    /**
     * Turn back (180 degrees)
     */
    private void turnBack() {
        directionProperty.set(directionProperty.get()+180);
    }
    
    /**
     * Get gender of this pistolero
     * @return gender (male or female)
     */
    public Gender getGender() {
        return genderProperty.get();
    }
    
    /**
     * Get id of this vampire
     * @return ident number
     */
    public int getVampireId() {
        return vampire_id;
    }
    
    
}
