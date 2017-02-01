package cu.cs.cpsc2150.project3;

/**
 * CartPanel - Displays user's current inventory and pending transaction
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class CartPanel extends JPanel {
	private BookList BL;		/** Book database */
	private Person P;			/** Person as to who's account is being viewed */
	JTable table1;				/** Displays current inventory */
	Cart rawtable;				/** Person's pending transactions for release */
	PersonInventory pi;			/** Person's current inventory */
	
	/**
	 * @param b - Book database
	 * @param p - Account of info to view
	 */
	CartPanel(BookList b, Person p){
		super(new GridLayout(2, 1, 10, 10));
		BL = b;
		P = p;
	}
	
	/**
	 * Initialize inventory and pending transaction (cart) tables
	 */
	public void initialize(){
		pi = new PersonInventory();
			
		updateInventory();
			
		table1 = new JTable(pi);
		JScrollPane out = new JScrollPane(table1);
		
		rawtable = new Cart(BL,P);
		JTable table2 = new JTable(rawtable);
		

		JScrollPane queue = new JScrollPane(table2);
		
		/**
		 * If double clicked, get selected row and add to pending transaction table
		 */
        table1.addMouseListener(new MouseAdapter() {
		  @Override
		  public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() == 2) {
		         JTable target = (JTable)e.getSource();
		         int row = target.getSelectedRow();
		      
		         String title = (String) table1.getValueAt(row, 0);
		         Book book = BL.getBook(title);
		         addNewCheckout(book,"return");  
		         pi.remove(title);
		         pi.fireTableDataChanged();
		      }
		   }
		});
		
		

		this.add(out);
		this.add(queue);
		this.setVisible(true);
	}
	
	/**
	 * Removes table data and populates it with new person data
	 * @param p - person for staff to view
	 */
	public void newPersonInquiry(Person p){
		P = p;
		this.setVisible(false);
		this.removeAll();
		initialize();
		rawtable.fireTableDataChanged();
		pi.fireTableDataChanged();
	}
	
	/**
	 * Loads current person book inventory to table one
	 */
	public void updateInventory(){
		if(P == null) return;
		ArrayList<Book> og = P.getInventory().getArray();
		pi.removeAllData();
		for(Book b: og){
			Book nb = new Book(b.getTitle(),b.getAuthor(), b.getGenre());
			
			pi.add(nb);
		}
		pi.fireTableDataChanged();
		
	}

	public void clearCart(){
		rawtable.clearCart();
	}
	
	/**
	 * Notify books and people of changes
	 */
	public void finalize(){
		rawtable.notifyBooks();
	}
	
	/**
	 * @param b - book to add to transaction
	 * @param s - action being completed - return or checkout
	 */
	public void addNewCheckout(Book b, String s){
		rawtable.add(b,s.toLowerCase());
	}
}
