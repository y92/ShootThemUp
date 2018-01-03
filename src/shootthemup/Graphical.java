package shootthemup;

import shootthemup.audio.Music;
import shootthemup.interfaces.AutoAnimable;
import shootthemup.gamecharacter.Ball;
import shootthemup.gamecharacter.Gender;
import shootthemup.gamecharacter.Pistolero;
import shootthemup.gamecharacter.Vampire;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import shootthemup.box.BottomBox;
import shootthemup.box.LeftBox;
import shootthemup.box.RightBox;
import shootthemup.box.TopBox;
import shootthemup.food.Chocolate;
import shootthemup.interfaces.Food;
import shootthemup.food.Pizza;
import shootthemup.interfaces.Movable;
import shootthemup.obstacle.CircleStone;
import shootthemup.obstacle.Helix;
import shootthemup.interfaces.Obstacle;
import shootthemup.obstacle.SquaredStone;
import shootthemup.obstacle.Wall;
import shootthemup.interfaces.TransitionModifiable;

/**
 * This class contains all graphical elements
 */
public final class Graphical {
    
    /**
     * Default window width
     */
    public static final int DEFAULT_WIDTH = 1200;
    
    /**
     * Default window height
     */
    public static final int DEFAULT_HEIGHT = 800;
    
    /**
     * Elements reference size
     */
    private static final IntegerProperty ELEMENTS_SIZE = new SimpleIntegerProperty();
    
    
    /**
     * Game arena which contains all items (characters, obstacles...)
     */
    private static final Pane ARENA = new Pane();
    
    /**
     * Scene which contains a BorderPane (which contains all boxes and the arena)
     */
    private static Scene SCENE;
    
    /**
     * ScrollPane which contains the arena (can be useful if the computer screen size is too small)
     */
    private static final ScrollPane SCROLLPANE = new ScrollPane(ARENA);
    
    /**
     * Background of the arena
     */
    private static final Rectangle BACKGROUND = new Rectangle();
    
    /**
     * List of all helixes of the arena
     */
    private static final ListProperty<Helix> HELIXES = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * List of all vampires of the arena
     */
    private static final ListProperty<Vampire> VAMPIRES = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * List of all squared stones
     */
    private static final ListProperty<Movable> MOVABLES = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * List of all color modifiable items
     */
    private static final ListProperty<TransitionModifiable> TRANSITION_MODIFIABLES = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * List of all feed
     */
    private static final ListProperty<Food> FEED = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * Number of remaining male vampires
     */
    private static IntegerProperty REMAINING_MALE;
    
    /**
     * Number of remaining female vampires
     */
    private static IntegerProperty REMAINING_FEMALE;
    
    
    /**
     * List of all walls of the arena
     */
    private static final ListProperty<Wall> WALLS = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    
    /**
     * List of all pistoleros of the arena
     */
    private static final ListProperty<Pistolero> PISTOLEROS = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    /**
     * List of all balls shot by pistoleros
     */
    private static final ListProperty<Ball> BALLS = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    
    
    /**
     * Current game level
     */
    private static final IntegerProperty GAME_LEVEL = new SimpleIntegerProperty();
    
    /**
     * Represents initial configuration
     */
    private static CaseType[][] caseType; 
    
    /**
     * Represents differents types of items which can be present in the arena
     */
    private static enum CaseType {
        EMPTY,
        WALL,
        HELIX,
        VAMPIRE,
        PISTOLERO,
        STONE,
        PIZZA,
        CHOCOLATE
    }
    
    /**
     * Set game level
     * @param level new level
     */
    public static void setLevel(int level) {
        GAME_LEVEL.set(level);
    }
    
    /**
     * Size of elements (radius*2 for circle elements, length for squared elements)
     * @return size
     */
    public static int getElementsSize() {
        return ELEMENTS_SIZE.get();
    }
    
    /**
     * Radius of circle elements size
     * @return radius
     */
    public static int getCircleElementsSize() {
        return ELEMENTS_SIZE.get()/2;
    }
    
    
    /**
     * Creates a randomized initialization of the board
     * @return the board
     */
    private static CaseType[][] getRandomInit() {
        
        CaseType[][] res = new CaseType[DEFAULT_WIDTH/ELEMENTS_SIZE.get()][DEFAULT_HEIGHT/ELEMENTS_SIZE.get()];
        
        for (int i = 0; i < res.length; i++) { Arrays.fill(res[i], CaseType.EMPTY); }
        
        /* Fill left and right with walls */
        for (int i = 0; i < res[i].length; i++) {
            res[0][i] = CaseType.WALL;
            res[res.length-1][i] = CaseType.WALL;
        }
        
        /* Fill top and bottom with walls */
        for (int i = 1; i < res.length-1; i++) {
            res[i][0] = CaseType.WALL;
            res[i][res[0].length-1] = CaseType.WALL;
        }
        
        int i =0;
        int j =0;
        
        if (Math.random() < 0.5) addHorizontalWalls(res);
        else addVerticalWalls(res);
        
        /* Determinates intial coordinates of pistolero */
        int middle_w = res.length/2;
        int middle_h = res[0].length/2;
        res[middle_w][middle_h] = CaseType.PISTOLERO;
        
        return res;
        
    }
    


    
    /**
     * Add vertical walls to the board
     * @param tab array representing the board
     */
    private static void addVerticalWalls(CaseType[][] tab) {
        
        
        int width = tab.length;
        int height = tab[0].length;
        
        for (int i = 2;  i < height-2;) {
            
            int len = 2 + (int) (Math.random()*(width/4-1));
            for (int j =0; j< len; j++) tab[j+1][i] = CaseType.WALL;
            
            i += (Math.random() < 0.5) ? 2 : 3;
        }
        
        for (int i = height-3; i> 1; i--) {
            if (tab[1][i-1] == CaseType.WALL) {
                
                int len = 2 + (int) (Math.random()*(width/4-1));
                for (int j = 0; j< len; j++) tab[width-2-j][i] = CaseType.WALL;
            }
           
        }
        
        for (int i = 1; i < height; i++) {
            if (tab[1][i-1] == CaseType.WALL) tab[1][i] = CaseType.VAMPIRE;
        }
        
        for (int i = 1; i< height-1; i++) {
            if (tab[width-2][i+1] == CaseType.WALL) tab[width-2][i] = CaseType.VAMPIRE;
        }
        
        int middle_h = height/2;
        int middle_w = width/2;
        
        for (int i = 0; i < 3; i++) {
            tab[middle_w][i+1] = CaseType.WALL;
            tab[middle_w][height-2-i] = CaseType.WALL;
        }
        
        for (int i = 5; i < middle_h-2; i+=2) {
            tab[middle_w][i] = CaseType.HELIX;
        }
        
        
        for (int i = middle_h-1; i <= middle_h+1; i++) {
            tab[middle_w-1][i] = CaseType.WALL;
            tab[middle_w+1][i] = CaseType.WALL;
        }
        
        for (int i = height-6; i > middle_h+2; i-=2) {
            tab[middle_w][i] = CaseType.HELIX;
        }
        
        tab[middle_w][middle_h + (Math.random() < 0.5 ? 1 : -1)] = CaseType.WALL;


        tab[middle_w-2][middle_h-2] = CaseType.STONE;
        tab[middle_w+2][middle_h+2] = CaseType.STONE;
        tab[middle_w-2][middle_h+2] = CaseType.STONE;
        tab[middle_w+2][middle_h-2] = CaseType.STONE;

    }
    
    /**
     * Add horizontal walls to the board
     * @param tab array representing the board
     */
    private static void addHorizontalWalls(CaseType[][] tab) {
        
        int width = tab.length;
        int height = tab[0].length;
        
        for (int i = 2;  i < width-2;) {
            
            int len = 2 + (int) (Math.random()*(height/4-1));
            for (int j =0; j< len; j++) tab[i][j+1] = CaseType.WALL;
            
            i += (Math.random() < 0.5) ? 2 : 3;
        }
        
        for (int i = width-3; i> 1; i--) {
            if (tab[i-1][1] == CaseType.WALL) {
                
                int len = 2 + (int) (Math.random()*(height/4-1));
                for (int j = 0; j< len; j++) tab[i][height-2-j] = CaseType.WALL;
            }
           
        }
        
        for (int i = 1; i < width; i++) {
            if (tab[i-1][1] == CaseType.WALL) tab[i][1] = CaseType.VAMPIRE;
        }
        
        for (int i = 1; i< width-1; i++) {
            if (tab[i+1][height-2] == CaseType.WALL) tab[i][height-2] = CaseType.VAMPIRE;
        }
        
        int middle_h = height/2;
        int middle_w = width/2;
        
        for (int i = 0; i < 3; i++) {
            tab[i+1][middle_h] = CaseType.WALL;
            tab[width-2-i][middle_h] = CaseType.WALL;
        }
        
        for (int i = 5; i < middle_w-2; i+=2) {
            tab[i][middle_h] = CaseType.HELIX;
        }
        
        
        for (int i = middle_w-1; i <= middle_w+1; i++) {
            tab[i][middle_h-1] = CaseType.WALL;
            tab[i][middle_h+1] = CaseType.WALL;
        }
        
        for (int i = width-6; i > middle_w+2; i-=2) {
            tab[i][middle_h] = CaseType.HELIX;
        }
        
        tab[middle_w + (Math.random() < 0.5 ? 1 : -1)][middle_h] = CaseType.WALL;
        
        tab[middle_w-2][middle_h-2] = CaseType.STONE;
        tab[middle_w+2][middle_h+2] = CaseType.STONE;
        tab[middle_w-2][middle_h+2] = CaseType.STONE;
        tab[middle_w+2][middle_h-2] = CaseType.STONE;

    }
    
    /**
     * Reset current game
     */
    public static void reset() {
                
        for (AutoAnimable a : getAutoAnimables()) {
            a.stopMotion();
        }
        
        ARENA.getChildren().clear();
        PISTOLEROS.clear();
        VAMPIRES.clear();
        HELIXES.clear();
        MOVABLES.clear();
        FEED.clear();
        
        for (TransitionModifiable tm : TRANSITION_MODIFIABLES) tm.stopTransition();
        TRANSITION_MODIFIABLES.clear();
        WALLS.clear();
        BALLS.clear();
        BottomBox.resetTime();
        TopBox.reset();
        //LeftBox.reset();
        
        initialize();
    }
    
    /**
     * Initializes an arena
     * @param pane arena to fill
     */
    private static void initialize() {
    
        caseType = getRandomInit();
        ARENA.getChildren().add(BACKGROUND);
        
        for (int i = 0; i< caseType.length; i++) {
            
            for (int j = 0; j<caseType[i].length; j++) {
            
                double x = i*ELEMENTS_SIZE.get();
                double y = j*ELEMENTS_SIZE.get();
                
                switch(caseType[i][j]) {
                
                    case WALL:
                        add(new Wall(x,y));
                        break;
                    case HELIX:
                        Helix h = new Helix(x,y);
                        add(h);
                        //h.startMotion();
                        break;
                    case VAMPIRE:
                        Vampire v = new Vampire(x,y, Math.random() < 0.5 ? Gender.MALE : Gender.FEMALE);
                        add(v);
                        //v.startMotion();
                        break;
                    case PISTOLERO:
                        Pistolero p = Pistolero.getInstance(x,y);
                        p.setRadius(ELEMENTS_SIZE.get()/2);
                        p.setStartX(x+p.getRadius());
                        p.setStartY(y+p.getRadius());
                        p.reset();
                        add(p);
                        p.setDisable(true);
                        break;
                    case STONE:
                        if (Math.random() < 0.5) {
                            CircleStone s = new CircleStone(x,y);
                            add(s);
                        }
                        else {
                            SquaredStone s = new SquaredStone(x,y);
                            add(s);
                        }
                        break;
                    
                    default:
                        break;
                }
            
            
            }
        }
    
    }
    
    /**
     * List of every visible balls
     * @return list of balls
     */
    public static ArrayList<Ball> getBalls() {
        ArrayList<Ball> res = new ArrayList<Ball>();
        res.addAll(BALLS);
        return res;
    }
    
    /**
     * List of every auto-animables objects (vampires, balls and helixes)
     * @return list of vampires, balls and helixes
     */
    public static ArrayList<AutoAnimable> getAutoAnimables() {
        
        ArrayList<AutoAnimable> res = new ArrayList<AutoAnimable>();
        
        res.addAll(VAMPIRES);
        res.addAll(BALLS);
        res.addAll(HELIXES);
        
        return res;
        
    }
    
    /**
     * List of all visible and non movable obstacles on the arena, except Vampires and feed
     * @return list of obstacles, except vampires and movables
     */
    public static ArrayList<Obstacle> getObstacles() {
        ArrayList<Obstacle> res = new ArrayList<Obstacle>();
        res.addAll(HELIXES);
        res.addAll(WALLS);
        return res;
    }
    
    /**
     * List of all visible obstacles on the arena, including movables, except vampires and feed
     * @return list of obstacles including movables, except vampires
     */
    public static ArrayList<Obstacle> getObstaclesWithMovables() {
        ArrayList<Obstacle> res = new ArrayList<>();
        res.addAll(getObstacles());
        res.addAll(MOVABLES);
        return res;
                
    }
    
    /**
     * List of all feed on the arena
     * @return list of all feed
     */
    public static ArrayList<Food> getFeed() {
        ArrayList<Food> res = new ArrayList<>();
        res.addAll(FEED);
        return res;
    }
    
    /**
     * List of all color modifiable items on the arena
     * @return list of color modifiable items
     */
    public static ArrayList<TransitionModifiable> getTransitionModifiables() {
        ArrayList<TransitionModifiable> res = new ArrayList<TransitionModifiable>();
        res.addAll(TRANSITION_MODIFIABLES);
        return res;
    }

    /**
     * List of all movable obstacles on the arena
     * @return list of movables
     */
    public static ArrayList<Movable> getMovables() {
        ArrayList<Movable> res = new ArrayList<Movable>();
        res.addAll(MOVABLES);
        return res;
    }
    
    /**
     * List of every visible pistleros on the arena
     * @return list of pistoleros 
     */
    public static ArrayList<Pistolero> getPistoleros() {
        
        ArrayList<Pistolero> res = new ArrayList<Pistolero>();
        res.addAll(PISTOLEROS);
        
        return res;
        
    }
    
    /**
     * List of every visible vampires on the arena
     * @return list of vampires
     */
    public static ArrayList<Vampire> getVampires() {
        ArrayList<Vampire> res = new ArrayList<Vampire>();
        res.addAll(VAMPIRES);
        return res;
    }
    
    /**
     * Add a ball to the arena
     * @param b ball to add
     */
    public static void add(Ball b) {
        BALLS.add(b);
        ARENA.getChildren().add(b);
    }
    
    /**
     * Add an helix to the arena
     * @param h helix to add
     */
    public static void add(Helix h) {
        HELIXES.add(h);
        ARENA.getChildren().add(h);
    }
    
    /**
     * Add squared stone to the arena
     * @param s stone to add
     */
    public static void add(SquaredStone s) {
        MOVABLES.add(s);
        TRANSITION_MODIFIABLES.add(s);
        ARENA.getChildren().add(s);
    }
    
    /**
     * Add a circle stone to the arena
     * @param s stone to add
     */
    public static void add(CircleStone s) {
        MOVABLES.add(s);
        TRANSITION_MODIFIABLES.add(s);
        ARENA.getChildren().add(s);
    }
    
    /**
     * Add a pistolero to the arena
     * @param p pistolero to add
     */
    public static void add(Pistolero p) {
        PISTOLEROS.add(p);
        ARENA.getChildren().add(p);
    }
    
    /**
     * Add a vampire to the arena
     * @param v vampire to add
     */
    public static void add(Vampire v) {
        VAMPIRES.add(v);
        ARENA.getChildren().add(v);
    }
    
    /**
     * Add a wall to the arena
     * @param w wall to add
     */
    public static void add(Wall w) {
        WALLS.add(w);
        ARENA.getChildren().add(w);
    }
    
    /**
     * Add a pizza to the arena
     * @param p pizza to add
     */
    private static void add(Pizza p) {
        FEED.add(p);
        ARENA.getChildren().add(p);
    }
    
    /**
     * Add a chocolate bar to the arena
     * @param c chocolate bar to add
     */
    private static void add(Chocolate c) {
        FEED.add(c);
        ARENA.getChildren().add(c);
    }
    
    /**
     * Add food to the arena (randomly chocolate or pizza)
     */
    public static void addFood() {
        
        if (FEED.size() >= 6 + 2*GAME_LEVEL.get()) return;
        
        int i, j;
        
        do {
            i = (int) (caseType.length*Math.random());
            j = (int) (caseType[0].length*Math.random());
        }
        while (caseType[i][j] != CaseType.EMPTY);
        
        if (Math.random() < 0.5) add(new Pizza(i*ELEMENTS_SIZE.get(),j*ELEMENTS_SIZE.get()));
        else add(new Chocolate(i*ELEMENTS_SIZE.get(), j*ELEMENTS_SIZE.get(), Chocolate.randomColor()));
    }
    
    /**
     * Remove a ball from the arena
     * @param b ball to remove 
     */
    public static void remove(Ball b) {
        ARENA.getChildren().remove(b);
        BALLS.remove(b);
    }
    
    /**
     * Remove an helix from the arena
     * @param h helix to remove
     */
    public static void remove(Helix h) {
        ARENA.getChildren().remove(h);
        HELIXES.remove(h);
    }
    
    /**
     * Remove a pistolero from the arena
     * @param p pistolero to remove
     */
    public static void remove(Pistolero p) {
        ARENA.getChildren().remove(p);
        PISTOLEROS.remove(p);
    }
    
    /**
     * Remove a vampire from the arena
     * @param v vampire to remove
     */
    public static void remove(Vampire v) {
        ARENA.getChildren().remove(v);
        VAMPIRES.remove(v);
    }
    
    /**
     * Remove a wall from the arena
     * @param w wall to remove
     */
    public static void remove(Wall w) {
        ARENA.getChildren().remove(w);
        WALLS.remove(w);
    }
    
    /**
     * Remove a pizza from the arena
     * @param p pizza to remove
     */
    public static void remove(Pizza p) {
        ARENA.getChildren().remove(p);
        FEED.remove(p);
    }
    
    /**
     * Remove a chocolate bar from the arena
     * @param c chocolate bar to remove
     */
    public static void remove(Chocolate c) {
        ARENA.getChildren().remove(c);
        FEED.remove(c);
    }
    
    /**
     * Scene representing the game (firstly initialized it hasn't been done)
     * @return scene
     */
    public static Scene getScene() {
        if (SCENE == null) {
            
            
            BorderPane root = new BorderPane();
            root.setCenter(SCROLLPANE);
            BACKGROUND.fillProperty().bind(Bindings.createObjectBinding(() -> {
            
                return Color.rgb(LeftBox.getRed(),LeftBox.getGreen(),LeftBox.getBlue());
            
            }, LeftBox.redProperty(), LeftBox.greenProperty(), LeftBox.blueProperty()));
            
            BACKGROUND.widthProperty().bind(ARENA.widthProperty());
            BACKGROUND.heightProperty().bind(ARENA.heightProperty());
            ARENA.setPrefWidth(DEFAULT_WIDTH);
            ARENA.setPrefHeight(DEFAULT_HEIGHT);
            
            ELEMENTS_SIZE.bind(Bindings.createIntegerBinding(() -> {
            
                switch (GAME_LEVEL.get()) {
                    case 1:
                        return 40;
                    case 2:
                        return 35;
                    case 3:
                        return 30;
                    case 4:
                        return 25;
                    case 5:
                        return 20;
                    default:
                        return 40;
                }
                
            }, GAME_LEVEL));
            
            
            setLevel(1);
            initialize();

            TopBox.init();
            BottomBox.init();
            LeftBox.init();
            RightBox.init();

            TopBox.addTopBox(root);
            BottomBox.addBottomBox(root);
            LeftBox.addLeftBox(root);
            RightBox.addRightBox(root);
            Music.playMusic(Music.MUSIC_DEFAULT);

            
            SCENE = new Scene(root);
            
        }
        return SCENE;
    }
    
    /**
     * Enable all autoanimable items and pistoleros
     */
    public static void enableAll() {
        for (Pistolero p : getPistoleros()) p.setDisable(false);
        for (AutoAnimable a : getAutoAnimables()) a.startMotion();
        for (TransitionModifiable c : getTransitionModifiables()) c.playTransition();
    }
    
    /**
     * Disable all autoanimable items and pistoleros
     */
    public static void disableAll() {        
        for (Pistolero p : getPistoleros()) p.setDisable(true);
        for (AutoAnimable a : getAutoAnimables()) a.stopMotion();
        for (TransitionModifiable c : getTransitionModifiables()) c.pauseTransition();
    }
    
    /**
     * Number of vampires in the arena
     * @return number of vampires
     */
    public static ReadOnlyIntegerProperty getNbVampires() {
        
        return VAMPIRES.sizeProperty();
    }
    
    /**
     * Initializes property representing number of male vampires
     */
    private static void initNbMale() {
        if (REMAINING_MALE == null) {
            REMAINING_MALE = new SimpleIntegerProperty();
            REMAINING_MALE.bind(Bindings.createIntegerBinding(() -> {
                
                int nb_male = 0;
                
                for (Vampire v : getVampires()) {
                    if (v.getGender() == Gender.MALE) nb_male++;
                }
                
                return nb_male;
            
            }, VAMPIRES));
        }
    }
    
    /**
     * Initializes property representing number of female vampires
     */
    private static void initNbFemale() {
        
        if (REMAINING_FEMALE == null) {
            REMAINING_FEMALE = new SimpleIntegerProperty();
            REMAINING_FEMALE.bind(Bindings.createIntegerBinding(() -> {
            
                int nb_female = 0;
                
                for (Vampire v : getVampires()) {
                    if (v.getGender() == Gender.FEMALE) nb_female++;
                }
                
                return nb_female;
            
            }, VAMPIRES));
        }
        
    }
    
    /**
     * Get number of male vampires
     * @return number of male vampires
     */
    public static int getNbMaleVampires() {
        if (REMAINING_MALE == null) initNbMale();
        return REMAINING_MALE.get();
    }
    
    /**
     * Get number of female vampires
     * @return number of female vampires
     */
    public static int getNbFemaleVampires() {
        if (REMAINING_FEMALE == null) initNbFemale();
        return REMAINING_FEMALE.get();
    }
    
    /**
     * Get number of male vampires property
     * @return property for number of male vampires
     */
    public static ReadOnlyIntegerProperty nbMaleVampiresProperty() {
        if (REMAINING_MALE == null) initNbMale();
        return REMAINING_MALE;
    }
    
    /**
     * Get number of female vampires property
     * @return property for number of female vampires
     */
    public static ReadOnlyIntegerProperty nbFemaleVampiresProperty() {
        if (REMAINING_FEMALE == null) initNbFemale();
        return REMAINING_FEMALE;
    }
    

    
}
