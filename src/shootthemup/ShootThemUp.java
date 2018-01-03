package shootthemup;


import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main class of the program
 */
public class ShootThemUp extends Application {
    
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("Shoot Them Up");
        primaryStage.setScene(Graphical.getScene());
        primaryStage.show();
    }

    /**
     * Main function
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
