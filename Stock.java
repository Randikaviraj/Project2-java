/**
 * Stock
 */
public class Stock {

    
    private Object [] row;
    private int no_of_bids;


    public Stock(Object [] row,int no_of_bids){
        this.row=row;
        this.no_of_bids=no_of_bids;
    }
    

    public Object[] getStock(){
        return this.row;
    }

    public void setStock(Object []row){
       this.row=row;;
    }

    public int getNoOfBids(){
        return this.no_of_bids;
    }

    public void setNoOfBids(int no_of_bids){
        this.no_of_bids=no_of_bids;
    }

    
}

