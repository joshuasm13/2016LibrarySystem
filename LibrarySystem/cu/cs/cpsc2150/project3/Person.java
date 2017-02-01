package cu.cs.cpsc2150.project3;

/**
 * Person - Single user and their info, including if staff or not 
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.io.Serializable;

public class Person implements Serializable{
	private static final long serialVersionUID = -2999604581550665810L;
	private String username, type, name, email, phone;	/** User info */
	private int id, password;							/** User info */
	private PersonInventory inventory ;					/** User book inventory */
	
	/**
	 * @param i -  id to add to new Person
	 */
	Person(int i){
		id = i;
		username = "";
		password = 0000000;
		type = "";
		name = "";
		email = "";
		phone = "";
		inventory  = new PersonInventory();

	};
	/**
	 * @param i - ID
	 * @param u - Username
	 * @param n - Name
	 * @param p - Password
	 * @param t - Type
	 * @param e - Email
	 * @param ph - Phone
	 */
	Person(int i, String u, String n, int p, String t, String e, String ph){
		id = i;
		username = u;
		password = p;
		type = t;
		name = n;
		email = e;
		phone = ph;
		inventory = new PersonInventory();

	}

	/**
	 * Turn int ID to string ID
	 * @return String ID
	 */
	public String getPasswordString(){
		return id==0 ? "N/A" : Integer.toString(password);
	}
	public void setUserName(String s) { username = s; }
	public void setID(int i){ id = i;}
	public void setPassword(String s){  password = Integer.parseInt(s); }
	public void setType(String s){ type = s; }
	public void setName(String s){ name = s; }
	public void setEmail(String e) { email = e; }
	public void setPhone(String p) { phone = p; }
	
	/**
	 * @return true if staff account
	 */
	public boolean isStaff() {
		return type.equalsIgnoreCase("staff") ? true : false;		
	}
	
	/**
	 * @param b - Book to add to checkout inventory
	 */
	public void addInventory(Book b){ 
		inventory.add(b);
		inventory.fireTableDataChanged();
	}
	/**
	 * @param b - Book to remove from checkout inventory
	 */
	public void removeInventory(Book b) { 
		inventory.remove(b); 
		inventory.fireTableDataChanged();
	}
	
	public int getId() { return id; }
	public String getUserName() { return username; }
	public int getPassword() { return password; }
	public String getType(){ return type; };
	public String getName(){ return name; };
	public String getEmail(){ return email; };
	public String getPhone(){ return phone; };
	public PersonInventory getInventory(){ return inventory; }
}





