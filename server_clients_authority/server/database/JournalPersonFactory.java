package database;

import java.util.ArrayList;
import java.util.HashMap;

public class JournalPersonFactory {
	private ArrayList<Person> P_list;
	HashMap<String, ArrayList<Person>> relations;
	ArrayList<String> r = new ArrayList<String>();
	ArrayList<String> rw = new ArrayList<String>();
	ArrayList<String> re = new ArrayList<String>();

	public JournalPersonFactory() {
		P_list = new ArrayList<Person>();
		Person evelina = new Person("Evelina", 	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442601", "P01", "P_DIV1","patient", "DIV1");
		P_list.add(evelina);
		Person johannes = new Person("Johannes",	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442600", "P02", "P_DIV1","patient", "DIV1");
		P_list.add(johannes);
		Person joel = new Person("Joel", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442602", "P03", "P_DIV2","patient", "DIV2");
		P_list.add(joel);
		P_list.add(new Person("Frans", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442599", "D01", "DN_DIV1", "doctor", "DIV1"));
		P_list.add(new Person("Emma",		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442604", "D02", "DN_DIV2", "doctor", "DIV2"));
		P_list.add(new Person("Oscar", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442603", "N01", "DN_DIV1", "nurse", "DIV1"));
		P_list.add(new Person("Olivia", 	"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442605", "N02", "DN_DIV2", "nurse", "DIV2"));
		P_list.add(new Person("GA", 		"EMAILADDRESS=fake@email.com, CN=CA, OU=None, O=None, L=Lund, ST=Skane, C=SE", "233517865169099216092418102268418889084600442606", "G01", "G01", "gov", "-"));

		// Hard code Doctor Patient relations----------------------
		relations = new HashMap<String, ArrayList<Person>>();
		// Doctor Frans first
		ArrayList<Person> temp = new ArrayList<Person>();
		temp.add(evelina);
		temp.add(johannes);
		relations.put("D01", temp);
		// Doctor Emma now
		temp = new ArrayList<Person>();
		temp.add(joel);
		relations.put("D02", temp);
		// --------------------------------------------------------


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
		acl.addGID("DN_DIV01", r);
		ArrayList<String> contents = new ArrayList<String>();
		contents.add("Broken leg");
		Journal p01 = new Journal(acl, "JP01", contents);
		J_list.add(p01);

		// Creates Journal for P02 ------------------------
		acl = new ACL();
		acl.addUID("G01", re);
		acl.addUID("P02", r);
		acl.addUID("D01", rw);
		acl.addUID("N02", rw);
		acl.addGID("DN_DIV01", r);
		contents = new ArrayList<String>();
		contents.add("Broken arm");
		Journal p02 = new Journal(acl, "JP02", contents);
		J_list.add(p02);

		// Creates Journal for P03 ------------------------
		acl = new ACL();
		acl.addUID("G01", re);
		acl.addUID("P03", r);
		acl.addUID("D02", rw);
		acl.addUID("N02", rw);
		acl.addGID("DN_DIV02", r);
		contents = new ArrayList<String>();
		contents.add("Broken foot");
		Journal p03 = new Journal(acl, "JP03", contents);
		J_list.add(p03);

		return J_list;

	}

	public Journal createNewJournal(Person doctor, String journalID, String patientID, String nurseID, String content){
		ACL acl = new ACL();
		acl.addUID("G01", re); // government agency
		acl.addUID(patientID, r);
		acl.addUID(doctor.getUID(), rw);
		acl.addUID(nurseID, rw);
		acl.addGID(doctor.getGID(), r);
		ArrayList<String> contents = new ArrayList<String>();
		contents.add(content);
		Journal journal = new Journal(acl, journalID, contents);
		return journal;
	}

	public HashMap<String, ArrayList<Person>> createRelations(){
		return relations;
	}

}
