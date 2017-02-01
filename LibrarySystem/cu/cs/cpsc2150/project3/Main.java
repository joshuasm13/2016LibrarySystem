
/**
 * Main - Runs Catalog program. Pre-populates tables if none exist
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */
package cu.cs.cpsc2150.project3;

public class Main {
	public static void main(String []args){
		PersonData PD = new PersonData();	/** All users in the system */
		UserList UL = new UserList(PD);		/** All books in the system */
		
		BookData BD = new BookData();		/** Abstract table model of all books */
		BookList BL = new BookList(BD);		/** Abstract table model of all users */
		
		/**
		 * Pre-populate database if empty
		 */
		if(BL.getRowCount() == 0) {
			String [] a = {"20th century", "new york", "america"};
			String [] b = {"thriller","new york", "america"};
			String [] c = {"thriller", "spell", "adventure"};
			String [] d = {"rings", "adventure"};
			String [] e = {"pig", "adventure", "spider"};
			
			int N = 2;
			for(int i=1; i <=N; i++)
		     	BL.add(new Book("The Great Gatsby " + i, "F. Scott Fitzgerald", "Fiction", a));
			
			for(int i=1; i <=N; i++)
		     	BL.add(new Book("The Hunger Games " + i, "Suzanne Collins", "Fiction", b));
			
			for(int i=1; i <=N; i++)
		     	BL.add(new Book("Harry Potter " + i, "J.K.Rowling", "Fiction", c));
			
			for(int i=1; i <=N; i++)
		     	BL.add(new Book("The Lord of the Rings " + i, "J. Tolkien", "Fiction", d));
			
			for(int i=1; i <=N; i++)
		     	BL.add(new Book("Charlotte's Web " + i, "E.B. White", "Fiction", e));
		}
		
		/**
		 * Pre-populate database with users if empty
		 */
		if(UL.getRowCount() == 0){
			Person p1 = new Staff(0,"admin","(admin)", 0, "admin@clemson.edu", "8435551111");
			Person p2 = new Member(1,"mem1","josh", 0, "jsm4@g.clemson.edu", "1111111111");
			
			UL.add(p1);
			UL.add(p2);
		}
		
		Login myLogin = new Login(UL, BL);
		myLogin.initialize();
		myLogin.setVisible(true);
		

		
	}
}
