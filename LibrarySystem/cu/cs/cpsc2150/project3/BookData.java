package cu.cs.cpsc2150.project3;

/**
 * BookData - Array holding all books. Saves data to file.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.io.*;
import java.util.ArrayList;

public class BookData {
	
	private static final String SAVE_PATH = "bookdata-jsm4.dat";
	
	public ArrayList<Book> myRows;		/** Individual books in an array */
	
	/**
	 * Load any book data from file upon creation
	 */
	BookData() {
		load();
	}
	
	public BookData returnListCopy(){
		BookData nBL= new BookData();
		for(Book b:myRows)
			nBL.addRow(b);
		return nBL;
	}
	public void addRow(Book r) {
		myRows.add(r);
	}
	
	public Book getRow(int i) {
		return myRows.get(i);
	}
	
	public int getNumRows() {
		return myRows.size();
	}
	
	/**
	 * - Write Book info to file
	 * @exception If file not found
	 */
	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH));
			oos.writeObject(myRows);
			oos.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	/**
	 * @exception if found not found, create new array of book data
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_PATH));
			myRows = (ArrayList<Book>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			myRows = new ArrayList<Book>();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}
}
