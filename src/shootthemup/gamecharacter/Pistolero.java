package shootthemup.gamecharacter;

import shootthemup.interfaces.GameCharacter;
import shootthemup.audio.Sound;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.animation.RotateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import shootthemup.Graphical;
import shootthemup.interfaces.Food;
import shootthemup.interfaces.Movable;
import shootthemup.interfaces.Obstacle;

/**
 * Class for chocolate bars (which can be eaten by any character of the arena)
 */
public final class Pistolero extends Circle implements GameCharacter {
    
    /**
     * Prefix used to indicate where graphical elements are located
     */
    private static final String URL_PREFIX = "graphics/pistolero/";
    
    /**
     * Represents green level if the pistolero is turned to the left
     */
    private static final ImagePattern GREEN_LEFT = new ImagePattern(new Image(URL_PREFIX+"green_left.png"));
    
    /**
     * Represents green level if the pistolero is turned to the right
     */
    private static final ImagePattern GREEN_RIGHT = new ImagePattern(new Image(URL_PREFIX+"green_right.png"));
    
    /**
     * Represents yellow level if the pistolero is turned to the left
     */
    private static final ImagePattern YELLOW_LEFT = new ImagePattern(new Image(URL_PREFIX+"yellow_left.png"));
    
    /**
     * Represents yellow level if the pistolero is turned to the right
     */
    private static final ImagePattern YELLOW_RIGHT = new ImagePattern(new Image(URL_PREFIX+"yellow_right.png"));
    
    /**
     * Represents orange level if the pistolero is turned to the left
     */
    private static final ImagePattern ORANGE_LEFT = new ImagePattern(new Image(URL_PREFIX+"orange_left.png"));
    
    /** 
     * Represents orange level if the pistolero is turned to the right
     */
    private static final ImagePattern ORANGE_RIGHT = new ImagePattern(new Image(URL_PREFIX+"orange_right.png"));
    
    /**
     * Represents red level if the pistolero is turned to the left
     */
    private static final ImagePattern RED_LEFT = new ImagePattern(new Image(URL_PREFIX+"red_left.png"));
    
    /**
     * Represents red level if the pistolero is turned to the right
     */
    private static final ImagePattern RED_RIGHT = new ImagePattern(new Image(URL_PREFIX+"red_right.png"));

    /**
     * Choose key for left motions
     */
    private final ChoiceBox<KeyCode> choiceLeft;
    
    /**
     * Choose key for right motions
     */
    private final ChoiceBox<KeyCode> choiceRight;
    
    /**
     * Choose key for top motions
     */
    private final ChoiceBox<KeyCode> choiceTop;
    
    /**
     * Choose key for bottom motions
     */
    private final ChoiceBox<KeyCode> choiceBottom;
    
    /**
     * Choose key for shots
     */
    private final ChoiceBox<KeyCode> choiceShoot;
    
    /**
     * Choose key for rotate
     */
    private final ChoiceBox<KeyCode> choiceRotate;
    
    
    /**
     * CheckBox used to invert mouse buttons
     */
    private final CheckBox invertMouse;
    
    /**
     * Array of all ChoiceBoxes
     */
    private final ArrayList<ChoiceBox<KeyCode>> choiceBoxes;
    
    /**
     * Indicates maximal capacity of balls a pistolero can have
     */
    public static final double NB_BALLS_MAX = 100;
    
    /**
     * Indicates remaining balls at this pistolero's
     */
    private final DoubleProperty remainingBallsProperty;
    

    /**
     * Indicates the danger level of the pistolero, after collision with vampires
     */
    private final IntegerProperty levelProperty;
    
    /**
     * Indicates speed of balls shot by the pistolero
     */
    private final DoubleProperty ballsSpeedProperty;
    
    /**
     * Indicates pistolero motion speed
     */
    private final DoubleProperty speedMotionProperty;
    
    /**
     * Indicates pistolero rotation speed
     */
    private final DoubleProperty speedRotationProperty;
    
    /**
     * Indicates score of this pistolero
     */
    private final LongProperty scoreProperty;
    
    /**
     * Represents motions to the left
     */
    private final AnimationTimer anim_left;
    
    
    /**
     * Represents motions to the right
     */
    private final AnimationTimer anim_right;
    
    /**
     * Represents motions to the top
     */
    private final AnimationTimer anim_top;
    
    /**
     * Represents motions to the bottom
     */
    private final AnimationTimer anim_bottom;
    
    /**
     * Represents rotations
     */
    private final AnimationTimer anim_rotate;
    
    
    /**
     * Represent the only instance of this class
     */
    private static Pistolero singleton;
    
    /**
     * Starting coordinates of pistolero
     */
    private double start_x;
    
    /**
     * Starting coordinates of pistolero
     */
    private double start_y;
    
    /**
     * Number of pistoleros created
     */
    private static int nb_pistoleros = 0;
    
    /**
     * ID of this pistolero
     */
    private final int pistolero_id;
    
    /**
     * Indicates if the pistolero needs to reload its ball stock
     */
    private boolean needs_reload;
    

    /**
     * Number of male vampires killed
     */
    private final LongProperty nbMaleVampiresKilledProperty;
    
    /**
     * Number of female vampires killed
     */
    private final LongProperty nbFemaleVampiresKilledProperty;
    
    
    /**
     * Indicates name of this pistolero
     */
    private final TextField nameTextField;
    
    /**
     * Enables to set motion speed
     */
    private final Slider motionSpeedSlider;
    
    /**
     * Enables to set rotation speed
     */
    private final Slider rotationSpeedSlider;
    
    /**
     * Creates an instance of pistolero
     * @param x X axis
     * @param y Y axis
     */
    private Pistolero(double x, double y) {
        super(x+Graphical.getCircleElementsSize(),y+Graphical.getCircleElementsSize(),Graphical.getCircleElementsSize());
        
        int RADIUS = Graphical.getCircleElementsSize();
        
        choiceBoxes = new ArrayList<>();
        choiceBoxes.add((choiceLeft = new ChoiceBox<>()));
        choiceBoxes.add((choiceRight = new ChoiceBox<>()));
        choiceBoxes.add((choiceTop = new ChoiceBox<>()));
        choiceBoxes.add((choiceBottom = new ChoiceBox<>()));
        choiceBoxes.add((choiceShoot = new ChoiceBox<>()));
        choiceBoxes.add((choiceRotate = new ChoiceBox<>()));
        
        invertMouse = new CheckBox("Invert mouse");
        
        for (ChoiceBox cb : choiceBoxes) {
            cb.setItems(FXCollections.observableArrayList(KeyCode.values()));
            cb.setFocusTraversable(true);
            cb.setOnKeyReleased(e -> {
                cb.setValue(e.getCode());
            });
        }
        
        
        motionSpeedSlider = new Slider(0.5,2,1);
        motionSpeedSlider.setShowTickMarks(true);
        motionSpeedSlider.setShowTickLabels(true);
        motionSpeedSlider.setMajorTickUnit(0.5f);
        motionSpeedSlider.setBlockIncrement(0.01f);
        
        rotationSpeedSlider = new Slider(-2,2,1);
        rotationSpeedSlider.setShowTickMarks(true);
        rotationSpeedSlider.setShowTickLabels(true);
        rotationSpeedSlider.setMajorTickUnit(1.0f);
        rotationSpeedSlider.setBlockIncrement(0.01f);

        
        pistolero_id = ++nb_pistoleros;
        
        start_x = x+RADIUS;
        start_y = y+RADIUS;
        
        levelProperty = new SimpleIntegerProperty(0);
        ballsSpeedProperty = new SimpleDoubleProperty();
        ballsSpeedProperty.bind(Bindings.createObjectBinding(() -> {
        
            int l = getLevel();
            if (l <=0) return 10;
            else if (l == 1) return 6.67;
            return 3.33;
            
        }, levelProperty));
        
        remainingBallsProperty = new SimpleDoubleProperty(NB_BALLS_MAX);
        
        scoreProperty = new SimpleLongProperty(0);
        
        nbMaleVampiresKilledProperty = new SimpleLongProperty(0);
        nbFemaleVampiresKilledProperty = new SimpleLongProperty(0);
        
        fillProperty().bind(Bindings.createObjectBinding(() -> {
            
            int level = levelProperty.get();
            double angle = getRotate()%360;
            boolean cond = angle > 90 && angle <= 270;
            if (level <=0) {
                return cond ? GREEN_LEFT : GREEN_RIGHT;
            }
            else if (level == 1) {
                return cond ? YELLOW_LEFT : YELLOW_RIGHT;
            }
            else if (level == 2) {
                return cond ? ORANGE_LEFT : ORANGE_RIGHT;
            }
            return cond ? RED_LEFT : RED_RIGHT;
            
        }, levelProperty, rotateProperty()));
        
        
        anim_left = new AnimationTimer() {
          
            @Override
            public void handle(long l) {
                for (Obstacle o : Graphical.getObstacles()) {
                    if (o.collisionRight(Pistolero.this)) {
                        setCenterX(o.getMaxX()+getRadius());
                        return;
                    }
                }
                
                for (Movable m : Graphical.getMovables()) {
                    if (m.collisionRight(Pistolero.this)) {
                        setCenterX(m.getMaxX()+getRadius());
                        m.pushLeft(speedMotionProperty.get());
                        return;
                    }
                }
                
                for (Food f : Graphical.getFeed()) {
                    if (f.collisionRight(Pistolero.this)) {
                        eat(f);
                        //return;
                    }
                }
                
                setCenterX(getCenterX()-speedMotionProperty.get());
            }
        };
        
        anim_right = new AnimationTimer() {
            
            @Override
            public void handle(long l) {
                for (Obstacle o : Graphical.getObstacles()) {
                    if (o.collisionLeft(Pistolero.this)) {
                        setCenterX(o.getMinX()-getRadius());
                        return;
                    }
                }

                for (Movable m : Graphical.getMovables()) {
                    if (m.collisionLeft(Pistolero.this)) {
                        setCenterX(m.getMinX()-getRadius());
                        m.pushRight(speedMotionProperty.get());
                        return;
                    }
                }
                
                for (Food f : Graphical.getFeed()) {
                    if (f.collisionLeft(Pistolero.this)) {
                        eat(f);
                        //return;
                    }
                }
                
                
                setCenterX(getCenterX()+speedMotionProperty.get());
            }
        };
        
        anim_top = new AnimationTimer() {
          
            @Override
            public void handle(long l) {
                for (Obstacle o : Graphical.getObstacles()) {
                    if (o.collisionBottom(Pistolero.this)) {
                        setCenterY(o.getMaxY()+getRadius());
                        return;
                    }
                }
                
                for (Movable m : Graphical.getMovables()) {
                    if (m.collisionBottom(Pistolero.this)) {
                        setCenterY(m.getMaxY()+getRadius());
                        m.pushTop(speedMotionProperty.get());
                        return;
                    }
                }
                
                for (Food f : Graphical.getFeed()) {
                    if (f.collisionBottom(Pistolero.this)) {
                        eat(f);
                        //return;
                    }
                }
                
                setCenterY(getCenterY()-speedMotionProperty.get());
            }
        };
        
        anim_bottom = new AnimationTimer() {
          
            @Override
            public void handle(long l) {
                for (Obstacle o : Graphical.getObstacles()) {
                    if (o.collisionTop(Pistolero.this)) {
                        setCenterY(o.getMinY()-getRadius());
                        return;
                    }
                }
                
                for (Movable m : Graphical.getMovables()) {
                    if (m.collisionTop(Pistolero.this)) {
                        setCenterY(m.getMinY()-getRadius());
                        m.pushBottom(speedMotionProperty.get());
                        return;
                    }
                }
                
                for (Food f : Graphical.getFeed()) {
                    if (f.collisionTop(Pistolero.this)) {
                        eat(f);
                        //return;
                    }
                }
                
                setCenterY(getCenterY()+speedMotionProperty.get());
            }
        };
        
        anim_rotate = new AnimationTimer() {
            
            @Override
            public void handle(long l) {
                setRotate((getRotate()+speedRotationProperty.get())%360);
                while (getRotate() < 0) setRotate(getRotate()+360);
            }
            
        };
        
        setFocusTraversable(true);
        
        setOnKeyPressed(e -> {
            
            if (e.getCode() == choiceLeft.getValue()) anim_left.start();
            else if (e.getCode() == choiceRight.getValue()) anim_right.start();
            else if (e.getCode() == choiceTop.getValue()) anim_top.start();
            else if (e.getCode() == choiceBottom.getValue()) anim_bottom.start();
            else if (e.getCode() == choiceRotate.getValue()) anim_rotate.start();
            else if (e.getCode() == choiceShoot.getValue()) shoot();
            
            e.consume();
        
        });
        
        setOnKeyReleased(e -> {
        
            if (e.getCode() == choiceLeft.getValue()) anim_left.stop();
            else if (e.getCode() == choiceRight.getValue()) anim_right.stop();
            else if (e.getCode() == choiceTop.getValue()) anim_top.stop();
            else if (e.getCode() == choiceBottom.getValue()) anim_bottom.stop();
            else if (e.getCode() == choiceRotate.getValue()) anim_rotate.stop();
            
            e.consume();
        
        });
        
        setOnMousePressed(e -> {
           
            switch(e.getButton()) {
                case PRIMARY:
                    if (!invertMouse.isSelected()) shoot();
                    else anim_rotate.start();
                    break;
                case SECONDARY:
                    if (!invertMouse.isSelected()) anim_rotate.start();
                    else shoot();
                    break;
                default:
                    break;
            }
            
            e.consume();
            
        });
        
        setOnMouseReleased(e ->{
        
            switch(e.getButton()) {
                case SECONDARY:
                    if (!invertMouse.isSelected()) anim_rotate.stop();
                    break;
                case PRIMARY:
                    if (invertMouse.isSelected()) anim_rotate.stop();
                    break;
                default:
                    break;
            }
        
        
        });
        
        speedRotationProperty = new SimpleDoubleProperty();
        speedRotationProperty.bind(Bindings.createDoubleBinding(() -> {
        
            int l = getLevel();
            double tmp;
            if (l <= 0) tmp = 5.0;
            else if (l == 1) tmp = 2.0;
            else tmp = 1.0;
            return tmp*rotationSpeedSlider.getValue();
        
        }, levelProperty, rotationSpeedSlider.valueProperty()));
        
        speedMotionProperty = new SimpleDoubleProperty();
        speedMotionProperty.bind(Bindings.createDoubleBinding(() -> {
        
            int l = getLevel();
            final double default_speed = RADIUS * motionSpeedSlider.getValue();
            if (l <= 0) return default_speed;
            else if (l == 1) return default_speed/2;
            return default_speed/4;
            
        }, levelProperty, motionSpeedSlider.valueProperty()));
        
        nameTextField = new TextField();
        
        initializeSlidersAndChoices();
        reset();
    }
        
    
    
    /**
     * Creates an instance of Pistolero with default parameters
     */
    private Pistolero() {
        this(Graphical.getCircleElementsSize(),Graphical.getCircleElementsSize());
    }
    
    /**
     * Creates an instance of pistolero if it doesn't already exists and returns it
     * @param x coordinates of pistolero
     * @param y coordinates of pistolero
     * @return instance of Pistolero
     */
    public static Pistolero getInstance(double x, double y) {
        if (singleton == null) singleton = new Pistolero(x, y);
        return singleton;
    }
    
    
    /**
     * Rotation of pistolero
     */
    private synchronized void rotate() {
        RotateTransition ro = new RotateTransition(Duration.millis(500),this);
        ro.setToAngle(getRotate()+45);
        ro.setOnFinished(e -> {
            setRotate(getRotate()%360);
        });
        
    
        ro.play();
    }
    
    
    /**
     * Shoot a ball
     */
    private void shoot() {
        
        if (remainingBallsProperty.get() <= 0) return;
        
        double angle = getRotate();
        double start_x = getCenterX() + getRadius()*Math.cos(Math.toRadians(angle));
        double start_y = getCenterY() + getRadius()*Math.sin(Math.toRadians(angle));
        
        MediaPlayer sound = Sound.createSound(Sound.SHOT);
        if (sound != null) sound.play();
        Ball b = new Ball(start_x,start_y,angle,ballsSpeedProperty.get(), this);
        Graphical.add(b);
        b.startMotion();
        
        decrRemainingBalls();
        if (remainingBallsProperty.get() <= 0) needs_reload = true;
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
    public double getRefSize() {
        return getRadius();
    }
    
    @Override
    public double getRefSpeed() {
        return speedMotionProperty.get();
    }
    
    @Override
    public double getMinX() {
        return getCenterX()-getRadius();
    }
    
    
    @Override
    public double getMinY() {
        return getCenterY()-getRadius();
    }

    @Override
    public Bounds getRefBounds() {
        return getBoundsInParent();
    }
    
    /**
     * Replaces the pistolero at its starting position
     */
    public synchronized void relocateStartPosition() {
        setCenterX(start_x);
        setCenterY(start_y);
        setRotate(0);
    }
    
    /**
     * Returns level of the pistolero
     * @return current level of this pistolero
     */
    public int getLevel() {
        return levelProperty.get();
    }
    
    /**
     * Increases level of pistolero
     */
    public synchronized void incrLevel() {
        levelProperty.set(Math.min(3, getLevel()+1));
    }
    
    /**
     * Decreases level of pistolero
     */
    public synchronized void decrLevel() {
        levelProperty.set(Math.max(0, getLevel()-1));
    }
    
    @Override
    public void eat(Food f) {
        f.setEatenBy(this);
        MediaPlayer sound = Sound.createSound(Sound.PISTOLERO_EAT);
        if (sound != null) sound.play();
    }
     
    @Override
    public void regenerate() {
        decrLevel();
    }
    
    /**
     * Ident number
     * @return id of this pistolero
     */
    public int getPistoleroId() {
        return pistolero_id;
    }
    
    /**
     * Remaining balls number property
     * @return remaining balls property (read only)
     */
    public ReadOnlyDoubleProperty remainingBallsProperty() {
        return remainingBallsProperty;
    }
    
    /**
     * Get number of remaining balls
     * @return number of remaining balls
     */
    public long getNbRemainingBalls() {
        return Math.round(remainingBallsProperty.get());
    }
    
    /**
     * Decreases number of remaining balls
     */
    private void decrRemainingBalls() {
        remainingBallsProperty.set(Math.max(0, remainingBallsProperty.get()-1));
    }
    
    /**
     * Increases number of remaining balls
     */
    private void incrRemainingBalls() {
        remainingBallsProperty.set(Math.min(NB_BALLS_MAX, remainingBallsProperty.get()+1));
    }
    
    /**
     * Reload balls of this pistolero when its stock is over
     * @param n number pf balls to reload
     */
    public void reload(int n) {
        if (!needs_reload) return;
        
        for (int i =0; i<n; i++) {
            incrRemainingBalls();
            if (remainingBallsProperty.get() >= NB_BALLS_MAX) {
                needs_reload = false;
                break;
            }
        }
        MediaPlayer sound = Sound.createSound(Sound.RELOAD_BALLS);
        if (sound != null) sound.play();
    }
    
    /**
     * Score of pistolero
     * @return current score
     */
    public long getScore() {
        return scoreProperty.get();
    }
    
    /**
     * Property for score
     * @return score property
     */
    public ReadOnlyLongProperty scoreProperty() {
        return scoreProperty;
    }
    
    /**
     * Increases score of pistolero
     * @param n number of points to add
     */
    public void incrScore(long n) {
        scoreProperty.set(scoreProperty.get()+n);
    }
    
    /**
     * Reset this pistolero
     */
    public void reset() {

        relocateStartPosition();
        scoreProperty.set(0);
        nbMaleVampiresKilledProperty.set(0);
        nbFemaleVampiresKilledProperty.set(0);
        levelProperty.set(0);
        remainingBallsProperty.set(NB_BALLS_MAX);
        needs_reload = false;
        //nameTextField.clear();
        nameTextField.setDisable(false);
        setDisable(false);
        anim_left.stop();
        anim_right.stop();
        anim_top.stop();
        anim_bottom.stop();
        anim_rotate.stop();
        
        /*
        initializeSlidersAndChoices()
        */
        for (ChoiceBox<KeyCode> cb : choiceBoxes) cb.setDisable(false); 
        invertMouse.setDisable(false);
        


    }
    
    /**
     * Initializes sliders and choice boxes corresponding to this pistolero
     */
    private void initializeSlidersAndChoices() {
                
        motionSpeedSlider.setValue(1);
        rotationSpeedSlider.setValue(1);
        
        
        choiceLeft.setValue(KeyCode.LEFT);        
        choiceRight.setValue(KeyCode.RIGHT);       
        choiceTop.setValue(KeyCode.UP);
        choiceBottom.setValue(KeyCode.DOWN);
        choiceShoot.setValue(KeyCode.ALT);        
        choiceRotate.setValue(KeyCode.CONTROL);
        
        invertMouse.setSelected(false);
    }
        
    /**
     * Increases number of male vampires killed by this pistolero
     */
    public void incrMaleVampiresKilled() {
        nbMaleVampiresKilledProperty.set(nbMaleVampiresKilledProperty.get()+1);
    }
    
    /**
     * Increases number of female vampires killed by this pistolero
     */
    public void incrFemaleVampiresKilled() {
        nbFemaleVampiresKilledProperty.set(nbFemaleVampiresKilledProperty.get()+1);
    }
    
    /**
     * Number of male vampires killed by this pistolero
     * @return current number
     */
    public long getNbMaleVampiresKilled() {
        return nbMaleVampiresKilledProperty.get();
    }
    
    /**
     * Number of female vampires killed by this pistolero
     * @return current number
     */
    public long getNbFemaleVampiresKilled() {
        return nbFemaleVampiresKilledProperty.get();
    }
    
    /**
     * Property for number of male vampires killed
     * @return number of male vampires killed property
     */
    public ReadOnlyLongProperty nbMaleVampiresKilledProperty() {
        return nbMaleVampiresKilledProperty;
    }
    
    /**
     * Property for number of female vampires killed
     * @return number of female vampires killed
     */
    public ReadOnlyLongProperty nbFemaleVampiresKilledProperty() {
        return nbFemaleVampiresKilledProperty;
    }
    
    /**
     * TextField which contains name of player which plays with this pistolero
     * @return name text field
     */
    public TextField getNameTextField() {
        return nameTextField;
    }
    
    /**
     * Slider which indicates rotation speed
     * @return rotation slider
     */
    public Slider getRotationSpeedSlider() {
        return rotationSpeedSlider;
    }
    
    /**
     * Slider which indicates motion speed
     * @return motion slider
     */
    public Slider getMotionSpeedSlider() {
        return motionSpeedSlider;
    }
    
    /**
     * ChoiceBox which indicates key to press for left motions
     * @return left motions key
     */
    public ChoiceBox<KeyCode> getChoiceLeft() {
        return choiceLeft;
    }
    
    /**
     * ChoiceBox which indicates key to press for right motions
     * @return right motions key
     */
    public ChoiceBox<KeyCode> getChoiceRight() {
        return choiceRight;
    }
    
    /**
     * ChoiceBox which indicates key to press for top motions
     * @return top motions key
     */
    public ChoiceBox<KeyCode> getChoiceTop() {
        return choiceTop;
    }
    
    /**
     * ChoiceBox which indicates key to press for bottom motions
     * @return bottom motions key
     */
    public ChoiceBox<KeyCode> getChoiceBottom() {
        return choiceBottom;
    }
    
    /**
     * ChoiceBox which indicates key to press for shots
     * @return shot key
     */
    public ChoiceBox<KeyCode> getChoiceShoot() {
        return choiceShoot;
    }
    
    /**
     * ChoiceBox which indicates key to press for rotations
     * @return rotation key
     */
    public ChoiceBox<KeyCode> getChoiceRotate() {
        return choiceRotate;
    }
    
    /**
     * CheckBox to select to permute left and right mouse button commands
     * @return mouse buttons checkbox
     */
    public CheckBox getInvertMouse() {
        return invertMouse;
    }
    
    /**
     * Get all ChoiceBoxes
     * @return new array list which contains all ChoiceBoxes of this pistolero
     */
    public ArrayList<ChoiceBox<KeyCode>> getChoiceBoxes() {
        ArrayList<ChoiceBox<KeyCode>> res = new ArrayList<>();
        res.addAll(choiceBoxes);
        return res;
    }
    
    /**
     * Disable all ChoiceBoxes of this pistolero
     */
    public void disableChoiceAndCheckBoxes() {
        for (ChoiceBox cb : choiceBoxes) cb.setDisable(true);
        invertMouse.setDisable(true);
    }
    
    /**
     * Enables all ChoiceBoxes of this pistolero
     */
    public void enableChoiceAndCheckBoxes() {
        for (ChoiceBox cb : choiceBoxes) cb.setDisable(false);
        invertMouse.setDisable(false);
    }
    
    /**
     * Get all keys used by this pistolero
     * @return new array list with associated keys
     */
    public ArrayList<KeyCode> getKeys() {
        ArrayList<KeyCode> res = new ArrayList<>();
        for (ChoiceBox<KeyCode> cb : choiceBoxes) res.add(cb.getValue());
        return res;
    }
    
    /**
     * Start x axis of this pistolero
     * @return starting position
     */
    public double getStartX() {
        return start_x;
    }
    
    /**
     * Start y axis of this pistolero
     * @return starting position
     */
    public double getStartY() {
        return start_y;
    }
    
    /**
     * Modify starting position (usefull when game level changes)
     * @param x new x axis
     */
    public void setStartX(double x) {
        start_x = x;
    }
    
    /**
     * Modify starting position (useful when game level changes)
     * @param y new y axis
     */
    public void setStartY(double y) {
        start_y = y;
    }
        
    
}
