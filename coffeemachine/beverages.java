package coffeemachine;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.util.*;
public class beverages {

    static Map<String,Map<String,Integer> > beveragesMap;
    
    public beverages(){
        beveragesMap = new HashMap<String,Map<String,Integer> >();
    }
    
    public Map<String,Map<String,Integer> > getBeverageMap(){
        return beveragesMap;
    }
    public static void setbeverage(JSONObject beverageObject ){
        ArrayList<String> beverageKeyList  = getJsonKeys(beverageObject);
        for(String item : beverageKeyList) {
            JSONObject itemQuantity = (JSONObject)beverageObject.get(item);
            Map<String,Integer> itemQuantityMap = setItemQuantity(itemQuantity);
            
            beveragesMap.put((String)item,itemQuantityMap);
            
            
        }
    }
    
    static Map<String,Integer> setItemQuantity(JSONObject itemQuantity){
        Map<String,Integer> itemMap = new HashMap<String,Integer>();
        ArrayList<String> quantity = getJsonKeys(itemQuantity);
        
        for(String key : quantity){
            int itemValue = Integer.parseInt(String.valueOf(itemQuantity.get(key)));
            itemMap.put((String)key,itemValue);
        }
     
        
        return itemMap;
    } 
    
    static ArrayList<String> getJsonKeys(JSONObject jsonObject){
        return new ArrayList<String>(jsonObject.keySet());
    }
}
