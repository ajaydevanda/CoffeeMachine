package coffeemachine;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class itemsQuantity {
    static Map<String,Integer> itemQuantityMap;
    
    public itemsQuantity(){
        itemQuantityMap = new HashMap<String,Integer>();
    }
    public Map<String,Integer> getitemQuantityMap(){
        return itemQuantityMap;
    }
    
    public static void setTotalItemsQuantity(JSONObject totalQuantityObject){
        ArrayList<String> quantityList = getJsonKeys(totalQuantityObject);
        for(String key : quantityList){
            int itemValue = Integer.parseInt(String.valueOf(totalQuantityObject.get(key)));
            itemQuantityMap.put((String)key,itemValue);
        }
    }
    
    /*
        Update the available ingredients 
        availavleQuantity  =availavleQuantity - useQuantity
    */
    public static void updateItemsQuantity(Map<String,Integer> usedIngredients){
        for(Map.Entry<String,Integer> entry :usedIngredients.entrySet() ){
            String key = entry.getKey();
            int reqQuantity = entry.getValue(),availavleQuantity = itemQuantityMap.get(key);
            itemQuantityMap.put(key,availavleQuantity-reqQuantity);            
        }
    }
    
    
    /*Add the quantity in case ingredient goes low quantity*/
    public static void addIngredient(String name, int quantity){
        try {
            int availavleQuantity = itemQuantityMap.get(name);
            itemQuantityMap.put(name,quantity+availavleQuantity);
        }
        catch(Exception e){itemQuantityMap.put(name,quantity);};
        
        
    }
    static ArrayList<String> getJsonKeys(JSONObject jsonObject){
        return new ArrayList<String>(jsonObject.keySet());
    }
}
