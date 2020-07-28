import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import coffeemachine.*;

/*
item : hot_coffee, black_tea,hot_tea....
ingredients : water, milkm sugar...

*/
public class coffeeMachine extends baseCoffeeMachine{
    static beverages beveragesObj = null;                   // class for store the items with its requred ingredients
    static itemsQuantity itemsQuantityObj = null;           // class  for store  the available ingredients into the machine
    static Map<Integer,String> beverageChoice;              // 
    static JSONObject fileObj=null,machineObject=null;
    static BufferedReader br;
    static int totalOutlets = 0;
    static boolean lowIngredientAlarm;                      // alarm in case of quantity goes low
    
    /*
    Initialise the classes object and variables    
    */
    coffeeMachine(){
        beveragesObj = new beverages();
        itemsQuantityObj = new itemsQuantity();
        beverageChoice = new HashMap<Integer,String>();
        br = new BufferedReader(new InputStreamReader(System.in)); 
        lowIngredientAlarm = false;
    }
    
    /*
        beverageChoice map to show the choice of available items
        id   item
        1 : hot_coffee
        2 : green_tea
        3 : black_tea
        4 : hot_tea
        .
        .
        .
    */
    void setbeverageChoices() {
       ArrayList<String> itemList = new ArrayList<String>(beveragesObj.getBeverageMap().keySet());
       for(int i=0;i<itemList.size();i++){
          beverageChoice.put(i+1,itemList.get(i));
       }
    }
    
    
    /*
        initialise the (initial quantity)available ingredients map(itemQuantityMap) and quantiy of every ingredients for item
        
    */
    void setItemAndBeveragesQuantity(){
         try {
            fileObj = readItemFile();
            machineObject = getMachine(fileObj);
            beveragesObj.setbeverage(getbeverages(machineObject));
      
            itemsQuantity.setTotalItemsQuantity(getbItemsQuantity(machineObject));
            
            totalOutlets = getOutlets(machineObject);
            
            setbeverageChoices();
       
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /* User interface for choice selection*/
    void printUserInterFace(){
        System.out.println("Please select items from below items by their id and can select up to "+totalOutlets + " or press -1 for switch off the machine"); 
        System.out.println("id  : item");
        for(Map.Entry<Integer,String> entry :beverageChoice.entrySet() ){
            System.out.println( entry.getKey() + " : " +entry.getValue());
        }
    }
    
    
    /*
        procees the selected choice of items
    */
    void processBeverage(int itemId){
        try {
            String item = beverageChoice.get(itemId);
            Map<String,Integer> requiredIngredients = beveragesObj.getBeverageMap().get(item);
        
            boolean canPreparedItem = isIngredientsSuffecient(item,requiredIngredients);
            if(canPreparedItem){
                System.out.println(item + " is prepared");
                itemsQuantityObj.updateItemsQuantity(requiredIngredients);
            }
        }catch(Exception e){
             System.out.println("Plese choose correct option");
        }
        
    }
    
    /* Add the low quantity ingredients into machine*/
    void addIngredientsProcess(){
        System.out.println("Do you want to add ingredient? Press 1 for yes or press 0 for try with another item");
        while(true){
            try{
                int choice = Integer.parseInt(br.readLine());
                if(choice==1) {
                    System.out.println("Enter ingredient name and its quantity");
                    String[] ingredient = br.readLine().split(" ");
                    String name = ingredient[0];
                    int quantity = Integer.parseInt(ingredient[1]);
                    itemsQuantityObj.addIngredient(name,quantity);
                    return ;
                }
                else if(choice==0) return;
            }
            catch(Exception e){}
            System.out.println("Press 1 for yes or press 0 for try with another item");
        }
        
    }
    
    /*Check for safe cooking(every required ingredients for item shoud be present with enough quantity in machine)*/
    boolean isIngredientsSuffecient(String item, Map<String,Integer> requireIngredients){
        Map<String,Integer> availableIngredients= itemsQuantityObj.getitemQuantityMap();
        
        for(Map.Entry<String,Integer> entry :requireIngredients.entrySet() ){
            String key = entry.getKey();
            int reqQuantity = entry.getValue(),availavleQuantity;
            
            try {
                availavleQuantity= availableIngredients.get(key);
            }
            catch(NullPointerException e){availavleQuantity=0;}
     
            if(availavleQuantity==0){
                System.out.println(item+ " cannot be prepared because "+key+" is not available");
                lowIngredientAlarm=true;
                return false;
            }
            else if(reqQuantity>availavleQuantity){
                System.out.println(item+ " cannot be prepared because item "+key+" is not sufficient");
                lowIngredientAlarm=true;
                return false;
            }
        }        
        return true;
    }
    
    
    
    /* Main Function*/
    public static void main(String[] arg) throws IOException{
        coffeeMachine coffeeMachineObj = new coffeeMachine();
         
        try {
        
	        coffeeMachineObj.setItemAndBeveragesQuantity(); 
	     
	        do{
	            if(lowIngredientAlarm){
	                coffeeMachineObj.addIngredientsProcess();
	                lowIngredientAlarm = false;
	            }
	            coffeeMachineObj.printUserInterFace();
	            
	            String selectedBeverages[] = br.readLine().split(" ");
	            
	            if(selectedBeverages.length>totalOutlets){
	                System.out.println("there are only "+ totalOutlets + " outlets");
	            }
	            else if(selectedBeverages.length==1 && Integer.parseInt(selectedBeverages[0])==-1) {
	                break;
	            }
	            else{
	                for(String choice : selectedBeverages){
	                    try{
	                        int beverageId = Integer.parseInt(choice);
	                        coffeeMachineObj.processBeverage(beverageId);
	                    }
	                    catch(Exception e){
	                        e.printStackTrace();
	                        System.out.println("Plese choose correct option");
	                    }
	                }
	            }
	        }
	        while(true);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    
    
    
    
    
    
    
}
