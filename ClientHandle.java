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

    private static Gui maingui;
    private Socket client;
    private InputStreamReader input_stream;
    private BufferedReader br;
    private String nameofclient;
    private String symbol;
    private Vector<Object []> client_vector;
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

        
        this.client_vector=Gui.getRowVector();
        
    }

    public void run(){
        this.handleClient();
        
    }

    public static void setGui(Gui gui){
        maingui=gui;
    }


    private void handleClient(){

        Iterator<Object[]> iterator = null;
        Object []row=null;
        String price;
        int i;
        try {
            this.p.println("Enter your name as security code ?");
            nameofclient=this.br.readLine();
            this.p.println("Your are welcome to bidding "+nameofclient+".Please enter your symbol to bid...");

            while (true) {
                symbol=this.br.readLine();
                iterator = this.client_vector.iterator();

                i= -1;
                while (iterator.hasNext()) {
                    i++;
                    if(!(row=iterator.next())[0].equals(symbol)){
                        row=null;
                    }else{
                        break;
                    }
                      
                }

                if (row!=null) {
                    this.p.println("Current bid price of your "+row[0].toString()+" is  "+row[2].toString());
                    break;
                }

                this.p.println("Your entered symbol is wrong enter correct one");
            }

            
            this.p.println("Enter your bid price ?");
            while (true) {
                
                price=this.br.readLine();
                row[2]= price;
                maingui.editRow(price, i,row);
                row=this.client_vector.get(i);
                this.p.println("Enter your bid price ? Current bid is "+row[2]);
            }

        } catch (Exception e) {
                //TODO: handle exception
            System.out.println("Error is in server handle(run method) "+e);
            
        }
       
    }
}