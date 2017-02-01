package cu.cs.cpsc2150.project3;

/**
 * Catalog - Displays all books in book database
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class Catalog extends JTable implements ListSelectionListener{
	BookList BL;				/** Book database */
	Person P;					/** Person logged in */
	Book selectedBook = null;	/** Book selected by user (listener) */
	
	/**
	 * @param p - Person logged in
	 * @param b - Book database
	 */
	Catalog(Person p, BookList b){
		super(b);

		BL = b;
		P = p;		
	}
	public Book getSelectedBook(){ return selectedBook; }
	
	public void initialize(){
       
		/**
		 * If double clicked row, view book information
		 */
        this.addMouseListener(new MouseAdapter() {
		  @Override
		  public void mouseClicked(MouseEvent e) {
		      if (e.getClickCount() == 2) {
		         JTable target = (JTable)e.getSource();
		         int row = target.getSelectedRow();
		         
		         String title = (String) BL.getValueAt(row, 0);
		         Book book = BL.getBook(title);
		         BookWindow BW = new BookWindow(BL, P, book);
		         BW.setVisible(true);      
		      }
		   }
		});
    
        /**
         * If selected, update current selected book
         */
        this.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
            	if(Catalog.this.getSelectedRow()>=0){
	                String s = Catalog.this.getValueAt(Catalog.this.getSelectedRow(), 0).toString();
	                
	                selectedBook = BL.getBook(s);
            	}
            }
        });
      	
    	this.setVisible(true);
	}

	
	
}
