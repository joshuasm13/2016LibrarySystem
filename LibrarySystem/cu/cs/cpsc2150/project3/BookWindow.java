package cu.cs.cpsc2150.project3;

/**
 * BookWindow - Displays book information to edit
 * 
 * @author jsm4 | Joshua Moore
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class BookWindow extends JFrame {
	private Book book;			/** Book to view */
	private Person person;		/** Person viewing book */
	private BookList BL;		/** Book database */
	boolean editBookOnly;		/** Editing or creating a new book */
	
	/**
	 *  If book info empty, it is a new book. 
	 * @param bl - Book database
	 * @param p - Person logged in
	 * @param b - Book to view
	 */
	BookWindow(BookList bl, Person p, Book b){
		super("Book Details - " + b.getTitle());	
		this.setSize(400, 300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		book = b;
		person = p;
		BL = bl;
		if(book.getTitle().equals(""))
			editBookOnly = false;
		else
			editBookOnly = true;
		this.initialize();
	}
	
	public Book getBook(){ return book; }
	
	/**
	 * - Display Book Info to Edit/View
	 */
	public void initialize() {	
		
		JPanel mainPanel = new JPanel(new GridLayout(6,2,10,10));
		mainPanel.setBorder(new EmptyBorder(25,50,25,50));
		
	    final JTextField titleField = new JTextField(book.getTitle());
	    final JTextField authorField = new JTextField(book.getAuthor());
	    final JTextField genreField = new JTextField(book.getGenre());
	    final JTextField tagsField = new JTextField();
	    
	    /**
	     * If member, don't allow to edit
	     */
	    if(!person.getType().equals("staff")){
	    	titleField.setEditable(false);
	    	authorField.setEditable(false);
	    	genreField.setEditable(false);
	    	tagsField.setEditable(false);
	    }
	    
	    JLabel titleL = new JLabel("Title: ");
	    JLabel authorL = new JLabel("Author: ");
	    JLabel genreL = new JLabel("Genre: ");
	    JLabel tagL = new JLabel("Enter More Tags: ");
	    JLabel tagL2 = new JLabel("Tags (Scroll): ");
	    
	    JTextArea tags = new JTextArea();	    
	    
	    String tag = book.getTags();
	    if(tag.equals(""))
	    	tags = new JTextArea("(no tags)");
	    else
	    	tags = new JTextArea(book.getTags());
	    
	    tags.setEditable(false);
	    tags.setLineWrap(true);
	    tags.setWrapStyleWord(true);
	     
	    
	    JButton saveButton = new JButton("Save");
	    JButton cancelButton = new JButton("Cancel");
	    JButton removeButton = new JButton("Delete Book");
	  
	    
	    mainPanel.add(titleL);
	    mainPanel.add(titleField);
	    
	    mainPanel.add(authorL);
	    mainPanel.add(authorField);
	    
	    mainPanel.add(genreL);
	    mainPanel.add(genreField);
	    
	    mainPanel.add(tagL2);
	    mainPanel.add(new JScrollPane(tags));
	    
	    mainPanel.add(tagL);
	    mainPanel.add(tagsField);
	    
	    /**
	     * Add save, delete and cancel buttons if staff logged in
	     */
	    if(person.getType().equalsIgnoreCase("staff")){
		    mainPanel.add(saveButton);
		   
		    if(!book.getTitle().equals("")){
		    	mainPanel.setLayout(new GridLayout(7,2,10,10));
		     	if(book.getBorrower()!=null)
		    		mainPanel.add(new JLabel("Borrower ID: " + book.getBorrower().getId()));
		    	mainPanel.add(removeButton);
		   
		    }
		    mainPanel.add(cancelButton);
		
	    }	    
	    
		this.setContentPane(mainPanel);
	
		/**
		 * Update book info if clicked
		 */
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			    int response = JOptionPane.showConfirmDialog(null, "Confirm Changes?", "Confirm",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.YES_OPTION) {
			    	BookProxy bp = new BookProxy(book, null,  
			    			titleField.getText(),
			    			authorField.getText(),
			    			genreField.getText(),
			    			tagsField.getText());
					
					if(bp.isValid()) {
						if(!editBookOnly)BL.add(book);
						BookWindow.this.setVisible(false);
						//success = true;
					}
					BL.fireTableDataChanged();
			    } 
			}
		});
		
		

		
		/**
		 * Do not save book info if clicked
		 */
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BookWindow.this.setVisible(false);
			}
		});
		
		/**
		 * Delete book from database if clicked
		 */
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Delete Book?", "Confirm",
				        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.YES_OPTION) {
			    	BL.removeBook(book);
			    	BookWindow.this.setVisible(false);
			    } 
			}
		});
		
		
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				BL.save();
			}
		});
	}
	
	
	
}
