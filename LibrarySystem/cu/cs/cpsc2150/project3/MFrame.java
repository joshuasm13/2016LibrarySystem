package cu.cs.cpsc2150.project3;

/**
 * MFrame - Main window in which content will lie
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
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class MFrame extends JFrame{
	protected Person P;		/** Person logged in */
	protected BookList BL;	/** Book database */
	protected UserList UL;  /** User database */
	
	/**
	 * 
	 * @param p - Person Logged in
	 * @param b - Book database
	 * @param u - User database
	 */
	MFrame(Person p, BookList b, UserList u){
		super(p.getUserName() + " - " + p.getType() + " Account");	
		this.setSize(950, 550);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		BL = b;
		P = p;
		UL = u;
		memberToInquire = null;
		if(P.getType().equals("member"))
			memberToInquire = p;
		
	}
	
	private JPanel top  = new JPanel(new FlowLayout(FlowLayout.LEFT));
	protected JTabbedPane left  = new JTabbedPane();				
	protected JPanel right = new JPanel(new BorderLayout());		
	protected JPanel buttonPanel;										
	private JPanel mainPanel = new JPanel(new BorderLayout());
	protected JButton extraButton = new JButton();
	private JButton logoutButton;
	protected JPanel idBar =  new JPanel(new GridLayout(1,2,10,0));
	private JTextField searchField;
	protected Person memberToInquire;
	protected JCheckBox fuzzySearchBox;
	private boolean fuzzYesNo = false;
	JButton searchOnlineBttn;
	protected CartPanel cart;
	private Catalog catalog;
	protected void setCatalog(Catalog c){ catalog = c; }
	protected void setMemberToInquire(Person p) { memberToInquire = p; }
	
	public void initialize() {			
		
		/** 
		 * Add Side Panel (Tabs)
		 */
		left.setPreferredSize(new Dimension(750, 550));
		mainPanel.add(left,BorderLayout.WEST);
	
		
		/**
		 *  Add User Info Pane
		 */
		JPanel sideText = new JPanel(new GridLayout(4,1));
		JLabel label1 = new JLabel("Username: " + P.getUserName());
		JLabel label2 = new JLabel("ID: " + P.getId());
		JLabel label3 = new JLabel("Name: " + P.getName());
		JLabel label4 = new JLabel("Type: " + P.getType());
		sideText.add(label1);
		sideText.add(label2);
		sideText.add(label3);
		sideText.add(label4);
	    right.add(sideText,BorderLayout.NORTH);
		
	    buttonPanel = new JPanel();
	    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
	    
	    logoutButton = new JButton("Logout");

		addRight(logoutButton);
		right.add(buttonPanel,BorderLayout.SOUTH);
		
		right.setPreferredSize(new Dimension(200,600));
		right.setBorder(new EmptyBorder(15,15,15,15));
		
		mainPanel.setBorder(new EmptyBorder(0,0,0,0));
		mainPanel.add(right, BorderLayout.EAST);
		
		top.setBorder(new EmptyBorder(10,10,0,10));
		top.setPreferredSize(new Dimension(750,50));
		
		mainPanel.add(top, BorderLayout.NORTH);
		searchField = new JTextField();
		searchField.setPreferredSize(new Dimension(150,25));
		JButton searchButton = new JButton("Search Book");
		JButton resetButton = new JButton("Reset");
		
		fuzzySearchBox = new JCheckBox("Fuzzy");
	    searchOnlineBttn = new JButton("≫ Add From Web ≪");
		
		addTop(searchField);
		addTop(searchButton);
		addTop(resetButton);
		addTop(fuzzySearchBox);
		
		addTop(Box.createHorizontalStrut(25));
		
		addRight(searchOnlineBttn);
		searchOnlineBttn.setBackground(Color.ORANGE);
		
		this.setContentPane(mainPanel);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				BL.save();
				UL.save();
			}
			
		});
		
		/**
		 * If clicked open online search window
		 */
		searchOnlineBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				
			  String input = JOptionPane.showInputDialog("Pull Book Info Online (Search Any Phrase)  ");
			  
			  if(input != null){
					try {
						SearchOnlineFrame OSF = new SearchOnlineFrame(BL, input, P.isStaff());
			
						OSF.setVisible(true);
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(new JFrame(),
							    "Not Connected To Internet, Try Again.");
					}
			   }	
			}
		});
		
		/**
		 * Implement fuzzy search if checked
		 */
		
		fuzzySearchBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				fuzzYesNo = !fuzzYesNo;
			}
		});
		
		/**
		 * Start search if clicked
		 */
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				BL.setIsSwappedFalse();
				BL.fireTableDataChanged();
				
				if(fuzzYesNo)
					BL.fuzzySearch(searchField.getText());
				else{

					BL.strictSearch(searchField.getText());
				}
				
				BL.fireTableDataChanged();
				//left.setSelectedIndex(0);
				mainPanel.repaint();
			}
		});
		
		/**
		 * Reset catalog back to normal if clicked
		 */
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BL.setIsSwappedFalse();
				BL.fireTableDataChanged();
				mainPanel.repaint();
			}
		});
	
		/**
		 * Log out of account and display login screen
		 */
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MFrame.this.setVisible(false);
				Login login = new Login(UL,(BookList)BL);
				login.initialize();
				login.setVisible(true);
				
			}
		});
		
		/**
		 * Determine if tab is changed and display appropriate buttons
		 */
		left.addChangeListener(new ChangeListener() {
		        public void stateChanged(ChangeEvent e) {
		        	
		        	if(BL.getIsSwapped()){
		        		BL.setIsSwappedFalse();
		        		BL.fireTableDataChanged();
		        	}
		        
		        	buttonPanel.removeAll();
		        	top.remove(idBar);
		        	mainPanel.repaint();
		        	
		        	if(P.getType().equalsIgnoreCase("staff")){
			            if((left.getSelectedIndex() == 0) ){
			        		extraButton = newExtraButton(left, "New Book");
			        		addRight(extraButton);
			        		addRight(searchOnlineBttn);
			            }
			            else if((left.getSelectedIndex() == 1)){
			        		extraButton = newExtraButton(left, "New Account");
			        		addRight(extraButton);
			            }
			            else if((left.getSelectedIndex() == 2)){
			            	addTop(idBar);
			            	newCOkayButton();
			        		newCCancelButton();
			        		extraButton = newExtraButton(left, "Add Checkout");
			        		//extraButton.setEnabled(false);
			        		addRight(extraButton);
			            }
			        }
		        	else {
		        	    if((left.getSelectedIndex() == 0) ){
			        		addRight(searchOnlineBttn);
			            }
		        		if((left.getSelectedIndex() == 1)){
			        		newCOkayButton();
			        		newCCancelButton();
			        		extraButton = newExtraButton(left, "Add Checkout");
			        
			        		addRight(extraButton);
		        		}
		        	}
		        	addRight(logoutButton);
		       }
		  });
	 
		 
	}
	
	/**
	 * Button to confirm checkout changes
	 */
	private void newCOkayButton(){
		
		JButton newBttn = new JButton("Confirm Changes");
		
		 newBttn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cart.finalize();
					cart.updateInventory();
				}
			});
		 addRight(newBttn);	

	}

	/**
	 * Button to cancel transaction
	 */
	private void newCCancelButton(){
		
		JButton newBttn = new JButton("Clear (Cancel)");
		
		 newBttn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cart.clearCart();
					cart.updateInventory();
				}
			});
		 addRight(newBttn);

	}

	/**
	 * Auxiliary button based on selected tab
	 * @param jp - Tabbed pane
	 * @param s - Which button was clicked
	 * @return - New Button according what was clicked
	 */
	private JButton newExtraButton(final JTabbedPane jp, String s){
		
		JButton newBttn = new JButton(s);
		newBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JButton j = (JButton) e.getSource();
				
				String type = j.getText();

				if(type.equalsIgnoreCase("New Book")){
					BookWindow BkWndw = new BookWindow(BL,P, new Book());
					BkWndw.setVisible(true); 
				} else if (type.equals("New Account")){
					//Person nperson = new Person(UL.getRowCount());
					AccountWindow AccWndw = new AccountWindow(UL,null);
					AccWndw.setVisible(true); 					
				} else if (type.equals("Add Checkout")){
					if(memberToInquire == null && P.getType().equalsIgnoreCase("staff")){
						JOptionPane.showMessageDialog(new JFrame(),
								"Enter ID Before Proceeding");
					}
					else {
						jp.setSelectedIndex(0);	
						newSelectButton(jp);
					}
				}
			}
		});
		
		return newBttn;
	}
	
	/*
	 * When pressed, switches tabs to catalog and allows user checkout book
	 */
	private void newSelectButton(final JTabbedPane jp){
		buttonPanel.removeAll();

		JButton newBttn = new JButton("Select");
		addRight(newBttn);
		newBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(P.getType().equalsIgnoreCase("staff"))
					jp.setSelectedIndex(2);
				else
			     	jp.setSelectedIndex(1);
							
				Book temp = catalog.getSelectedBook();
				if(temp.getBorrower()== null)
					cart.addNewCheckout(temp, "checkout");
				else
					JOptionPane.showMessageDialog(new JFrame(),
						    "Book Already Checked Out. Select Another.");
				
			}
		});
		addRight(logoutButton);
	}
	
	
	/**
	 * Add new tab
	 * @param s - Tab title
	 * @param c - Component panel to add
	 * @return newly added component
	 */
	public Component addTab(String s, Component c){
		return left.add(s,c);
	}
	
	/**
	 * Add new button to right panel
	 * @param c - component to add
	 */
	public void addRight(Component c){
		JPanel wrapper = new JPanel(new BorderLayout());
		wrapper.add(c,BorderLayout.CENTER);
		buttonPanel.add(wrapper,BorderLayout.SOUTH);
	}
	
	@Override 
	public Component add(Component c){
		return mainPanel.add(c);
	}
	
	/**
	 * @param c - Component to add to Top of window
	 */
	public void addTop(Component c){
		top.add(c);
	}
	
	/**
	 * @param c - Component to remove from Top of window
	 */
	public void removeTop(Component c){
		top.remove(c);
	}

	
}
















