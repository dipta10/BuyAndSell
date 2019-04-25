/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package buyAndSell.ui.found;

import buyAndSell.database.DatabaseHandler;
import buyAndSell.ui.Main.MainController;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author dipta10
 */
public class FoundController implements Initializable {
    
    ObservableList <Item> list = FXCollections.observableArrayList();
    public static String search;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Item> tableView;
    @FXML
    private TableColumn <Item, String> itemNameCol;
    @FXML
    private TableColumn <Item, String> itemIdCol;
    @FXML
    private TableColumn <Item, String> sellerIdCol;
    @FXML
    private TableColumn <Item, String> priceCol;
    @FXML
    private TableColumn <Item, String> availabilityCol;
    @FXML
    private JFXButton close;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initCol();
        loadData();
    }    

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }
    
    private void loadData() {
        DatabaseHandler handler;
        handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM ITEM WHERE item_name LIKE '%"+ search + "%'; ";
        ResultSet rs = handler.execQuery(qu);
        try {
            String itemId2, itemName2, sellerId2; int price2; boolean availability2;
            while (rs.next()) {
                itemId2 = rs.getString("item_id");
                itemName2 = rs.getString("item_name");
                sellerId2 = rs.getString("seller_id");
                price2 = rs.getInt("price");
                availability2 = rs.getBoolean("is_avail");
                System.out.println("id: " + itemId2);
                list.add(new Item(itemId2, itemName2, sellerId2, price2, availability2));
//                list.add(new Item(name, id, name, avail));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
        tableView.getItems().setAll(list);

    }
    
    private void initCol() {
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        sellerIdCol.setCellValueFactory(new PropertyValueFactory<>("sellerId"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }
    
    public static final class Item {
        private final SimpleStringProperty itemId;
        private final SimpleStringProperty itemName;
        private final SimpleStringProperty sellerId;
        private final SimpleIntegerProperty price;
        private final SimpleBooleanProperty availability;

        public Item(String itemId, String itemName, String sellerId, int price, boolean availability) {
            this.itemId = new SimpleStringProperty(itemId);
            this.itemName = new SimpleStringProperty(itemName);
            this.sellerId = new SimpleStringProperty(sellerId);
            this.price = new SimpleIntegerProperty(price);
            this.availability = new SimpleBooleanProperty(availability);
        }

        public String getItemId() {
            return itemId.get();
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getSellerId() {
            return sellerId.get();
        }

        public int getPrice() {
            return price.get();
        }

        public boolean getAvailability() {
            return availability.get();
        }

    }
    
}
