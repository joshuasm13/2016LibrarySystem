package cu.cs.cpsc2150.project3;

/**
 * Cart - Displays pending return or checkout items
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class Cart extends AbstractTableModel {
	private ArrayList<Book> books = new ArrayList<Book>();        /** Books pending */
	private ArrayList<String> action = new ArrayList<String>();	  /** Corresponding action */
	private static String[] colNames = {"Action", "Book"};		  /** Column names */
	private Person person;										  /** Person viewing */
	private BookList BL;										  /** Book database */
	
	/**
	 * 
	 * @param b - Book database
	 * @param p - Account of info to view
	 */
	Cart(BookList b, Person p){
		person = p;
		BL = b;
	}
	
	/**
	 * @param p - Book in pending transaction
	 * @param a - return or checkout action tag
	 */
	public void add(Book p,String a){
		books.add(p);
		action.add(a);
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return books.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(arg1 == 0)
			return action.get(arg0);
		if(arg1 == 1) 
			return books.get(arg0).getTitle();
		return null;
	}
	
	
	@Override
	public String getColumnName(int index){
		return colNames[index];
	}
	
	/**
	 * Observer pattern. Notifies books and user of checkout/check-in
	 */
	public void notifyBooks(){
		for(int i=0; i<books.size(); i++){
			
		    // System.out.println(i);
			
			Book ub = BL.getBook(books.get(i).getTitle());

			if(action.get(i).equals("checkout")){
				ub.setBorrower(person);
				person.addInventory(ub);
				
			} else {
				ub.setBorrower(null);
				person.removeInventory(ub);
			}
			
		}
		
		books.clear();
		action.clear();
		
		this.fireTableDataChanged();
	    BL.fireTableDataChanged();
		
	}
	
	public void clearCart(){
		action.clear();
		books.clear();
		this.fireTableDataChanged();
	}
		
}















