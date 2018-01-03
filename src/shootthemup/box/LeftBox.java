package shootthemup.box;

import shootthemup.gamecharacter.Pistolero;
import java.util.ArrayList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import shootthemup.Graphical;

/**
 * Left side of graphical interface
 */
public final class LeftBox {
    
    /**
     * ScrollPane which contains LEFT_BOX
     */
    private static final ScrollPane SCROLLPANE = new ScrollPane();
    
    /**
     * Contains all sliders and game parameters
     */
    private static final VBox LEFT_BOX = new VBox();
    
    /**
     * Interval (seconds) for reloading pistoleros which have no more balls
     */
    private static final LongProperty INTERVAL_RELOAD_PISTOLEROS = new SimpleLongProperty(2000);
    
    /**
     * Number of balls to reload on each time
     */
    private static final IntegerProperty NUMBER_BALLS_TO_RELOAD = new SimpleIntegerProperty(10);
    
    /**
     * All pistoleros' sliders (motion and rotation speed)
     */
    private static final ListProperty<Slider> PISTOLERO_SLIDERS = new SimpleListProperty<Slider>(FXCollections.observableArrayList(new ArrayList<Slider>()));
    
    /**
     * Default vampires speed
     */
    private static final double DEFAULT_VAMPIRES_SPEED = 1;
    
    /**
     * Slider for vampires speed
     */
    private static final Slider SL_VAMPIRES_SPEED = new Slider(0.5,2,DEFAULT_VAMPIRES_SPEED);
    
    /**
     * Default helixes speed
     */
    private static final double DEFAULT_HELIXES_SPEED = 1;
    
    /**
     * Slider for helixes rotation speed
     */
    private static final Slider SL_HELIXES_ROTATION_SPEED = new Slider(-2,2,DEFAULT_HELIXES_SPEED);
    
    /**
     * Default music volume
     */
    private static final double DEFAULT_MUSIC_VOLUME = 0.25;
    
    /**
     * Slider for music volume
     */
    private static final Slider SL_MUSIC_VOLUME = new Slider(0,1,DEFAULT_MUSIC_VOLUME);
    
    /**
     * Slider for sounds volume
     */
    private static final Slider SL_SOUNDS_VOLUME = new Slider(0,1,DEFAULT_MUSIC_VOLUME);
    
    /**
     * Checkbox to select to make music and sounds mute
     */
    private static final CheckBox MUTE = new CheckBox("Mute");
    
    /**
     * Slider for red background color
     */
    private static final Slider SL_COLOR_RED = new Slider(0,255,0);
    
    /**
     * Slider for green background color
     */
    private static final Slider SL_COLOR_GREEN = new Slider(0,255,0);
    
    /**
     * Slider for blue background color
     */
    private static final Slider SL_COLOR_BLUE = new Slider(0,255,0);
    
    /**
     * Default balls reload interval
     */
    private static final double DEFAULT_RELOAD = 20;
    
    /**
     * Slider for balls reload interval
     */
    private static final Slider SL_RELOAD_INTERVAL = new Slider(10,30,DEFAULT_RELOAD);

    /**
     * Number of balls to reload on each time
     */
    private static final double DEFAULT_NB_BALLS = 10;
    
    /**
     * Slider for number of balls to reload
     */
    private static final Slider SL_NB_BALLS_TO_RELOAD = new Slider(5,20,DEFAULT_NB_BALLS);
    
    /**
     * Button for red walls
     */
    private static final RadioButton BLUE_WALLS = new RadioButton("Blue");
    
    /**
     * Button for blue walls
     */
    private static final RadioButton RED_WALLS = new RadioButton("Red");
    
    /**
     * Initializes fields of this class
     */
    public static void init() {
        
        SCROLLPANE.setContent(LEFT_BOX);
        SCROLLPANE.setFitToWidth(true);
        
        
        Label l_volume = new Label("[Volume]");
        
        SL_MUSIC_VOLUME.setShowTickMarks(true);
        SL_MUSIC_VOLUME.setShowTickLabels(true);
        SL_MUSIC_VOLUME.setMajorTickUnit(0.25f);
        SL_MUSIC_VOLUME.setBlockIncrement(0.01f);

        Label l_music_volume = new Label();
        l_music_volume.textProperty().bind(Bindings.createStringBinding(() -> {
            
            return "Music ("+Math.round(SL_MUSIC_VOLUME.getValue()*100)+"%)";
        
        },SL_MUSIC_VOLUME.valueProperty()));
        
        SL_SOUNDS_VOLUME.setShowTickMarks(true);
        SL_SOUNDS_VOLUME.setShowTickLabels(true);
        SL_SOUNDS_VOLUME.setMajorTickUnit(0.25f);
        SL_SOUNDS_VOLUME.setBlockIncrement(0.01f);
       
        Label l_sounds_volume = new Label();
        l_sounds_volume.textProperty().bind(Bindings.createStringBinding(() -> {
        
            return "Sounds ("+Math.round(SL_SOUNDS_VOLUME.getValue()*100)+"%)";
            
        }, SL_SOUNDS_VOLUME.valueProperty()));

        LEFT_BOX.getChildren().addAll(l_volume,l_music_volume,SL_MUSIC_VOLUME,l_sounds_volume,SL_SOUNDS_VOLUME,MUTE);
        
        
        LEFT_BOX.getChildren().addAll(new Label("[Background]"));
        

        SL_COLOR_RED.setShowTickMarks(true);
        SL_COLOR_RED.setShowTickLabels(true);
        SL_COLOR_RED.setMajorTickUnit(50.0f);
        SL_COLOR_RED.setBlockIncrement(1.0f);
        
        Label l_color_red = new Label();
        Circle r_red_1 = new Circle();
        r_red_1.setFill(Color.RED);
        r_red_1.radiusProperty().bind(Bindings.divide(l_color_red.heightProperty(),2));
        l_color_red.setGraphic(r_red_1);
        
        l_color_red.textProperty().bind(Bindings.createStringBinding(() -> {
            
            return " Red ("+Math.round(SL_COLOR_RED.getValue())+")";
        
        }, SL_COLOR_RED.valueProperty()));

        LEFT_BOX.getChildren().addAll(l_color_red,SL_COLOR_RED);



                
        SL_COLOR_GREEN.setShowTickMarks(true);
        SL_COLOR_GREEN.setShowTickLabels(true);
        SL_COLOR_GREEN.setMajorTickUnit(50.0f);
        SL_COLOR_GREEN.setBlockIncrement(1.0f);
        
        Label l_color_green = new Label();
        Circle r_green_1 = new Circle();
        r_green_1.setFill(Color.GREEN);
        r_green_1.radiusProperty().bind(Bindings.divide(l_color_green.heightProperty(),2));
        l_color_green.setGraphic(r_green_1);
        
        l_color_green.textProperty().bind(Bindings.createStringBinding(() -> {
            
            return " Green ("+Math.round(SL_COLOR_GREEN.getValue())+")";
        
        }, SL_COLOR_GREEN.valueProperty()));

        LEFT_BOX.getChildren().addAll(l_color_green,SL_COLOR_GREEN);


                
        
        SL_COLOR_BLUE.setShowTickMarks(true);
        SL_COLOR_BLUE.setShowTickLabels(true);
        SL_COLOR_BLUE.setMajorTickUnit(50.0f);
        SL_COLOR_BLUE.setBlockIncrement(1.0f);
        
        Label l_color_blue = new Label();
        Circle r_blue_1 = new Circle();
        r_blue_1.setFill(Color.BLUE);
        r_blue_1.radiusProperty().bind(Bindings.divide(l_color_blue.heightProperty(),2));
        l_color_blue.setGraphic(r_blue_1);
        
        l_color_blue.textProperty().bind(Bindings.createStringBinding(() -> {
            
            return " Blue ("+Math.round(SL_COLOR_BLUE.getValue())+")";
        
        }, SL_COLOR_BLUE.valueProperty()));

        LEFT_BOX.getChildren().addAll(l_color_blue,SL_COLOR_BLUE);

        
        ToggleGroup colorWalls = new ToggleGroup();
        BLUE_WALLS.setToggleGroup(colorWalls);
        RED_WALLS.setToggleGroup(colorWalls);
        
        Circle r_blue_2 = new Circle();
        r_blue_2.setFill(Color.BLUE);
        r_blue_2.radiusProperty().bind(Bindings.divide(BLUE_WALLS.heightProperty(),2));
        BLUE_WALLS.setGraphic(r_blue_2);
        
        Circle r_red_2 = new Circle();
        r_red_2.setFill(Color.RED);
        r_red_2.radiusProperty().bind(Bindings.divide(RED_WALLS.heightProperty(), 2));
        RED_WALLS.setGraphic(r_red_2);
        
        RED_WALLS.setSelected(true);
        
        LEFT_BOX.getChildren().addAll(new Label("Walls color"),BLUE_WALLS,RED_WALLS);
        
        ArrayList<Pistolero> pistoleros = Graphical.getPistoleros();
        
        
        for (Pistolero p : pistoleros) {
            
            ProgressBar pb = new ProgressBar();
            pb.progressProperty().bind(Bindings.divide(p.remainingBallsProperty(),Pistolero.NB_BALLS_MAX));
            pb.setStyle("-fx-accent: red;");
            pb.styleProperty().bind(Bindings.when(pb.progressProperty().lessThan(0.20)).then("-fx-accent: red;").otherwise(Bindings.when(pb.progressProperty().greaterThanOrEqualTo(0.9)).then("-fx-accent: lime;").otherwise("")));
                    
            Label lab = new Label();
            lab.textProperty().bind(Bindings.createStringBinding( () -> {
            
                return "Remainig balls : "+p.getNbRemainingBalls();
            
            }, p.remainingBallsProperty() ));
            
            LEFT_BOX.getChildren().addAll(new Label("[Pistolero "+p.getPistoleroId()+"]"),lab,pb);
            
            
            Slider sl1 = p.getMotionSpeedSlider();
            Slider sl2 = p.getRotationSpeedSlider();
            
            PISTOLERO_SLIDERS.addAll(sl1,sl2);
            
            Label l1 = new Label();
            l1.textProperty().bind(Bindings.createStringBinding(() -> {
                return "Motion speed (x"+Math.round(sl1.getValue()*100.0)/100.0+")";
            }, sl1.valueProperty()));
            
            Label l2 = new Label();
            l2.textProperty().bind(Bindings.createStringBinding(() -> {
            
                return "Rotation speed (x"+Math.round(sl2.getValue()*100.0)/100.0+")";
                
            }, sl2.valueProperty()));
            
            LEFT_BOX.getChildren().addAll(l1,sl1,l2,sl2);
            
        }
        
        
        SL_VAMPIRES_SPEED.setShowTickMarks(true);
        SL_VAMPIRES_SPEED.setShowTickLabels(true);
        SL_VAMPIRES_SPEED.setMajorTickUnit(0.5f);
        SL_VAMPIRES_SPEED.setBlockIncrement(0.01f);
        
        Label l_vampires_speed = new Label();
        l_vampires_speed.textProperty().bind(Bindings.createStringBinding(() -> {
        
            return "Motion speed (x"+Math.round(SL_VAMPIRES_SPEED.getValue()*100.0)/100.0+")";
            
        },SL_VAMPIRES_SPEED.valueProperty()));
        
        LEFT_BOX.getChildren().addAll(new Label("[Vampires]"),l_vampires_speed,SL_VAMPIRES_SPEED);
        
        SL_HELIXES_ROTATION_SPEED.setShowTickMarks(true);
        SL_HELIXES_ROTATION_SPEED.setShowTickLabels(true);
        SL_HELIXES_ROTATION_SPEED.setMajorTickUnit(1.0f);
        SL_HELIXES_ROTATION_SPEED.setBlockIncrement(0.01f);
        
        Label l_helixes_speed = new Label();
        l_helixes_speed.textProperty().bind(Bindings.createStringBinding(() -> {
        
            return "Rotation speed (x"+Math.round(SL_HELIXES_ROTATION_SPEED.getValue()*100.0)/100.0+")";
                    
        },SL_HELIXES_ROTATION_SPEED.valueProperty()));
        
        LEFT_BOX.getChildren().addAll(new Label("[Helixes]"),l_helixes_speed,SL_HELIXES_ROTATION_SPEED);
        
        LEFT_BOX.getChildren().addAll(new Label("[Reload pistoleros]"));
        

        SL_RELOAD_INTERVAL.setShowTickMarks(true);
        SL_RELOAD_INTERVAL.setShowTickLabels(true);
        SL_RELOAD_INTERVAL.setMajorTickUnit(5.0f);
        SL_RELOAD_INTERVAL.setBlockIncrement(1.0f);
        
        SL_NB_BALLS_TO_RELOAD.setShowTickMarks(true);
        SL_NB_BALLS_TO_RELOAD.setShowTickLabels(true);
        SL_NB_BALLS_TO_RELOAD.setMajorTickUnit(5.0f);
        SL_NB_BALLS_TO_RELOAD.setBlockIncrement(1.0f);
        
        Label l_reload_interval = new Label();
        l_reload_interval.textProperty().bind(Bindings.createStringBinding(() -> {
        
            return "Interval reload ("+Math.round(SL_RELOAD_INTERVAL.getValue())+" seconds)";
            
        }, SL_RELOAD_INTERVAL.valueProperty()));
        
        Label l_nb_balls_to_reload = new Label();
        l_nb_balls_to_reload.textProperty().bind(Bindings.createStringBinding(() -> {
        
            return "Number of balls to reload ("+Math.round(SL_NB_BALLS_TO_RELOAD.getValue())+")";
        
        }, SL_NB_BALLS_TO_RELOAD.valueProperty()));
        
        NUMBER_BALLS_TO_RELOAD.bind(Bindings.createIntegerBinding(() -> {
            return (int) Math.round(SL_NB_BALLS_TO_RELOAD.getValue());        
        }, SL_NB_BALLS_TO_RELOAD.valueProperty()));
        
        INTERVAL_RELOAD_PISTOLEROS.bind(Bindings.createIntegerBinding(() -> {
        
            return 100*(int) Math.round(SL_RELOAD_INTERVAL.getValue());
            
        }, SL_RELOAD_INTERVAL.valueProperty()));
        
        LEFT_BOX.getChildren().addAll(l_nb_balls_to_reload,SL_NB_BALLS_TO_RELOAD,l_reload_interval,SL_RELOAD_INTERVAL);
        
    }
    
    /**
     * Add this LeftBox to a BorderPane
     * @param bp pane to configure
     */
    public static void addLeftBox(BorderPane bp) {
        bp.setLeft(SCROLLPANE);
        
    }
    
    /**
     * Property for balls reloading interval
     * @return balls reload interval property
     */
    public static ReadOnlyLongProperty intervalReloadProperty() {
        return INTERVAL_RELOAD_PISTOLEROS;
    }
    
    /**
     * Balls reload interval
     * @return reload interval in centiseconds
     */
    public static long getIntervalReload() {
        return INTERVAL_RELOAD_PISTOLEROS.get();
    }
    
    /**
     * Property for number of balls to reload
     * @return number balls to reload property
     */
    public static ReadOnlyIntegerProperty numberBallsToReloadProperty() {
        return NUMBER_BALLS_TO_RELOAD;
    }
    
    /**
     * Number of balls to reload
     * @return number of balls to reload
     */
    public static int getNbBallsToReload() {
        return NUMBER_BALLS_TO_RELOAD.get();
    }
    
    /**
     * Enable all elements of this LeftBox
     */
    public static void enableAll() {
        for (Slider sl : PISTOLERO_SLIDERS) {
            sl.setDisable(false);
        }
        SL_VAMPIRES_SPEED.setDisable(false);
        SL_HELIXES_ROTATION_SPEED.setDisable(false);
        SL_MUSIC_VOLUME.setDisable(false);
        SL_SOUNDS_VOLUME.setDisable(false);
        SL_COLOR_RED.setDisable(false);
        SL_COLOR_GREEN.setDisable(false);
        SL_COLOR_BLUE.setDisable(false);
        SL_NB_BALLS_TO_RELOAD.setDisable(false);
        SL_RELOAD_INTERVAL.setDisable(false);
        BLUE_WALLS.setDisable(false);
        RED_WALLS.setDisable(false);
        MUTE.setDisable(false);
    }
    
    /**
     * Disable all elements of this LeftBox
     */
    public static void disableAll() {
        for (Slider sl : PISTOLERO_SLIDERS) {
            sl.setDisable(true);
        }
        SL_VAMPIRES_SPEED.setDisable(true);
        SL_HELIXES_ROTATION_SPEED.setDisable(true);
        SL_MUSIC_VOLUME.setDisable(true);
        SL_SOUNDS_VOLUME.setDisable(true);
        SL_COLOR_RED.setDisable(true);
        SL_COLOR_GREEN.setDisable(true);
        SL_COLOR_BLUE.setDisable(true);
        SL_NB_BALLS_TO_RELOAD.setDisable(true);
        SL_RELOAD_INTERVAL.setDisable(true);
        BLUE_WALLS.setDisable(true);
        RED_WALLS.setDisable(true);
        MUTE.setDisable(true);
    }
    
    /**
     * Property for vampires motion speed multiplier coefficient
     * @return vampires speed multiplier property
     */
    public static ReadOnlyDoubleProperty multiplyVampiresSpeedProperty() {
        return SL_VAMPIRES_SPEED.valueProperty();
    }
    
    /**
     * Property for helixes rotation speed multiplier coefficient
     * @return helixes speed multiplier property
     */
    public static ReadOnlyDoubleProperty multiplyHelixesRotationSpeedProperty() {
        return SL_HELIXES_ROTATION_SPEED.valueProperty();
    }
    
    /**
     * Property for music volume
     * @return music volume property
     */
    public static ReadOnlyDoubleProperty musicVolumeProperty() {
        return SL_MUSIC_VOLUME.valueProperty();
    }
    
    /**
     * Property for sounds volume
     * @return sounds volume property
     */
    public static ReadOnlyDoubleProperty soundsVolumeProperty() {
        return SL_SOUNDS_VOLUME.valueProperty();
    }
    
    /**
     * Property which indicates if mute is checked
     * @return mute property
     */
    public static ReadOnlyBooleanProperty muteCheckedProperty() {
        return MUTE.selectedProperty();
    }
    
    /**
     * Property for red background color property
     * @return red property
     */
    public static ReadOnlyDoubleProperty redProperty() {
        return SL_COLOR_RED.valueProperty();
    }
    
    /**
     * Property for green background color property
     * @return green property
     */
    public static ReadOnlyDoubleProperty greenProperty() {
        return SL_COLOR_GREEN.valueProperty();
    }
    
    /**
     * Property for blue background color property
     * @return blue property
     */
    public static ReadOnlyDoubleProperty blueProperty() {
        return SL_COLOR_BLUE.valueProperty();
    }
    
    /**
     * Property which indicates if red walls button is checked
     * @return red walls property
     */
    public static ReadOnlyBooleanProperty redWallsSelectedProperty() {
        return RED_WALLS.selectedProperty();
    }
    
    /**
     * Property which indicates if blue walls button is checked
     * @return blue walls property
     */
    public static ReadOnlyBooleanProperty blueWallsSelectedProperty() {
        return BLUE_WALLS.selectedProperty();
    }
    
    /**
     * Value of red background color, indicated by the corresponding slider
     * @return red level
     */
    public static int getRed() {
        return (int) Math.round(SL_COLOR_RED.getValue());
    }
    
    /**
     * Value of green background color, indicated by the corresponding slider
     * @return green level
     */
    public static int getGreen() {
        return (int) Math.round(SL_COLOR_GREEN.getValue());
    }
    
    /**
     * Value of blue color, indicated by the corresponding slider
     * @return blue level
     */
    public static int getBlue() {
        return (int) Math.round(SL_COLOR_BLUE.getValue());
    }
    
    /**
     * Reset all elements of this LeftBox
     */
    public static void reset() {
        SL_COLOR_RED.setValue(0);
        SL_COLOR_GREEN.setValue(0);
        SL_COLOR_BLUE.setValue(0);
        SL_RELOAD_INTERVAL.setValue(DEFAULT_RELOAD);
        SL_NB_BALLS_TO_RELOAD.setValue(DEFAULT_NB_BALLS);
        SL_VAMPIRES_SPEED.setValue(DEFAULT_VAMPIRES_SPEED);
        SL_HELIXES_ROTATION_SPEED.setValue(DEFAULT_HELIXES_SPEED);
        RED_WALLS.setSelected(true);
    }
    

    
}
