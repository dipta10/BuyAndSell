
package buyAndSell.ui.InUp;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InUpLoader extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("in_up.fxml"));
        /*@workLeft
            need to make it undecorated here
        */
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    
}
