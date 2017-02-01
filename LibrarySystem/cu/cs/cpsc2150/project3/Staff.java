package cu.cs.cpsc2150.project3;

/**
 * Staff - Main screen when staff, not a member logs in
 * 
 * @author jsm4 | Joshua Moore
 * @since 4/27/2016
 * 
 * CSPC 2150 - Durkee - Project 3
 */

import java.util.ArrayList;


public class Staff extends Person{
	
	/**
	 * @param i - ID
	 * @param u - Username
	 * @param n - Full Name
	 * @param p - Password
	 * @param e - Email
	 * @param ph - Phone number
	 */
	
	Staff(int i, String u, String n, int p, String e, String ph){
		super(i,u,n,p,"staff",e,ph);
	}
	
	/**
	 * @param p - Person to copy info from to create new Member
	 */
	Staff(Person p){
		super(p.getId(),p.getUserName(),p.getName(),p.getPassword(),
				"staff",p.getEmail(),p.getPhone());
	}
}
