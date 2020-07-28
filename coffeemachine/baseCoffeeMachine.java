package coffeemachine;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;
public class baseCoffeeMachine{
    JSONParser parser = null;
    String path = "./input.json"; 
    
    
    public baseCoffeeMachine(){
        parser = new JSONParser();
    }
    public JSONObject readItemFile() {
        try{
            return (JSONObject)parser.parse(new FileReader(path));
        }
        catch(FileNotFoundException fe){
            fe.printStackTrace();
            System.exit(0);
        }
        catch(IOException io) {
            io.printStackTrace();
            System.exit(0);
        }
        catch(ParseException pe){
            pe.printStackTrace();
            System.exit(0);
        }
        
        
        return null;
        
    }
    public int getOutlets(JSONObject machineObj){
        JSONObject outletObj = (JSONObject)machineObj.get("outlets");
        
        try {
            return Integer.parseInt(String.valueOf(outletObj.get("count_n")));
        }
        catch(Exception pe){return 0;}  
    } 
    public JSONObject getMachine(JSONObject fileObj){
        return (JSONObject)fileObj.get("machine");
    }
    
    public JSONObject getbeverages(JSONObject machineObj){
        return (JSONObject)machineObj.get("beverages");
    }
    
    public JSONObject getbItemsQuantity(JSONObject machineObj){
        return (JSONObject)machineObj.get("total_items_quantity");
    }
       
}
