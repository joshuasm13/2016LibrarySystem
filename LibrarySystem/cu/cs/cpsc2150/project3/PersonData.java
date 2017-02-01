package cu.cs.cpsc2150.project3;

/**
 * PersonData - Array holding all users. Saves data to file.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.io.*;
import java.util.ArrayList;

public class PersonData {
	
	private static final String SAVE_PATH = "userdata-jsm4.dat";
	
	public ArrayList<Person> myRows;
	
	PersonData() {
		load();
	}
	/**
	 * @param new Person to add to user database
	 */
	public void addRow(Person r) {
		myRows.add(r);
	}
	
	public Person getRow(int i) {
		return myRows.get(i);
	}
	
	public int getNumRows() {
		return myRows.size();
	}
	
	/**
	 * - Write Person info to file
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
			myRows = (ArrayList<Person>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			myRows = new ArrayList<Person>();
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
	}
}
