package cu.cs.cpsc2150.project3;
/**
 * Book - Single Book Object
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.io.Serializable;
import java.util.ArrayList;

public class Book implements BookInterface, Serializable{
	private static final long serialVersionUID = 8537801094028940253L;
	
	private String title, author, genre; /** Book info */
	private ArrayList<String> tags ;     /** Book tags */
	private Person borrower = null;      /** Account borrowing book */
	
	/**
	 * @param b - Create a book object from book B
	 */
	Book(Book b){
		this.title = b.getTitle();
		this.author = b.getAuthor();
		this.genre = b.getGenre();
		this.tags = b.getTagArray();
		this.borrower = b.getBorrower();
	}
	Book(){
		title = "";
		author = "";
		genre = "";
		tags = new ArrayList <String>();
	}
	
	/**
	 * - Primarily called by CartCatalog
	 * @param t - Title
	 * @param a - Author
	 * @param g - Genre
	 */
	Book(String t, String a, String g){
		title = t;
		author = a;
		genre = g;
		tags = new ArrayList <String>();
	}
	
	/**
	 * - Only used to pre-populate program w/ users
	 * @param t - Title	
	 * @param a - Author
	 * @param g - Genre
	 * @param b - Array with tags
	 */
	Book(String t, String a, String g, String[] b){
		title = t;
		author = a;
		genre = g;
		tags = new ArrayList<String>();
		for(int i=0; i<b.length; i++)
			tags.add(b[i]);
	}

	public void setTitle(String title) { this.title = title; }
	public void setAuthor(String author) { this.author = author; }
	public void setGenre(String genre) { this.genre = genre; }
	/**
	 * @param s - parses individual words and adds to array
	 */
	public void setTags(String s) { 
		String[] words = s.split("\\s+");
		for(int i=0; i<words.length; i++)	
			this.tags.add(words[i]); 
		}
	public void setBorrower(Person borrower) { this.borrower = borrower; }
		
	
	public String getTitle() { return title; }
	public String getAuthor() { return author; }
	public String getGenre() { return genre; }
	public Person getBorrower() { return borrower; }
	
	/**
	 * - Determines if word exists in tag array 
	 * @param s - tag word to search 
	 * @return - true if word found in tags, else false
	 */

	public boolean searchTag(String s){
		for(String a : tags)
			if(a.equalsIgnoreCase(s))
				return true;
		return false;
	}
	public ArrayList<String>getTagArray(){ return tags; }
	
	/**
	 * @return String of all tags
	 */
	public String getTags(){
		String t = new String ("");
	
		for(int i=0; i<tags.size(); i++)
			t += tags.get(i).toLowerCase() + "  ";
		
		return t;
	}
	

}
