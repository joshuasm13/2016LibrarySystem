package cu.cs.cpsc2150.project3;

/**
 * AccountWindow - JFrame displaying user account info to edit/add
 * 
 * @author jsm4  -  Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class AccountWindow extends JFrame {
	protected Person person;          /** Person who is logged in */
	protected JPanel mainPanel;		  /** Main panel */
	protected UserList UL;            /** User database */ 
	boolean editPersonOnly;			  /** Determines if account is new */
	
	/**
	 * @param u - List of users in database
	 * @param p - Account to edit
	 * @return - JFrame with user account info
	 */
	AccountWindow(UserList u, Person p){
		super();		
		this.setSize(450, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		UL = u;
		
		if(p == null){
			editPersonOnly = false;
			person = new Person(UL.getRowCount());
		}else{ 
			editPersonOnly = true;
			person = p;
		}
		this.initialize();
	}
	/**
	 * - Initializes GUI for editing accounts
	 */
	public void initialize() {		
		 
		mainPanel = new JPanel(new GridLayout(9,2,10,10));
		mainPanel.setBorder(new EmptyBorder(15,30,0,30));
		
	    final JTextField userField = new JTextField(person.getUserName());
	    final JTextField passField = new JTextField(person.getPasswordString());
	    final JTextField typeField = new JTextField(person.getType());
	    final JTextField nameField = new JTextField(person.getName());
	    final JTextField emailField = new JTextField(person.getEmail());
	    final JTextField phoneField = new JTextField(person.getPhone());
	    
	    
	   
	    JLabel userL = new JLabel("Username: ");
	    JLabel passL = new JLabel("Password: ");
	    JLabel typeL = new JLabel("Type: ");
	    JLabel nameL = new JLabel("Name: ");
	    JLabel emailL = new JLabel("Email: ");
	    JLabel phoneL = new JLabel("Phone: ");
	    
	    
	    JButton saveButton = new JButton("Save");
	    JButton cancelButton = new JButton("Cancel");
	    
	    mainPanel.add(userL);
	    mainPanel.add(userField);
	    
	    mainPanel.add(passL);
	    mainPanel.add(passField);
	    
	    mainPanel.add(typeL);
	    mainPanel.add(typeField);
	    
	    
	    mainPanel.add(nameL);
	    mainPanel.add(nameField);
	    
	    mainPanel.add(emailL);
	    mainPanel.add(emailField);
	    
	    mainPanel.add(phoneL);
	    mainPanel.add(phoneField);
	    
	    mainPanel.add(saveButton);
	    mainPanel.add(cancelButton);
	    

		this.setContentPane(mainPanel);
	    /**
	     * - Confirm account modifications
	     */
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			    int response = JOptionPane.showConfirmDialog(null, "Confirm Changes?", "Confirm",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    
			    if (response == JOptionPane.YES_OPTION) {
			    	PersonProxy pp = new PersonProxy(person, 
						userField.getText(),
						typeField.getText(),
						emailField.getText(),
						phoneField.getText(),
						passField.getText(),
						nameField.getText());
						
					if(pp.isSuccess()){
						if(!editPersonOnly){
							if(person.getType().equalsIgnoreCase("staff"))
								UL.add(new Staff(person));
							else
								UL.add(new Member(person));
						}
						AccountWindow.this.setVisible(false);
					}
					UL.fireTableDataChanged();
			    } 
			}
		});
		
		/** 
		 * Cancel Account modifications
		 */
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountWindow.this.setVisible(false);
			}
		});
		
	    /**
	     * Save to disk upon exiting edit window
	     */
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				UL.save();
			}
		});
	}
	
	
}
