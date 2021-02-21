package database;

import java.util.ArrayList;

public class JournalPersonFactory {
	private ArrayList<Person> P_list;
	ArrayList<String> r = new ArrayList<String>();
	ArrayList<String> rw = new ArrayList<String>();
	ArrayList<String> re = new ArrayList<String>();
	
	public JournalPersonFactory() {
		P_list = new ArrayList<Person>();
		
		P_list.add(new Person("Evelina", 	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442601", "P01", "P_WARD01"));
		P_list.add(new Person("Johannes",	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442600", "P02", "P_WARD01"));
		P_list.add(new Person("Joel", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442602", "P03", "P_WARD02"));
		P_list.add(new Person("Frans", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442599", "D01", "DN_WARD01"));
		P_list.add(new Person("Emma",		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442604", "D02", "DN_WARD02"));
		P_list.add(new Person("Oscar", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442603", "N01", "DN_WARD01"));
		P_list.add(new Person("Olivia", 	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442605", "N02", "DN_WARD02"));
		P_list.add(new Person("GA", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442606", "G01", "G01"));
		
		ArrayList<String> r = new ArrayList<String>();
		r.add("R");
		r.add("-");
		r.add("-");
		ArrayList<String> rw = new ArrayList<String>();
		rw.add("R");
		rw.add("W");
		rw.add("-");
		ArrayList<String> re = new ArrayList<String>();
		re.add("R");
		re.add("-");
		re.add("E");
		this.r = r;
		this.rw = rw;
		this.re = re;
	}
	
	public ArrayList<Person> getPersonList() {
		return P_list;
	}
	
	
	/* Creates a list of journals, one for each patient with access as specified in CLIENTS.txt
	 * this is only done at server start up and is a substitute to having an actual database
	 */
	public ArrayList<Journal> createJournals() {
		ArrayList<Journal> J_list = new ArrayList<Journal>();
		
		// Creates Journal for P01 ------------------------
		ACL acl = new ACL();
		acl.addUID("G01", re);
		acl.addUID("P01", r);
		acl.addUID("D01", rw);
		acl.addUID("N01", rw);
		acl.addGID("DN_WARD01", r);
		ArrayList<String> contents = new ArrayList<String>();
		contents.add("Broken leg");
		Journal p01 = new Journal(acl, "P01", contents);
		J_list.add(p01);
		
		// Creates Journal for P02 ------------------------
		acl = new ACL();
		acl.addUID("G01", re);
		acl.addUID("P02", r);
		acl.addUID("D01", rw);
		acl.addUID("N02", rw);
		acl.addGID("DN_WARD01", r);
		contents = new ArrayList<String>();
		contents.add("Broken arm");
		Journal p02 = new Journal(acl, "P02", contents);
		J_list.add(p02);
		
		// Creates Journal for P03 ------------------------
		acl = new ACL();
		acl.addUID("G01", re);
		acl.addUID("P03", r);
		acl.addUID("D02", rw);
		acl.addUID("N02", rw);
		acl.addGID("DN_WARD02", r);
		contents = new ArrayList<String>();
		contents.add("Broken foot");
		Journal p03 = new Journal(acl, "P03", contents);
		J_list.add(p03);
		
		return J_list;
		
	}

}
