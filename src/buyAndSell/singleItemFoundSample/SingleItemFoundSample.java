
package buyAndSell.singleItemFoundSample;

import java.sql.Blob;

public class SingleItemFoundSample {
    public String itemId;
    public String itemName;
    public String sellerId;
    public int price;
    public boolean isAvail;
    public Blob imagePath;

    public SingleItemFoundSample(String itemId, String itemName, String sellerId, int price, boolean isAvail, Blob imagePath) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellerId = sellerId;
        this.price = price;
        this.isAvail = isAvail;
        this.imagePath = imagePath;
    }
    
}
