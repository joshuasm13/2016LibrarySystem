package cu.cs.cpsc2150.project3;

/**
 * UserList - Used to display Person data from array into a table form.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;


public class UserList extends AbstractTableModel{
	private PersonData UL;        /** Holds individual users in an arraylist */
	private static String[] colNames = 
		{"ID", "Username", "Name", "Password", "Account Type"};  /** Column names */
	
	UserList(PersonData p){
		UL = p;
	}
	
	/**
	 * Save to Disk
	 */
	public void save(){
		UL.save();
	}
	
	public Person getPerson(Person p){
		Person toReturn = null;
		for(Person P: UL.myRows)
			if(P == p)
				toReturn = P;
		return toReturn;
	}
	/**
	 * @param i - ID to search User by
	 * @return Person inquired 
	 */
	public Person getPerson(int i){
		Person toReturn = null;
		for(Person P: UL.myRows)
			if(P.getId() == i)
				toReturn = P;
		return toReturn;
	}
	public boolean removePerson(Person P) {
		if(this.getRowCount() != 1)
			UL.myRows.remove(P);
	    else 
		    return true;
		
		this.fireTableDataChanged();
		return false;
		
	}
	public void add(Person p){
		Person ps = getPerson(p);
		if(ps == null) {
			UL.myRows.add(p);
			this.fireTableDataChanged();
		}
		else {
			JOptionPane.showMessageDialog(new JFrame(),
					"UserName Already exists");
		}

		this.fireTableDataChanged();

	}
	
	/**
	 * If username is admin, no password necessary.
	 * @param s - username of person wishing to login
	 * @param p - password of person wishing to login
	 * @return
	 */
	public Person searchUserList(String s, int p){
		for(Person P: UL.myRows)
			if(P.getUserName().equalsIgnoreCase(s) && P.getPassword() == p)
				return P;		
			else if(P.getUserName().equals(s) && s.equals("admin"))
				return P;
		return null;
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public int getRowCount() {
		return UL.myRows.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if(arg1 == 0) return UL.myRows.get(arg0).getId();
		if(arg1 == 1) return UL.myRows.get(arg0).getUserName();
		if(arg1 == 2) return UL.myRows.get(arg0).getName();
		if(arg1 == 3) {
			if(UL.myRows.get(arg0).getUserName().equalsIgnoreCase("admin"))
				return "N/A";
			return UL.myRows.get(arg0).getPassword();
		}
		if(arg1 == 4) return UL.myRows.get(arg0).getType();
		return null;
	}
	
	@Override
	public String getColumnName(int index){
		return colNames[index];
	}

		
}
