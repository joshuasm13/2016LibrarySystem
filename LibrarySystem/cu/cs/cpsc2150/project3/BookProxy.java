package cu.cs.cpsc2150.project3;
/**
 * BookProxy - Intermediate between a book and calling function.
 *  		 - Validates info before adding to Book database
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class BookProxy implements BookInterface {
	private String title, author, genre, tag;		/** Book info to validate*/
	private Person borrower = null;					/** Person borrowing this book */
	private Book book;								/** Book to update if valid */
    private boolean Valid = false;					/** Book valid or not */
	
    /**
     * @param b - Original book
     * @param p - Person who checked out book
     * @param t - New title of book
     * @param a - New Author
     * @param g - New Genre
     * @param ta - New Tags
     */
	BookProxy(Book b, Person p, String t, String a, String g, String ta){
		book = b; 
		title = t;
		author = a;
		genre = g;
		tag = ta;
		borrower = p;	
		Validate();
		
	}
	
	/**
	 * Updates if new book info is valid
	 * @return true if value, else false
	 */
	public boolean isValid(){ return Valid; }
	
	/**
	 * Determines if data is valid. i.e. No empty strings etc. 
	 * Updates book if valid and sets valid boolean true;
	 */
	public void Validate(){
		String orig = "Invalid:";
		String s = orig;
		
		if(title == null || !(title.trim().length() > 0))
			s += " title,";
		if(author == null || !(author.trim().length() > 0))
			s += " author,";
		if(genre == null || !(genre.trim().length() > 0))
			s += " genre,";

		
		if(s.equals(orig)){
			setTitle(title);
			setAuthor(author);
			setGenre(genre);
			setTags(tag);
			setBorrower();
			Valid  = true;
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
				    "Book not added - " + s.substring(0,s.length()-1));
		}
			
	}

	public void setTitle(String title) { book.setTitle(title); }
	public void setAuthor(String author) { book.setAuthor(author); }
	public void setGenre(String genre) { book.setGenre(genre); }
	public void setTags(String s) { book.setTags(s); }
	public void setBorrower() { book.setBorrower(borrower); }

}
















