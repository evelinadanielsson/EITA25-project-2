package database;

import java.util.ArrayList;

public class Database {
	
	private ArrayList<Person> persons;
	private ArrayList<Journal> journals; 
	
	public Database() {
		JournalPersonFactory factory = new JournalPersonFactory();
		persons = factory.getPersonList();
		journals = factory.createJournals();
		//persons = 
	}
	
	public Person getPerson(String CA, String serial_number) {
		for(Person p : persons) {
			if(p.equalsPerson(CA, serial_number)) {
				return p;
			}
		}
		return null;
	}
	
	// p is person trying to run commands, patientID is the ID in the journal
	public String readJournal(Person p, String patientID) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getPatientID().equals(patientID)) {
				ArrayList<String> list = journal.read(p.getUID(), p.getGID());
				if(list.get(0).equals("Access denied")) {
					return "Access to read journal " + patientID +" denied";
				}
				String ret = "";
				for (String l : list) {
					ret = ret+"\n"+l;
				}
				// Return Journals as a string
				return ret;

			}
		}
		return "No Journal found for patient ID" + patientID;
	}
	
	// p is person trying to run commands, patientID is the ID in the journal
	public String writeJournal(Person p, String patientID, String newEntry) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getPatientID().equals(patientID)) {
				boolean access = journal.write(p.getUID(), p.getGID(), newEntry);
				if (access) {
					return "Entry added to journal";
				}else {
					return "Access to write to journal " + patientID +" denied";
				}

			}
		}
		return "No Journal found for patient ID" + patientID;
	}
	
	// p is person trying to run commands, patientID is the ID in the journal
	public String executeJournal(Person p, String patientID) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getPatientID().equals(patientID)) {
				boolean access = journal.execute(p.getUID(), p.getGID());
				if (access) {
					return "Journal removed";
				}else {
					return "Access to delete journal " + patientID +" denied";
				}

			}
		}
		return "No Journal found for patient ID" + patientID;
	}
}
