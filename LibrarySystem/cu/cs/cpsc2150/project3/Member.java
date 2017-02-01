package cu.cs.cpsc2150.project3;

/**
 * Member - Inherits from Person. Limited capabilities.
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

public class Member extends Person{
	
	/**
	 * @param i - ID
	 * @param u - Username
	 * @param n - Full Name
	 * @param p - Password
	 * @param e - Email
	 * @param ph - Phone number
	 */

	Member(int i, String u, String n, int p, String e, String ph){
		super(i,u,n,p,"member",e,ph);
	}
	
	
	/**
	 * @param p - Person to copy info from to create new Member
	 */
	Member(Person p){
		super(p.getId(),p.getUserName(),p.getName(),p.getPassword(),
				"member" ,p.getEmail(),p.getPhone());
	}
	
}
