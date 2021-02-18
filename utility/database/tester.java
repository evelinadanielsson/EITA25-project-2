package database;

import java.util.ArrayList;

public class tester {

	public static void main(String[] args) {
		// create factory
		JournalFactory factory = new JournalFactory();
		// factory creates journals as specified in CLIENTS
		// So our database is basically the information in JournalFactory
		ArrayList<Journal> journals = factory.createJournals();
		
		// try for access like this:
		
		/*client with UID "D01" and GUI "DN_WARD01" wants
		 delete/execute access to journal for UID "P01" */
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getPatientID().equals("P01")) {
				if(journal.execute("D01", "DN_WARD01")) {
					System.out.println("Execute access granted");
					// here we can now safely delete the journal
				}else {
					System.out.println("Execute access denied");
					// not granted access, can't delete journal
				}
			}
		}
		
		/*client with UID "P01" and GUI "-" wants
		 read access to journal for UID "P01" */
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getPatientID().equals("P01")) {
				ArrayList<String> temp = journal.read("P01", "-");
				if(!temp.get(0).equals("DENIED")) {
					System.out.println("Read access granted:");
					for(int j=0; j<temp.size(); j++) {
						System.out.println(temp.get(j));
					}
					
				}else {
					System.out.println("read access denied");
					// not granted access
				}
			}
		}
	}

}
