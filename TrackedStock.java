import java.util.Vector;

/**
 * TrackedStock
 */
public class TrackedStock {

    private String symbol;
    private Vector<Float> price;
    private Vector<String> clints;
    private static Vector<TrackedStock> tracking=new Vector<TrackedStock>();

    

    public TrackedStock(String symbol,float price){
        this.symbol=symbol;
        this.price=new Vector<Float>();
        this.price.add(price);
        this.clints=new Vector<String>();
        this.clints.add("initial");
    }

    public static void setTracking(TrackedStock t){
        tracking.add(t);
    }

    public static Vector<TrackedStock> getTracking(){
        return tracking;
    }


    public void addClint(String client){
        this.clints.add(client);
    }

    public void updatePrice(float price){
        this.price.add(price);
    }

    public float getPrice(){
        return this.price.lastElement();
    }

    public String getSymbol(){
        return this.symbol;
    }

    public Vector<String> getAllClients(){
        return this.clints;
    }
    public Vector<Float> getAllPrices(){
        return this.price;
    }
}