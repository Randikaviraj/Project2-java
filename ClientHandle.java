/**
 * ClientHandle
 */
import java.net.*;
import java.lang.Thread;
import java.io.*;
import java.lang.Exception;
import java.util.Iterator;
import java.util.Vector;

public class ClientHandle extends Thread{
    
    private Socket client;
    private InputStreamReader input_stream;
    private BufferedReader br;
    private String nameofclient;
    private String symbol;
    private static Vector<Symbols> symbol_vector;
    private PrintStream p;

    ClientHandle(Socket client){
        try {
            this.client=client;
            this.p=new PrintStream(this.client.getOutputStream());
            this.input_stream=new InputStreamReader(this.client.getInputStream());
            this.br=new BufferedReader(input_stream);
            System.out.println("client connected");
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Error is in server handle "+e);
        }  
        
    }

    public static void setClientVector(Vector<Symbols> symbol_vector){
        ClientHandle.symbol_vector=symbol_vector;
    }

   

    public void run(){
        this.handleClient();
        
    }

    


    private void handleClient(){
        
        Iterator<Symbols> iterator = null;
        Symbols sym=null;
        String price;
        float bidprice=0;
        

        try {
            
            this.p.println("Enter your name as security code ?");
            this.nameofclient=this.br.readLine();
            this.p.println("Your are welcome to bidding "+this.nameofclient+".Please enter your symbol to bid...");

            while (true) {
                symbol=this.br.readLine();
                iterator = symbol_vector.iterator();
                System.out.println("2");
                while (iterator.hasNext()) {
                    sym=iterator.next();
                    if(!sym.getSymbol().equals(symbol)){
                        sym=null;
                    }else{
                        break;
                    } 
                }

                if (sym!=null) {
                    this.p.println("Let's bid on "+sym.getSymbol());
                    break;
                }

                this.p.println("Your entered symbol is wrong enter correct one");
            }

          
            this.p.println("Enter your bid price ?");
            while (true) {
                price=this.br.readLine();

                if (isNumber(price)) {
                    bidprice=sym.controlStock(symbol,price);
                } else {
                    this.p.println("Error,--Enter a valid bid price ? ");
                    return;
                    
                }
                
                this.p.println("Enter your next bid price ? Current bid is "+bidprice);
            }

        } catch (Exception e) {
                //TODO: handle exception
            System.out.println("Error is in server handle(run method) "+e);
            
        }
       
    }


    public static boolean isNumber(String num) {
        if (num == null) {
            return false;
        }
        try {
            float f = Float.parseFloat(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}


