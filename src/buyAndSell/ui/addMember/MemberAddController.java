 
package buyAndSell.ui.addMember;

import java.net.URL;
import java.util.ResourceBundle;

import buyAndSell.database.DatabaseHandler;
import buyAndSell.ui.InUp.InUpController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

import javax.xml.crypto.Data;


public class MemberAddController implements Initializable {

    DatabaseHandler handler;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField ID;

    @FXML
    private JFXTextField mobile;

    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;

    @FXML
    private void addMember(ActionEvent event) {
        
        String mname = name.getText();
        String mid = ID.getText();
        String mmobile = mobile.getText();
        String memmail = email.getText();
        String mpassword = password.getText();
        
        boolean flag = mname.isEmpty() || mpassword.isEmpty() || mid.isEmpty() || mmobile.isEmpty() || memmail.isEmpty();

        if (flag) {    
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields!");
            alert.showAndWait();
            return;
        }

        String st = "INSERT INTO MEMBER VALUES( " +
                "'" + mid + "'," +
                "'" + mpassword + "'," +
                "'" + mname + "'," +
                "'" + mmobile + "'," +
                "'" + memmail + "'" +
                ");";

        System.out.println(st);
        if (handler.execAction(st)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED");
            alert.showAndWait();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        handler = DatabaseHandler.getInstance();
    }


    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        InUpController.signUpPageLoaded = false;
    }

    
    
}
