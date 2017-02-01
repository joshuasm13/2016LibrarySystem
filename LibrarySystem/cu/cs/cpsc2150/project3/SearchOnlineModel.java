package cu.cs.cpsc2150.project3;

/**
 * SearchOnlineModel - Contains Online search result raw data
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class SearchOnlineModel extends AbstractTableModel{
	ArrayList<Book> BL = new ArrayList<Book>();   /** Book database */
	private String[] colNames = 
		{ "Title", "Author", "Genre" };			  /** Table column names */

	
	void clear(){ BL.clear(); }
	
	public void add(Book p){
		BL.add(p);
		this.fireTableDataChanged();
	}

	/**
	 * @param s - Title of book to return
	 * @return book with selected title 
	 */
	public Book getBook(String s){
		Book bk = null;
		for(Book B: BL){
			if(B.getTitle().equalsIgnoreCase(s))
				bk = B;
		}
		return bk;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
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
	
	

}
