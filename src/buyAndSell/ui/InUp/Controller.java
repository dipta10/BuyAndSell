
package buyAndSell.ui.InUp;

import buyAndSell.database.DatabaseHandler;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import buyAndSell.myDetails.MyDetails;

public class Controller implements Initializable {

    DatabaseHandler handler = DatabaseHandler.getInstance();
    public static boolean signUpPageLoaded = false;
    
    @FXML
    private VBox rootPane;
    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton close;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void signIn(ActionEvent event) {
        MyDetails.myUserName = userName.getText();
        System.out.println("FINALLYYYYYYYYYYYYYYYYY");
        boolean flag = password.getText().isEmpty() || userName.getText().isEmpty();
        if (flag) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the options");
            alert.showAndWait();
            return;
        }
        
        System.out.println("Entered user name: " + userName.getText());
        System.out.println("mydetails: " + MyDetails.myUserName);
        
        String query = "SELECT * FROM MEMBER";
        ResultSet result = handler.execQuery(query);
        boolean found = false;
        boolean match = false;
        
        
        try {
            while (result.next()) {
                String id = result.getString("id");
                String pass = result.getString("password");
                if (id.equals(userName.getText())) {
                    found = true;
                    if (pass.equals(password.getText())) {
                        match = true;
                    }
                }
            }
            if (!found) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Username '" + userName.getText() + "' not found");
                alert.showAndWait();
            } else if (!match) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("The password is not correct!!");
                alert.showAndWait();
            } else {
                loadWindow("/buyAndSell/ui/Main/main.fxml", "Sign Up", StageStyle.UNDECORATED);
                cancel();
            }
        } catch (SQLException ex) {
            Logger.getLogger(InUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    void cancel() {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void signUp(ActionEvent event) {
        if (signUpPageLoaded) return;
        loadWindow("/buyAndSell/ui/addMember/member_add.fxml", "Sign Up", StageStyle.UNDECORATED);
        signUpPageLoaded = true;
    }
    
    void loadWindow(String location, String title) {
        try {
            Parent parent = (Parent) FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void loadWindow(String location, String title, StageStyle style) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(style);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(InUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*@work
        don't konw for some reasons this following loadWindow() function is not working,
        gotta make it working for smooth finish
    */
    
//    void loadWindow(String location, String title, StageStyle style, boolean windowOpen) {
//        if (windowOpen) return;
//        try {
//            System.out.println("HERE I AM NEW: value: " + windowOpen);
//            windowOpen = true;
//            Parent parent = FXMLLoader.load(getClass().getResource(location));
//            Stage stage = new Stage(style);
//            stage.setTitle(title);
//            stage.setScene(new Scene(parent));
//            stage.show();
//        } catch (IOException ex) {
//            Logger.getLogger(InUpController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
