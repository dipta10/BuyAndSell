
package buyAndSell.singleItemFoundSample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;


public class MultipleItemTable {
    public final SimpleStringProperty itemName;
    public  SimpleStringProperty itemId;
    public  SimpleStringProperty sellerId;
    public  SimpleBooleanProperty isAvail;

    public MultipleItemTable(String itemName, String itemId, String sellerId, boolean isAvail) {
        this.itemName = new SimpleStringProperty(itemName);
        this.itemId = new SimpleStringProperty(itemId);
        this.sellerId = new SimpleStringProperty(sellerId);
        this.isAvail = new SimpleBooleanProperty(isAvail);
    }
    
    public String getItemName() {
            return itemName.get();
    }
    
}
