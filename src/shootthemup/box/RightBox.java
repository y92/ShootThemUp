package shootthemup.box;

import shootthemup.gamecharacter.Pistolero;
import java.util.ArrayList;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import shootthemup.Graphical;

/**
 * Right side of graphical interface
 */
public final class RightBox {
    
    /**
     * ScrollPane which contains RIGHT_BOX
     */
    private static final ScrollPane SCROLLPANE = new ScrollPane();
    
    /**
     * Contains all TextFields for names and ChoiceBoxes for pistoleros parameters
     */
    private static final VBox RIGHT_BOX = new VBox();
    
    /**
     * Contains all KeyCode
     */
    private static final ListProperty<KeyCode> KEYS = new SimpleListProperty<KeyCode>(FXCollections.observableArrayList(new ArrayList<KeyCode>()));
    
    /**
     * Initializes fields of this class
     */
    public static void init() {
        
        SCROLLPANE.setContent(RIGHT_BOX);
        SCROLLPANE.setFitToWidth(true);
        
        ArrayList<Pistolero> pistoleros = Graphical.getPistoleros();
        /*
        KEYS.addAll(KeyCode.A, KeyCode.B, KeyCode.C, KeyCode.D, KeyCode.E, KeyCode.F, KeyCode.G, KeyCode.H, KeyCode.I, KeyCode.J);
        KEYS.addAll(KeyCode.K, KeyCode.L, KeyCode.M, KeyCode.N, KeyCode.O, KeyCode.P, KeyCode.Q, KeyCode.R, KeyCode.S, KeyCode.T);
        KEYS.addAll(KeyCode.U, KeyCode.V, KeyCode.W, KeyCode.X, KeyCode.Y, KeyCode.Z);
        
        KEYS.addAll(KeyCode.NUMPAD0, KeyCode.NUMPAD1, KeyCode.NUMPAD2, KeyCode.NUMPAD3, KeyCode.NUMPAD4, KeyCode.NUMPAD5, KeyCode.NUMPAD6, KeyCode.NUMPAD7);
        KEYS.addAll(KeyCode.NUMPAD8, KeyCode.NUMPAD9);
        */
        KEYS.addAll(KeyCode.values());
        
        
        for (Pistolero p : pistoleros) {
            RIGHT_BOX.getChildren().add(new Label("[Pistolero "+p.getPistoleroId()+"] Your name"));
            TextField tf = p.getNameTextField();
            
            tf.textProperty().addListener( (obs, old, _new) -> {
                if (_new.trim().length() <= 0 ) TopBox.disableButton();
                else {
                    boolean no_empty = true;
                    
                    for (Pistolero pp : pistoleros) {
                        if (pp == p) continue;
                        
                        if (pp.getNameTextField().getText().trim().length() <= 0) {
                            no_empty = false;
                            break;
                        }
                        
                    }
                    
                    if (no_empty) TopBox.enableButton();
                }
            });
            
            RIGHT_BOX.getChildren().add(tf);
        }
        
        for (Pistolero p : pistoleros) {
            RIGHT_BOX.getChildren().add(new Label("[Pistolero "+p.getPistoleroId()+"] Set keys"));
            RIGHT_BOX.getChildren().addAll(new Label("Left motions"),p.getChoiceLeft());
            RIGHT_BOX.getChildren().addAll(new Label("Right motions"),p.getChoiceRight());
            RIGHT_BOX.getChildren().addAll(new Label("Top motions"),p.getChoiceTop());
            RIGHT_BOX.getChildren().addAll(new Label("Bottom motions"),p.getChoiceBottom());
            RIGHT_BOX.getChildren().addAll(new Label("Shots"),p.getChoiceShoot());
            RIGHT_BOX.getChildren().addAll(new Label("Rotate"),p.getChoiceRotate());
            RIGHT_BOX.getChildren().addAll(p.getInvertMouse());
        }
        
    }
    
    /**
     * Add this rightbox to a BorderPane
     * @param bp pane to configure
     */
    public static void addRightBox(BorderPane bp) {
        bp.setRight(SCROLLPANE);
    }
    
    /**
     * Disable all elements of this RightBox
     */
    public static void disableAll() {
        for (Pistolero p : Graphical.getPistoleros()) {
            p.disableChoiceAndCheckBoxes();
        }
        
    }
    
    /**
     * Enable all elements of this RightBox
     */
    public static void enableAll() {
        for (Pistolero p : Graphical.getPistoleros()) {
            p.enableChoiceAndCheckBoxes();
        }
    }
    
}
