/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buyAndSell.ui.singleItemFound;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class SingleItemFoundController implements Initializable {
    
    public static String item_name2;
    public static String item_id2;
    public static String seller_id2;
    public static int price2;
    public static boolean is_avail2;
    public static Blob imagePath;

    @FXML
    private Label itemName;
    @FXML
    private Label itemID;
    @FXML
    private Label itemPrice;
    @FXML
    private Label sellerId;
    @FXML
    private Label isAvail;
    @FXML
    private JFXButton close;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private ImageView image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        itemName.setText(item_name2);
        itemID.setText(item_id2);
        itemPrice.setText(Integer.toString(price2));
        sellerId.setText(seller_id2);
        isAvail.setText((is_avail2 ? "Available" : "Not available"));
        if (imagePath != null) try {
            image.setImage(new Image(imagePath.getBinaryStream()));
        } catch (SQLException ex) {
            Logger.getLogger(SingleItemFoundController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    
    
}
