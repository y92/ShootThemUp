package shootthemup.box;

import shootthemup.gamecharacter.Pistolero;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import shootthemup.Graphical;

/**
 * Bottom of graphical interface
 */
public final class BottomBox {
    
    /**
     * VBox which contains every elements of bottom box
     */
    private static final VBox BOTTOM_BOX = new VBox();
    
    /**
     * Timer which indicates number of centiseconds passed since the beginning of the game
     */
    private static final LongProperty DURATION_PROPERTY = new SimpleLongProperty(0);
    
    /**
     * Updates timed passed
     */
    private static final Timeline TIME = new Timeline(new KeyFrame(Duration.millis(10), f -> { 
        DURATION_PROPERTY.set(1+DURATION_PROPERTY.get());
    
        if (DURATION_PROPERTY.get() % LeftBox.getIntervalReload() == 0) {
            for (Pistolero p : Graphical.getPistoleros()) p.reload(LeftBox.getNbBallsToReload());
        }
        
        if (DURATION_PROPERTY.get() % 3000 == 0) {
            Graphical.addFood();
        }
    
    }));
    
    /**
     * Initializes fields of this class
     */
    public static void init() {
        
        ArrayList<Pistolero> pistoleros = Graphical.getPistoleros();
        
        Label l_remaining_vampires = new Label();
        l_remaining_vampires.textProperty().bind(Bindings.createStringBinding(() -> { 
            return "Remaining vampires : "+Graphical.getVampires().size()+ " ("+ Graphical.getNbMaleVampires()+" male, "+Graphical.getNbFemaleVampires()+" female)";
        }, Graphical.getNbVampires(), Graphical.nbMaleVampiresProperty(), Graphical.nbFemaleVampiresProperty()));
        
        
        
        
        Label l_time = new Label();
        l_time.textProperty().bind(Bindings.createStringBinding( () -> {
            
            long duration = DURATION_PROPERTY.get();
            
            long centiseconds = duration%100;
            String centiseconds_str = (centiseconds < 10 ? "0" : "")+centiseconds;
            
            long seconds = (duration/100)%60;
            String seconds_str = (seconds < 10 ? "0" : "")+seconds;
            
            long minutes = (duration/6000)%60;
            String minutes_str = (minutes < 10 ? "0" : "")+minutes;
            
            long hours = (duration/360000);
            String hours_str = (hours < 10 ? "0" : "")+hours;
            
            return "Time passed : "+hours_str+":"+minutes_str+":"+seconds_str+"."+centiseconds_str;
        },DURATION_PROPERTY));
        
        TIME.setCycleCount(Timeline.INDEFINITE);
        
        for (Pistolero p : pistoleros) {
            
            Label l_score = new Label();
            l_score.textProperty().bind(Bindings.createStringBinding( () -> {
            
                return "[Score Pistolero "+p.getPistoleroId()+"] "+p.getScore();
                
            }, p.scoreProperty()));
            
            Label l_killed_vampires = new Label();
            
        
            l_killed_vampires.textProperty().bind(Bindings.createStringBinding(() -> {
            long nb_male_killed = p.getNbMaleVampiresKilled();
            long nb_female_killed = p.getNbFemaleVampiresKilled();
            
            return "[Vampires killed by Pistolero "+p.getPistoleroId()+"] "+(nb_male_killed + nb_female_killed)+" ("+nb_male_killed+" male, "+nb_female_killed+" female)";
        
            }, p.nbMaleVampiresKilledProperty(), p.nbFemaleVampiresKilledProperty()));
            
            BOTTOM_BOX.getChildren().addAll(l_score,l_killed_vampires);
            
            
        }
        
        BOTTOM_BOX.getChildren().addAll(l_remaining_vampires, l_time);
    }
    
    /**
     * Add elements of this BottomBox to a BorderPane
     * @param bp pane to configure
     */
    public static void addBottomBox(BorderPane bp) {
        bp.setBottom(BOTTOM_BOX);
    }
    
    
    /**
     * Start timer
     */
    public static void startTime() {
        TIME.play();
    }
    
    /**
     * Stop timer
     */
    public static void stopTime() {
        TIME.pause();
    }
    
    /**
     * Reset timer
     */
    public static void resetTime() {
        DURATION_PROPERTY.set(0);
    }
    
}
