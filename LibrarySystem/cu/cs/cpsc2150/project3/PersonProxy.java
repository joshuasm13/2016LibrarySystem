package cu.cs.cpsc2150.project3;

/**
 * PersonProxy - Intermediate between a book and calling function.
 *  		   - Validates info before adding to User database
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class PersonProxy implements PersonInterface {
	private String username, type, email, 
		phone, ID, password, name;			/** Person info to validate */
	Person person;							/** Person to update */
	boolean Valid;							/** If Person is valid */
	
	/**
	 * @param p - Person to validate
	 * @param u - Username
	 * @param t - Type
	 * @param e - Email
	 * @param ph - Phone
	 * @param pa - Password
	 * @param n - Name
	 */
	PersonProxy(Person p, String u, String t, String e, 
			String ph, String pa, String n){
		username = u.trim();
		type = t.trim().toLowerCase();

		email = e.trim();
		phone = ph;
		password = pa;
		person = p;		
		name = n;
		Valid = false;
		Validate();
	}
	
	/**
	 * Conveys that new book info is valid
	 * @return true if valid book
	 */
	public boolean isSuccess(){ return Valid; }
	
	/**
	 * Determines if book info entered is valid
	 */
	public void Validate(){
		String orig = "Invalid:";
		String s = orig;

		if(username == null || !(username.trim().length() > 0))
			s += " username,";
		if( !(type.equalsIgnoreCase("staff") || type.equalsIgnoreCase("member")))
			s += " type,";
		if(!validEmail())
			s += " email,";
		if(!validPhone())
			s += " phone number,";
		if(!validPass())
			s += " password,";
		if(name.matches(".*[0-9].*"))
			s += " name,";
		
		if(s.equals(orig)){
			setType(type.toLowerCase());
			setPassword(password);
			setEmail(email);
			setPhone(phone);
			setName(name);
			setUserName(username);
		    // setID();
		
			Valid  = true;
		} else {
			JOptionPane.showMessageDialog(new JFrame(),
				    "User not added - " + s.substring(0,s.length()-1));
		}
			
	}
	
	/**
	 * @return true if phone number is valid
	 */
	private boolean validPhone(){
		if(phone == null) return false;
		
		phone = phone.replace("-", "");
		phone = phone.replace("(", "");
		phone = phone.replace(")", "");
		phone = phone.trim();

		if(!(phone.trim().length() > 0)) return false;
	
		if(!phone.matches("^[0-9]+$")) return false;

		return true;
	}

	/**
	 * @return true if password is valid
	 */
	private boolean validPass(){
		password = password.trim();
		if(password == null) return false;
		if(!(password.length() > 0)) return false;
		if(!password.matches("^[0-9]+$")) return false;

		return true;
	}
	
	/**
	 * @return true if email is valid
	 */
	private boolean validEmail(){
		if(email == null) return false;
		if(!(email.trim().length() > 0)) return false;
		if(!email.contains("@")) return false;
		if(!email.contains(".")) return false;
		if(email.substring(0,1).equals("@")) return false;
		if(email.substring(email.length()-1, email.length()).equals(".")) 
			return false;
		return true;
	}
	
	public void setUserName(String s) { person.setUserName(s); }
	public void setType(String s) { person.setType(s); }
	public void setName(String s) { person.setName(s); }
	public void setPassword(String i) { person.setPassword(i); }
	public void setEmail(String e) { person.setEmail(e); }
	public void setPhone(String p) { person.setPhone(p); }



}





