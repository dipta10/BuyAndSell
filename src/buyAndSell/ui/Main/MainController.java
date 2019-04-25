package buyAndSell.ui.Main;

import buyAndSell.database.DatabaseHandler;
import buyAndSell.myDetails.MyDetails;
import buyAndSell.ui.InUp.InUpController;
import buyAndSell.ui.found.FoundController;
import buyAndSell.ui.singleItemFound.SingleItemFoundController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class MainController implements Initializable {
    
    public static boolean cartPageLoaded = false;
    public static boolean settingsPageLoaded = false;
    public static boolean requestsPageLoaded = false;
    public static boolean contactPageLoaded = false;
    ObservableList <Item> list = FXCollections.observableArrayList();
    private FileInputStream fis;
    DatabaseHandler handler = DatabaseHandler.getInstance();

    @FXML
    private Button close;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button cart;
    @FXML
    private Button requests;
    @FXML
    private Button settings;
    @FXML
    private Button contact;
    @FXML
    private JFXTextField itemNameSellTab;
    @FXML
    private JFXTextField itemIdSellTab;
    @FXML
    private JFXTextField priceSellTab;
    @FXML
    private JFXButton selectImage;
    @FXML
    private JFXButton sell;
    @FXML
    private JFXTextField imagePath;
    @FXML
    private JFXTextField enterItemName;
    @FXML
    private JFXButton searchByName;
    @FXML
    private JFXTextField enterItemID;
    @FXML
    private JFXButton searchById;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTable();
        setMyInfoTab();
    }
    
    private void setMyInfoTab() {
        DatabaseHandler handler;
        handler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM MEMBER WHERE id='" + MyDetails.myUserName + "'";
        System.out.println(query);
        ResultSet rs = handler.execQuery(query);
        try {
            rs.next();
            String name2 = rs.getString("name");
            System.out.println("My name: " + name2);
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadTable() {
        initCol();
        loadData();
    }
    
    private void loadData() {
        DatabaseHandler handler;
        handler = DatabaseHandler.getInstance();
        String qu = "SELECT * FROM ITEM";
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

    @FXML
    private void cancel(ActionEvent event) {
        
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
        /*@workLeft
            1. here first I need to close all the connections
            2. I need to close all the windows those are still open other than the main window
        */
    }
    
    void loadWindow(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
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

    @FXML
    private void cart(ActionEvent event) {
        if (cartPageLoaded) return;
        loadWindow("/buyAndSell/ui/cart/cart.fxml", "Cart", StageStyle.UNDECORATED);
        cartPageLoaded = true;
    }

    @FXML
    private void requests(ActionEvent event) {
        if (requestsPageLoaded) return;
        loadWindow("/buyAndSell/ui/request/request.fxml", "Sign Up", StageStyle.UNDECORATED);
        requestsPageLoaded = true;
    }

    @FXML
    private void settings(ActionEvent event) {
        if (settingsPageLoaded) return;
        loadWindow("/buyAndSell/ui/settings/settings.fxml", "Sign Up", StageStyle.UNDECORATED);
        settingsPageLoaded = true;
        
    }

    @FXML
    private void contact(ActionEvent event) {
        if (contactPageLoaded) return;
        loadWindow("/buyAndSell/ui/contact/contact.fxml", "Sign Up", StageStyle.UNDECORATED);
        contactPageLoaded = true;
    }

    @FXML
    private void sell(ActionEvent event) {
        System.out.println("nabil sell");
        String itemName2 = itemNameSellTab.getText();
        String itemId2 = itemIdSellTab.getText();
        int price2 = 0;
        if (!priceSellTab.getText().isEmpty() && priceSellTab.getText() != null) {
            if (isNumber(priceSellTab.getText())) {
                price2 = Integer.parseInt(priceSellTab.getText());
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("The price should be a number");
                alert.showAndWait();
                return;
            }
        }
        boolean isValid = isValid(itemName2) && isValid(itemId2) && isValid(priceSellTab.getText());
        
        if (!isValid) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all the options");
            alert.showAndWait();
            return;
        }
        
        String query = "INSERT INTO ITEM VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement statement = DatabaseHandler.con.prepareStatement(query);
            
            statement.setString(1, itemId2);
            statement.setString(2, itemName2);
            statement.setString(3, MyDetails.myUserName);
            statement.setInt(4, price2);
            statement.setBoolean(5, true);
            if (fis != null) {
                statement.setBinaryStream(6, fis);
            } else {
                statement.setBinaryStream(6, null);
            }
            
            statement.execute();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("SUCCESS");
            alert.showAndWait();
            
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("FAILED");
            alert.showAndWait();
            
        }
        
    }
    
    private boolean isNumber (String input) {
        for (int i = 0; i < input.length(); ++i) {
            if (!Character.isDigit(input.charAt(i))) {
                System.out.println("here: " + input.charAt(i));
                return false;
            }
        }
        return true;
    }
    
    private boolean isValid(String input) {
        if (input.isEmpty()) return false;
        return (input.trim().length() > 0);
    }

    @FXML
    private void selectImage(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File selectedFile = fc.showOpenDialog(null);
        try {
            if (selectedFile != null) fis = new FileInputStream(selectedFile);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (selectedFile != null) {
//            System.out.println("selected file: " + selectedFile.getAbsolutePath());
//            System.out.println("Binary stream: " + fis.toString());
            System.out.println("I am null but here");
            imagePath.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void searchByName(ActionEvent event) {
        if (searchByName.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Enter the item id to search for");
            alert.showAndWait();
            return;
        }
        
        String search = enterItemName.getText();
        String query = "SELECT * FROM ITEM WHERE item_name LIKE '%" + search + "%'";
        
        ResultSet rs = handler.execQuery(query);
        try {
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Item Found!");
                alert.showAndWait();
                while(rs.next()) {
                    
                }
                FoundController.search = search;
                loadWindow("/buyAndSell/ui/found/found.fxml", "Item(s) Searched", StageStyle.UNDECORATED);
                
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Item Not Found!");
                alert.showAndWait();
            }
            
            
//        String query = "SELECT * FROM ITEM" +
//"WHERE item_name LIKE '%guitar%'"
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void searchById(ActionEvent event) {
        if (searchById.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Enter the item id to search for");
            alert.showAndWait();
            return;
        }
        
        String search = enterItemID.getText();
        String query = "SELECT * FROM ITEM";
        ResultSet rs = handler.execQuery(query);
        boolean found = false;
        try {
            while (rs.next()) {
                String item_id = rs.getString("item_id");
                System.out.println("id: " + item_id + " search for: " + search);
                if (item_id.toUpperCase().equals(search.toUpperCase())) {
                    found = true;
                    System.out.println("Here i've found the item and made the variable true");
//                    SingleItemFoundSample item = new SingleItemFoundSample( rs.getString("item_id"), 
//                                                                            rs.getString("item_name"), 
//                                                                            rs.getString("seller_id"), 
//                                                                            rs.getInt("price"), 
//                                                                            rs.getBoolean("is_avail"), 
//                                                                            rs.getBlob("image") );
                    
                    SingleItemFoundController.item_id2 =  rs.getString("item_id");
                    SingleItemFoundController.item_name2 =  rs.getString("item_name");
                    SingleItemFoundController.seller_id2 =  rs.getString("seller_id");
                    SingleItemFoundController.price2 =  rs.getInt("price");
                    SingleItemFoundController.is_avail2 =  rs.getBoolean("is_avail");
                    SingleItemFoundController.imagePath =  rs.getBlob("image") ;
                    
                    loadWindow("/buyAndSell/ui/singleItemFound/single_item_found.fxml", "Item Information", StageStyle.UNDECORATED);
                    
                    break;
                }
            }
            System.out.println("done with while loop");
            if (found) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Item Found!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Item Not Found!");
                    alert.showAndWait();
                    
//                    loadWindow(search, query);
                    
                }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
