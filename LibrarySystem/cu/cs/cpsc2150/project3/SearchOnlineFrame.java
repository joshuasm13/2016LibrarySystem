package cu.cs.cpsc2150.project3;

/**
 * SearchOnlineFrame - Displays users search results pulled from Internet
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class SearchOnlineFrame extends JFrame {
	SearchOnline list;	/** Parses and stores all books found */
	BookList BL;		/** Book database */
	String toSearch;	/** String to search online */
	JPanel left;		/** Holds book table **/
	JPanel right;		/** Holds add button */
	JPanel mainPanel;	/** Main Panel */
	boolean isStaff;	/** Is staff or not */
	
	/**
	 * @param b - Book database
	 * @param s - Phrase to search
	 * @param t - If staff account, then true
	 * @throws IOException - If any network errors encountered
	 */
	SearchOnlineFrame(BookList b, String s, boolean t) throws IOException{
		super("Online Results");	
		this.setSize(700, 400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		left = new JPanel();
		right = new JPanel();
		mainPanel = new JPanel(new BorderLayout());
	    toSearch = s;
		BL = b;
		isStaff = t;
		init();
	}
	
	
	private void init() throws IOException{
		
		mainPanel.add(left,BorderLayout.WEST);
		mainPanel.add(right, BorderLayout.EAST);
		list = new SearchOnline(toSearch);
		left.add(new JScrollPane(list));
		
		left.setPreferredSize(new Dimension(500,300));
		right.setPreferredSize(new Dimension(240,400));
		
		JButton addBookBttn = new JButton("Add To Catalog");
		right.add(Box.createVerticalStrut(75));
		right.add(addBookBttn);
		
		mainPanel.repaint();
		this.setContentPane(mainPanel);
		
		
		/**
		 * If account is staff, enable ADD button
		 */
		if(!isStaff)
			addBookBttn.setEnabled(false);
		
		addBookBttn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedBook() != null){
					BL.add(list.getSelectedBook());
					SearchOnlineFrame.this.setVisible(false);
				} else
					JOptionPane.showMessageDialog(new JFrame(),
							"Select a Book to Proceed.");
					
				
			}
		});
	

	}
	
	
	
	
	
	
	
	
	
	
	
	
}
