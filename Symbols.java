/**
 * Symbols
 */
import java.io.*;


public class Symbols {
    private String symbol;
    private static Gui maingui;

    public Symbols(String symbol){
        this.symbol=symbol;
    }

    public static void setGui(Gui gui){
        Symbols.maingui=gui;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public synchronized float controlStock(String symbol,String price){
        try {
            Thread.sleep(500);
            return maingui.editRow(symbol,price);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Error in thread");
            return -1;
        }
        
    }
}