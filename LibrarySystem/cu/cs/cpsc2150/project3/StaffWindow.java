package cu.cs.cpsc2150.project3;

/**
 * StaffWindow - Inherits from Person. Limited capabilities.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class StaffWindow extends MFrame {

	/**
	 * @param p - Person logged in
	 * @param u - User database
	 * @param b - Book database
	 */
	StaffWindow(Person p, UserList u, BookList b) {
		super(p,b,u);
		super.initialize();
	}
	
	private int lastId = -1;
	
	
	
	@Override
	public void initialize(){
	
		Catalog catalog = new Catalog(P, BL);
		catalog.initialize();
		super.setCatalog(catalog);
		
		/** 
		 * Since staff, add account window
		 */
		Accounts acc = new Accounts(UL);

		final JTextField tf = new JTextField();
		final JButton idB = new JButton("ID");
		final JLabel j = new JLabel();
		idBar.add(tf);
		idBar.add(idB);
		
		
		idBar.setPreferredSize(new Dimension(150,25));
		
		
		cart = new CartPanel(BL, null);
		cart.initialize();
		
		addTab("Catalog", new JScrollPane(catalog));
    	addTab("Accounts", new JScrollPane(acc));
		addTab("Checkout", cart);
				
		catalog.setVisible(true);
		
		/**
		 * Add ID button. If clicked, staff can view another account
		 */
		idB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				String s = tf.getText();
				int i; 

				try {
					i = Integer.parseInt(s);

				} catch (NumberFormatException e1){
					JOptionPane.showMessageDialog(new JFrame(),
								"Bad ID (Numbers Only)");
					return;
				}

				Person p = UL.getPerson(i);
				
				if(p!= null && p.getType().equalsIgnoreCase("staff")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Cannot Inquire Staff, Only Members");
					return; 
				}
					
					
				if(!s.matches("^[0-9]+$") || p == null){
					JOptionPane.showMessageDialog(new JFrame(),
							"Bad ID");
					return;
				}
		
				memberToInquire = p;
				
				if(lastId != i) {
					removeTop(j);
					j.setText("    Viewing:  " + p.getUserName() +
							"     ID: " + p.getId());
					j.setForeground(Color.RED);
					addTop(j);
				}
				
				lastId = i;
				cart.newPersonInquiry(p);
			}
		});
		
		
	}


}
