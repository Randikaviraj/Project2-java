import java.net.*;
import java.lang.Thread;
import java.io.*;

public class  Server{
    public static void main(String []args) throws Exception{

        int port_no=2000;

        ServerSocket server=new ServerSocket(port_no);
        Socket client=null;
      
        new Thread(){
            public void run(){
                
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                String line = "";
                
                while (true) {
                    try {
                        line = in.readLine();
                        if (line.equals("quit")) {
                            in.close();
                            server.close();
                            System.exit(0); 
                        } 
                    } catch (Exception e) {
                        //TODO: handle exception
                        System.out.println("Error in your command");
                    }
                }  
            }
        }.start();



        new Thread(){
            public void run(){
                Gui.makeGuiVector();
                Gui serverGui=new Gui();
                Symbols.setGui(serverGui);
                
            }
        }.start();
        
    



        System.out.println("Server is starting on port............"+port_no);

        while(true){
            client=server.accept();
            new ClientHandle(client).start();
            System.out.println("Server is listening  ");
        }
    }
}