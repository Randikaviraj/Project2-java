import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.Vector;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.Thread;
import java.net.Socket;
import java.awt.event.*;
import java.util.Random;
import java.text.DecimalFormat;

public class Gui extends JFrame{

    private static Vector<Stock> row_vector=new <Stock>Vector();
    private JTable table;
    DefaultTableModel model;
    JScrollPane pane;
    

    public static Vector getRowVector(){
        return Gui.row_vector;
    }

    

    public Gui(){
        super("Bid Table");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.table = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                return false;               
            };
        }; 

        // create a table model and set a Column Identifiers to this model 
        Object[] columns = {"Symbol","Name","Price"};
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);

        // set the model to the table
        this.table.setModel(this.model);

        //table Appearance
        this.table.setBackground(Color.blue);
        this.table.setForeground(Color.red);
        Font font = new Font("",1,22);
        this.table.setFont(font);
        this.table.setRowHeight(30);
        
       

        table.addMouseListener(new MouseAdapter(){
        
            public void mouseClicked(MouseEvent e){
                
                
                new Thread(){
                    public void run(){
                        new TrackingFrame(table.getValueAt(table.getSelectedRow(), 0).toString()).refresh();
                    }
                }.start();
                
            }
        });

        this.pane = new JScrollPane(this.table);
        this.pane.setBounds(0, 0, 580, 580);

        add(this.pane);

        Iterator<Stock> iterator = Gui.row_vector.iterator();
        Stock stock;
        while (iterator.hasNext()) {
            stock=iterator.next();
            model.addRow(stock.getStock());
        }

        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    public static  void makeGuiVector(){
        String [] dataset;
        String  data;
        Object[] row;
        Stock stock;
        Vector <Symbols> symbols=new Vector<Symbols>();
        Random random = new Random();
        DecimalFormat df = new DecimalFormat("0.00");
        

        try  
        {  
            //the file to be opened for reading  
            FileInputStream fis=new FileInputStream("stocks.csv");       
            Scanner sc=new Scanner(fis);    //file to be scanned  
            
            data=sc.nextLine();
            float rannum=0;
            while(sc.hasNextLine())  
            {  
                data=sc.nextLine();
                dataset=data.split(",");
                rannum = (random.nextFloat()*1000000)%30 +10;
                
                row=new Object[3];

                row[0]=dataset[0];
                row[1]=dataset[1];
                row[2]= Float.parseFloat(df.format(rannum));

                TrackedStock.setTracking(new TrackedStock(dataset[0],Float.parseFloat(df.format(rannum))));

                stock=new Stock(row,0);
                Gui.row_vector.add(stock);
                symbols.add(new Symbols(dataset[0]));
                 
            }  
            sc.close();     //closes the scanner  
            ClientHandle.setClientVector(symbols);
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();
            System.exit(-1);  
        }  
    }


    public synchronized float editRow(String symbol,String price,String name){
        
        Object []row=null;
        Stock temp=null,element=null;
        Iterator<Stock> itr=row_vector.iterator();
        int i=-1;
        int no_of_bids=0;
        float bid=0;

        while (itr.hasNext()) {
            i++;
            element=itr.next();
            if (!(element.getStock())[0].equals(symbol)) {
                element=null;
            } else {
                row=element.getStock();
                no_of_bids=element.getNoOfBids();
               break; 
            }
            
        }

        try {
            
            if (Float.parseFloat(row[2].toString())< Float.parseFloat(price)) {
                
                row[2]=Float.parseFloat(price);
                no_of_bids++;
                Iterator<TrackedStock> iter;
                iter=TrackedStock.getTracking().iterator();
                TrackedStock item;
                int index =0;
                while (iter.hasNext()) {
                    item=iter.next();
                    if (!item.getSymbol().equals(symbol)) {
                        continue;
                    }else{
                        item.addClint(name);
                        item.updatePrice(Float.parseFloat(price));

                    }
                    index++;
                }
            }

            
            bid=Float.parseFloat(row[2].toString());
            element.setNoOfBids(no_of_bids);
            element.setStock(row);
            row_vector.set(i, element);
            this.model.setValueAt(row[0].toString(), i, 0);
            this.model.setValueAt(row[1].toString(), i, 1);
            this.model.setValueAt(row[2].toString(), i, 2);
            

            while (i!=-1) {
                element=row_vector.get(i);
                row=element.getStock();

                this.model.setValueAt(row[0], i, 0);
                this.model.setValueAt(row[1], i, 1);
                this.model.setValueAt(row[2], i, 2);

                if(i!=0 && row_vector.get(i-1).getNoOfBids()<no_of_bids){
                    temp = row_vector.get(i-1);
                    row_vector.set(i-1, element);
                    row_vector.set(i, temp);

                    row=temp.getStock();
                    this.model.setValueAt(row[0], i, 0);
                    this.model.setValueAt(row[1], i, 1);
                    this.model.setValueAt(row[2], i, 2);
                }else{
                    break;
                }
                
                i--;
            }
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Erorr  "+e);
            return -1;
        }
        return bid;
    }

}