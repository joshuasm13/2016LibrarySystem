package cu.cs.cpsc2150.project3;

/**
 * PersonInventory - Displays Users current inventory of checkout books
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class PersonInventory extends AbstractTableModel {
	private ArrayList<Book> BL = new ArrayList<Book>();		/** Book database */
	private static String[] colNames = 
		{"Title", "Author", "Genre"};						/** Table column names */
	PersonInventory(){}

	public void removeAllData(){
		BL = new ArrayList<Book>();
	}
	
	/**
	 * @param p - Book to add
	 */
	public void add(Book p){
		BL.add(p);
		this.fireTableDataChanged();
	}
	
	/**
	 * @param b - Book to remove
	 */
	public void remove(Book b){
		String title = b.getTitle();
		Book toDelete = null;
		for(Book bk : BL)
			if(bk.getTitle().equalsIgnoreCase(title))
				toDelete = bk;
		BL.remove(toDelete);
		this.fireTableDataChanged();
	}
	/**
	 * @param s - Title of book to remove
	 */
	public void remove(String s){
		Book toDelete = null;
		for(Book b: BL)
			if(b.getTitle().equalsIgnoreCase(s))
				toDelete = b;
		
		//System.out.println(toDelete.getTitle());
		BL.remove(toDelete);
		
		this.fireTableDataChanged();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return BL.size();
	}
	
	@Override
	public Object getValueAt(int arg0, int arg1) {
		
		if(arg1 == 0)
			return BL.get(arg0).getTitle();
		if(arg1 == 1) 
			return BL.get(arg0).getAuthor();
		if(arg1 == 2) 
			return BL.get(arg0).getGenre();
		return null;
	}
	
	
	@Override
	public String getColumnName(int index){
		return colNames[index];
	}
	
	public ArrayList<Book> getArray(){
		return BL;
	}
}
