/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

/**
 *
 * @author Miguel Martrinez
 */
public enum PurchaseItems {
    NOADS("ads","$0.99","noads",0),SMALLPACK("smallpack","$0.99","smallpack",15),SMALLPACKGAMEOVER("smallpackGameOver","$0.99","smallpack",15),
    HIGHPACK("highpack","$3.99","highpack",100),MINIPACK("minipack","5,000","minipack",10);
    
String itemName;
String itemPrice;
String SKU;
int chickensBought;
    
    private PurchaseItems(String itemName, String itemPrice, String sku, int chickens){
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        SKU = "com.darkfig." + sku;//"android.test.purchased";// + sku;
        chickensBought = chickens;
    }
   
    public static String getItemPriceThroughName(String name){
        for(PurchaseItems item: PurchaseItems.values())
            if(name.equalsIgnoreCase(item.itemName))
                return item.itemPrice;
        return "";
    }
    
    public static PurchaseItems getItemThroughName(String name){
        for(PurchaseItems item: PurchaseItems.values())
            if(item.getItemName().equalsIgnoreCase(name))
                return item;
        return null;
    }
    
    public String getItemName(){
        return itemName;
    }
    
    public String getItemPrice(){
        return itemPrice;
    }
    
    public String getSku(){
        return SKU;
    }
    
    public int getChickensBought(){
        return chickensBought;
    }
}
