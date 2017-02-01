package cu.cs.cpsc2150.project3;

/**
 * Login - Displays login screen. Starts main program if correct username and password.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame{
	UserList UL;		/** User database */
	BookList BL;		/** Book database */
	
	/**
	 * @param U - User list database
	 * @param B - Book list database
	 */
	Login(UserList U, BookList B){
		super("Login");	
		UL = U;
		BL = B;
		
		this.setSize(300, 150);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocationRelativeTo(null);
	}
	
	public void initialize() {

		JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setContentPane(mainPanel);
		
		
		JLabel label1 = new JLabel("Username:");
		JLabel label2 = new JLabel("Password:");
	    final JTextField idField = new JTextField("admin");
	    final JTextField passField = new JTextField();
		JButton loginButton = new JButton("Login");
		JButton cancelButton = new JButton("Cancel");
		String s = idField.getText();
		
		mainPanel.add(label1);
		mainPanel.add(idField);
		mainPanel.add(label2);
		mainPanel.add(passField);
		mainPanel.add(loginButton);
		mainPanel.add(cancelButton);
		
		/**
		 * If login pressed, check if valid password/username and if in database.
		 */
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usr = idField.getText();
				String pass = passField.getText();
				int i = -1;
				
				try {
					i = Integer.parseInt(pass);
				} catch (NumberFormatException e1){
					if(!usr.equals("admin")){
						JOptionPane.showMessageDialog(new JFrame(),
								"Bad Password (Numbers Only)");
						return;
					}
				}
			
				Person p1 = UL.searchUserList(usr,i);
				
				/**
				 * If account valid, create catalog window
				 */
				if(p1 !=null){
					Login.this.setVisible(false);
					MFrame f1, f2;
					
					if(p1.getType().equalsIgnoreCase("staff")){
					  f1 = new StaffWindow(p1,UL, BL);
					}
					else if(p1.getType().equalsIgnoreCase("member")){
					  f1 = new MemberWindow(p1, UL, BL);
					} 
					else {
						  f1 = null;
					 }
					
				    f1.initialize();
				    f1.setVisible(true);			    
		     	}
					
				else {
					JOptionPane.showMessageDialog(new JFrame(),
					    "User Not Found - Bad Username/Password .");
				}
				
				
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UL.save();
				BL.save();
			
				Login.this.setVisible(false);
				System.exit(0);
			}	
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				BL.save();
				UL.save();
			}
		});
		

	}
	
	
	
	
}
