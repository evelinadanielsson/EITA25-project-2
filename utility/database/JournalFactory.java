package database;

import java.util.ArrayList;

public class JournalFactory {
	ArrayList<String> r = new ArrayList<String>();
	ArrayList<String> rw = new ArrayList<String>();
	ArrayList<String> re = new ArrayList<String>();
	
	public JournalFactory() {
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
