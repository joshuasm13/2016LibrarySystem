package cu.cs.cpsc2150.project3;

/**
 * Accounts - JTable displaying all user accounts
 * 
 * @author jsm4  -  Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;

public class Accounts extends JTable {
	UserList UL;   /** User database */
	
	
	/**
	 * @param u - List of users in database
	 * @return - Accounts Jtabel with all user accounts
	 */
	Accounts(UserList u){
		super(u);
		UL = u;
		
		this.initialize();
	}
	
	/**
	 * Adds double click listener to Account Table - brings up edit window
	 */
	public void initialize(){
	
        this.addMouseListener(new MouseAdapter() {
		  @Override
		  public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() == 2) {
		         JTable target = (JTable)e.getSource();
		         int row = target.getSelectedRow();
		         
		         int id = (int) UL.getValueAt(row, 0);
		         Person p = UL.getPerson(id);
		         EditAccountWindow AW = new EditAccountWindow(UL,p);

		         AW.setVisible(true);      
		      }
		   }
		});
        
		
		this.setVisible(true);

	}

}
