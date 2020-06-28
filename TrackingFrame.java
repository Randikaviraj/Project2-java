/**
 * TrackingFrame
 */
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.Font;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.util.Vector;
import java.util.Iterator;
import java.lang.Thread;

public class TrackingFrame extends JFrame{

    private JTable table;
    private DefaultTableModel model;
    private JScrollPane pane;
    private TrackedStock st;

    public TrackingFrame(String symbol){
        super("Tracking On symbol "+symbol);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.table = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {                
                return false;               
            };
        }; 

        // create a table model and set a Column Identifiers to this model 
        Object[] columns = {"Client Name","Price"};
        this.model = new DefaultTableModel();
        this.model.setColumnIdentifiers(columns);

        // set the model to the table
        this.table.setModel(this.model);

        //table Appearance
        this.table.setBackground(Color.black);
        this.table.setForeground(Color.white);
        Font font = new Font("",1,18);
        this.table.setFont(font);
        this.table.setRowHeight(20);


        this.pane = new JScrollPane(this.table);
        this.pane.setBounds(0, 0, 580, 580);

        add(this.pane);

        Iterator<TrackedStock> iterator = TrackedStock.getTracking().iterator();
        
        while (iterator.hasNext()) {
            st=iterator.next();
            if (!st.getSymbol().equals(symbol)) {
                st=null;
            } else {
                break;
            }
            
        }

        Iterator<String> clients=st.getAllClients().iterator();
        Iterator<Float> prices=st.getAllPrices().iterator();
        Object [] temp;
        while(clients.hasNext()){
            temp=new Object[2];
            temp[0]=clients.next();
            temp[1]=prices.next();
            model.addRow(temp);
        }

        setSize(600,600);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    public void refresh(){
        int noofrows=0;
        Iterator<String> clients=null;
        Iterator<Float> prices=null;
        Object [] temp;
        int row=0;
        while (true) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                //TODO: handle exception
                System.out.print("Eroor in server refresh()");
            }
            
            noofrows=this.model.getRowCount();

            clients=st.getAllClients().iterator();
            prices=st.getAllPrices().iterator();
            row=0;

            while(clients.hasNext()){
                temp=new Object[2];
                temp[0]=clients.next();
                temp[1]=prices.next();
                if (row < noofrows) {
                    this.model.setValueAt(temp[0], row, 0);
                    this.model.setValueAt(temp[1], row, 1);
                } else {
                    this.model.addRow(temp);
                }
                row++;
            }
      
        }
        
    }
}