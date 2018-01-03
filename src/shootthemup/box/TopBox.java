package shootthemup.box;

import shootthemup.audio.Music;
import shootthemup.gamecharacter.Pistolero;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import shootthemup.Graphical;

/**
 * Top side of graphical interface
 */
public final class TopBox {
    
    /**
     * VBox which contains every element of topbox
     */
    private static final VBox TOP_BOX = new VBox();
    
    /**
     * Menu of graphical interface
     */
    private static final MenuBar MENU = new MenuBar();
    
    /**
     * Button used to start, pause or resume a game
     */
    private static final Button BUTTON = new Button("Start");
    
    /**
     * Initializes attributes of this class
     */
    public static void init() {
        
        Menu menu1 = new Menu("Game");
        MenuItem newGame = new MenuItem("New Game");
        MenuItem save = new MenuItem("Save scores");
        
        newGame.setAccelerator(new KeyCodeCombination(KeyCode.F2));
        newGame.setOnAction(f -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

            alert.setTitle("Start new game");
            alert.setHeaderText("Are you sure you want to start a new game ?");
            alert.setContentText("Your current progress will be lost");
                        
            alert.showAndWait().ifPresent(b -> {
                if (b == ButtonType.OK) {
                    Graphical.reset();
                }
            });
        });
        save.setAccelerator(new KeyCodeCombination(KeyCode.F3,KeyCombination.ALT_DOWN));
        save.setOnAction(f -> {
        
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save scores");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            
            File fileToSave = fileChooser.showSaveDialog(MENU.getScene().getWindow());
            
            if (fileToSave != null) {
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave));
                    
                    Date date = new Date();
                    
                    
                    bw.write("Shoot Them Up Scores ["+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)+"]");
                    bw.newLine();
                    for (Pistolero p : Graphical.getPistoleros()) {
                        long nbMaleKilled = p.getNbMaleVampiresKilled();
                        long nbFemaleKilled = p.getNbFemaleVampiresKilled();
                        long totalKilled = nbMaleKilled + nbFemaleKilled;
                        
                        bw.write("[Score of Pistolero "+p.getPistoleroId()+"] ["+p.getNameTextField().getText()+"] "+p.getScore()+" ("+totalKilled+ " vampires killed : "+nbMaleKilled+" male, "+nbFemaleKilled+" female)");
                        bw.newLine();

                        
                    }
                    
                    
                    bw.flush();
                    bw.close();
                    
                }
                catch(IOException exc) {
                    System.err.println(exc);
                    exc.printStackTrace();
                }
            }
            
        });
        
        
        
        menu1.getItems().addAll(newGame,save);
        
        Menu menu2 = new Menu("Music");
        ToggleGroup toggleMusic = new ToggleGroup();
        RadioMenuItem music1 = new RadioMenuItem("Music 1");
        music1.setToggleGroup(toggleMusic);
        music1.setOnAction(e -> {
            Music.playMusic(Music.MUSIC_1);
        });
        music1.setSelected(true);
        
        RadioMenuItem music2 = new RadioMenuItem("Music 2");
        music2.setToggleGroup(toggleMusic);   
        music2.setOnAction(e -> {
            Music.playMusic(Music.MUSIC_2);
        });
        
        RadioMenuItem music3 = new RadioMenuItem("Music 3");
        music3.setToggleGroup(toggleMusic);
        music3.setOnAction(e -> {
            Music.playMusic(Music.MUSIC_3);
        });
        
        RadioMenuItem music4 = new RadioMenuItem("Music 4");
        music4.setToggleGroup(toggleMusic);
        music4.setOnAction(e -> {
            Music.playMusic(Music.MUSIC_4);
        });
        
        RadioMenuItem no_music = new RadioMenuItem("No Music");
        no_music.setToggleGroup(toggleMusic);
           
        no_music.setOnAction(e -> {
            Music.stopMusic();
        });
        
        menu2.getItems().addAll(music1,music2,music3,music4,no_music);
        
        Menu menu3 = new Menu("Level");
        ToggleGroup toggleLevel = new ToggleGroup();

        RadioMenuItem level1 = new RadioMenuItem("Leve1 1");
        level1.setToggleGroup(toggleLevel);
        level1.setOnAction(f -> { Graphical.setLevel(1) ; alertLevelChanged(level1); });
        
        RadioMenuItem level2 = new RadioMenuItem("Level 2");
        level2.setToggleGroup(toggleLevel);
        level2.setOnAction(f -> { Graphical.setLevel(2) ; alertLevelChanged(level2); });
        
        RadioMenuItem level3 = new RadioMenuItem("Level 3");
        level3.setToggleGroup(toggleLevel);
        level3.setOnAction(f -> { Graphical.setLevel(3) ; alertLevelChanged(level3); });
        
        RadioMenuItem level4 = new RadioMenuItem("Level 4");
        level4.setToggleGroup(toggleLevel);
        level4.setOnAction(f -> { Graphical.setLevel(4) ; alertLevelChanged(level4); });
        
        RadioMenuItem level5 = new RadioMenuItem("Level 5");
        level5.setToggleGroup(toggleLevel);
        level5.setOnAction(f -> { Graphical.setLevel(5) ; alertLevelChanged(level5); });
        
        level1.setSelected(true);
        
        menu3.getItems().addAll(level1,level2,level3,level4,level5);
        
        
        
        MENU.getMenus().addAll(menu1,menu2, menu3);
        
        for (Menu m : MENU.getMenus()) {
            m.disableProperty().bind(MENU.disabledProperty());
            
            for (MenuItem mi : m.getItems()) mi.disableProperty().bind(m.disableProperty());
        }

        
        BUTTON.setOnAction(e -> {
        
            if (BUTTON.getText().toLowerCase().trim().equals("pause")) {
                BUTTON.setText("Resume");
                MENU.setDisable(false);
                Graphical.disableAll();
                RightBox.enableAll();
                LeftBox.enableAll();
                BottomBox.stopTime();
            }
            
            
            else {
                
                if (BUTTON.getText().toLowerCase().trim().equals("start")) {
                    for (Pistolero p : Graphical.getPistoleros()) {
                        p.getNameTextField().setDisable(true);
                    }
                }
                
                BUTTON.setText("Pause");
                MENU.setDisable(true);
                Graphical.enableAll();
                RightBox.disableAll();
                LeftBox.disableAll();
                BottomBox.startTime();
            }
            
        });
        
        TOP_BOX.getChildren().addAll(MENU,BUTTON);
        
        disableButton();
    }
    
    /**
     * Add elements of this TopBox to a BorderPane
     * @param bp pane to configure
     */
    public static void addTopBox(BorderPane bp) {
        bp.setTop(TOP_BOX);
    }
    
    /**
     * Enable start button
     */
    public static void enableButton() {
        BUTTON.setDisable(false);
    }
    
    /**
     * Disable start button
     */
    public static void disableButton() {
        BUTTON.setDisable(true);
    }
    
    /**
     * Reset elements of top box
     */
    public static void reset() {
        BUTTON.setText("Start");
    }
    
    /**
     * Warn when level is changed
     * @param it menu item which corresponds to the new level
     */
    public static void alertLevelChanged(RadioMenuItem it) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Level changed");
        alert.setHeaderText("You have selected : "+it.getText());
        alert.setContentText("Start a new game (without closing the window) so that your settings come into effect");
        alert.show();
    }
    
}
