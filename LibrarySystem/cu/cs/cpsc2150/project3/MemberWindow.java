package cu.cs.cpsc2150.project3;

/**
 * MemberWindow - Main screen when a member, not staff logs in
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */


import javax.swing.JScrollPane;

public class MemberWindow extends MFrame {
	
	/**
	 * @param p - Main frame to view if a Member
	 * @param u - User database
	 * @param b - Book database
	 */
	MemberWindow(Person p, UserList u, BookList b) {
		super(p,b,u);
		super.initialize();
	}
	
	/**
	 * Create new Catalog with all books database
	 */
	@Override
	public void initialize(){
		
		Catalog catalog = new Catalog(P, BL);
		catalog.initialize();
		super.setCatalog(catalog);
	
		cart = new CartPanel(BL, P);
		cart.initialize();
		
		super.addTab("Catalog", new JScrollPane(catalog));
		super.addTab("Checkout", cart);
		
		catalog.setVisible(true);
	
		
	}
}
