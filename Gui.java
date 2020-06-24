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

public class Gui extends JFrame{

    private static Vector<Object []> row_vector=new <Object[]> Vector();
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

        this.pane = new JScrollPane(this.table);
        this.pane.setBounds(0, 0, 580, 580);

        add(this.pane);

        Iterator<Object[]> iterator = Gui.row_vector.iterator();

        while (iterator.hasNext()) {
            model.addRow(iterator.next());
        }

        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    public static void rowAddToGui(){
        String [] dataset;
        String  data;
        Object[] row;
        try  
        {  
            //the file to be opened for reading  
            FileInputStream fis=new FileInputStream("stocks.csv");       
            Scanner sc=new Scanner(fis);    //file to be scanned  
            
            data=sc.nextLine();
            
            while(sc.hasNextLine())  
            {  
                data=sc.nextLine();
                dataset=data.split(",");

                row=new Object[3];

                row[0]=dataset[0];
                row[1]=dataset[1];
                row[2]=new Float(0.00);

                Gui.row_vector.add(row);
                 
            }  
            sc.close();     //closes the scanner  
        }  
        catch(IOException e)  
        {  
            e.printStackTrace();  
        }  
    }


    public synchronized void editRow(String value,int i,Object []element){
        try {
            Thread.sleep(500);
            this.model.setValueAt(value, i, 2);
            row_vector.set(i, element);
        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("System error  "+e);
        }
        
    }

}