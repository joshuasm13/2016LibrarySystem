package cu.cs.cpsc2150.project3;

/**
 * EditAccountWindow - Called if user is only editing an account, not adding a new one
 * 					 - Enabled a remove user button
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class EditAccountWindow extends AccountWindow {
	Person toDelete;     /** Person to delete from User Database */

	/**
	 * @param u - User DataBase
	 * @param p - Person to view
	 */
	EditAccountWindow(UserList u, Person p) {
		super(u,p);
		toDelete = p;
		this.addRemoveButton();
	}
	
	/**
	 * If not adding new account, insert this button. Deletes user.
	 */
	public void addRemoveButton(){
	    JButton removeButton = new JButton("Delete Account");
	    //new GridLayout(9,2,10,10);
	    mainPanel.add(removeButton);
	    
		
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Delete User?", "Confirm",
				        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.YES_OPTION) {
			    	if(UL.removePerson(toDelete)){
			    		JOptionPane.showMessageDialog(new JFrame(),
							    "Cannot Delete Only Admin Account");
			    	} else {
			    		EditAccountWindow.this.setVisible(false);
			    	}
			    } 
			
			}
		});
		
	}

	

}
