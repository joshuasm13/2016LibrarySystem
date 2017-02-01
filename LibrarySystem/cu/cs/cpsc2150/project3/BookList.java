package cu.cs.cpsc2150.project3;
/**
 * BookList - Used to display Book data from array into a table form.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class BookList extends AbstractTableModel {
	protected BookData  BL;			                 /** Book database */
	protected static String[] colNames = 
		{"Title", "Author", "Genre", "Checked Out"}; /** Table columns */
	protected boolean isSwappedFuzzy = false;        /** Fuzzy Search in progress */
	protected boolean isSwappedStrict = false;	     /** Strict Search in progress */
	private BookList SearchInstance;				 /** Search Singleton Instance */


	/**
	 * @param tableData - Sophisticated Array containing book information
	 */
	BookList(BookData tableData){
		super();
		BL = tableData;
	}
	
	
	public void save(){
		BL.save();
	}
	/**
	 * - Conveys that the current table should display all books, instead of search
	 */
	public void setIsSwappedFalse(){
		isSwappedFuzzy = false;
		isSwappedStrict = false;
	}
	
	/** 
	 * @return true if search is displayed, false if all books are displayed
	 */
	public boolean getIsSwapped() { 
		return (isSwappedFuzzy || isSwappedStrict) ? true : false;
	}
	
	public ArrayList<Book> getArray(){
		return BL.myRows;
	}
	
	public void add(Book p){
	
		Book bk = getBook(p.getTitle());
		
		if(p.getTitle().equals("")){
			JOptionPane.showMessageDialog(new JFrame(),
					"Bad Title");
			return;
		}
		if(bk == null) {
			BL.addRow(p);
			this.fireTableDataChanged();
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(),
					"Book Already exists");
		}
	}
	
	/**
	 * Add a book from the phrase search to temporary table
	 *   -Eliminates warning messages as with regular add
	 * @param p - book to add to temp table
	 */
	
	public void addFromSearch(Book p){
	
		Book bk = getBook(p.getTitle());
		
		if(bk == null) {
			BL.addRow(p);
			this.fireTableDataChanged();
		}
	}

	/**
	 * @param s - Phrase to compare
	 * @param c - Word to compare
	 * @return true, if word is in phrase
	 * @return i - Conveys if fuzzy or strict search
	 */
	
	private boolean containsWord(String s, String c, int i){
		if(s.equalsIgnoreCase(c))
			return true;
		else if(i == 1)
			return false;
		
		String[] words = c.split("\\s+");
		
		for(String w : words)
			if(w.equalsIgnoreCase(s))
				return true;
		
		return false;
	}
	
	/**
	 * Completes fuzzy search of word s, in Book database
	 * @param B - Book database to search
	 * @param s - String to search for
	 * @param j - Conveys if fuzzy or strict search is called
	 */
	public void search(ArrayList<Book> B, String s, int j){
		String [] w = null;
		if(j==0){
			w = s.split("\\s+");
		} else {
			w = new String[1];
			w[0] = s;
		}

		for(Book b : B){
			for(int i=0; i<w.length; i++){
				if (b.searchTag(w[i]))
					addFromSearch(b);
				else if (containsWord(w[i],b.getTitle(),j))
					addFromSearch(b);
				else if (containsWord(w[i], b.getAuthor(),j))
					addFromSearch(b);
				else if(containsWord(w[i], b.getGenre(),j))
				    addFromSearch(b);
			}
		}
	}
	
	/**
	 * Searches for books with string s. Swap tables to display search info. 
	 * @param s - String to fuzzy search
	 */
	public void fuzzySearch(String s){
		isSwappedFuzzy = true;
		SearchInstance = new BookList(new BookData());
		SearchInstance.BL.myRows.clear();
		SearchInstance.search(BL.myRows, s,0);
	}
	
	/**
	 * Searches for books with string s. Swap tables to display search info. 
	 * @param s - String to strict search
	 */
	public void strictSearch(String s){
		isSwappedStrict = true;
		SearchInstance = new BookList(new BookData());
		SearchInstance.BL.myRows.clear();
		SearchInstance.search(BL.myRows, s, 1);
	}
	
	/**
	 * @param s - Title of book to search
	 * @return - Corresponding book
	 */
	public Book getBook(String s){
		for(Book B: BL.myRows)
			if(B.getTitle().equalsIgnoreCase(s))
				return B;
		return null;
	}
	
	/**
	 * @param P - Book object to remove
	 */
	public void removeBook(Book P) {
		if(BL.myRows.contains(P))
			BL.myRows.remove(P);
		this.fireTableDataChanged();		
	}
	
	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		if(isSwappedFuzzy || isSwappedStrict) 
			return SearchInstance.getRowCount();

		return BL.getNumRows();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(isSwappedFuzzy || isSwappedStrict) 
			return SearchInstance.getValueAt(arg0, arg1);

		if(arg1 == 0)
			return BL.getRow(arg0).getTitle();
		if(arg1 == 1) 
			return BL.getRow(arg0).getAuthor();
		if(arg1 == 2) 
			return BL.getRow(arg0).getGenre();
		if(arg1 == 3) 
			if( BL.getRow(arg0).getBorrower() != null)  return "yes";
			else return "no";
		return null;
	}
	
	
	@Override
	public String getColumnName(int index){
		return colNames[index];
	}
}
