package database;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class Database {

	private ArrayList<Person> persons;
	private ArrayList<Journal> journals;
	private HashMap<String, ArrayList<Person>> doctorPatientRelations;
	private JournalPersonFactory factory;

	public Database() {
		factory = new JournalPersonFactory();
		persons = factory.getPersonList();
		journals = factory.createJournals();
		doctorPatientRelations = factory.createRelations();
	}

	public Person getPerson(String CA, String serial_number) {
		for(Person p : persons) {
			if(p.equalsPerson(CA, serial_number)) {
				return p;
			}
		}
		return null;
	}

	private Person getPersonFromID(String ID) {
		for(Person p : persons) {
			if(p.getUID().equals(ID)) {
				return p;
			}
		}
		return null;
	}

	// p is person trying to run commands, journalID is the ID in the journal
	public String readJournal(Person p, String journalID) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getJournalID().equals(journalID)) {
				ArrayList<String> list = journal.read(p.getUID(), p.getGID());
				if(list.get(0).equals("Access denied")) {
					return "Access to read journal " + journalID +" denied ";
				}
				String ret = "";
				for (String l : list) {
					ret = ret + l + "\n";
				}
				// Return Journals as a string
				return ret;

			}
		}
		return "No Journal found for Journal ID " + journalID;
	}

	// p is person trying to run commands, journalID is the ID in the journal
	public String writeJournal(Person p, String journalID, List<String> newEntry) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getJournalID().equals(journalID)) {
				String res = "";
				for ( String s : newEntry){
					res = res + " " + s;
				}
				boolean access = journal.write(p.getUID(), p.getGID(), res);
				if (access) {
					return "Entry added to journal ";
				}else {
					return "Access to write to journal " + journalID +" denied ";
				}

			}
		}
		return "No Journal found for Journal ID  " + journalID;
	}

	public String addJournal(Person p, String journalID, String patientID, String nurseID, List<String> newEntry) {
		// Check journal doesn't exist already:
		for(Journal j : journals){
			if(j.getJournalID().equals(journalID)){
				return "Unable to add journal, journal with id '" + journalID + "' already exists";
			}
		}

		// Check that a doctor is calling cmd
		String requestorRole = p.getRole();
		if(requestorRole.equals("doctor")){
			String doctorID = p.getUID();
			ArrayList<Person> patientsOfDoctor = doctorPatientRelations.get(doctorID);
			if(patientsOfDoctor != null){
				for (Person pat : patientsOfDoctor){
					if(pat.getUID().equals(patientID)){
						// Check nure is in same division
						Person nurse = getPersonFromID(nurseID);
						if(nurse == null){
							return  "Unable to add journal, nurse not found";
						}else if (!nurse.getDivision().equals(p.getDivision())){
							return "Unable to add journal, nurse from a different division";
						}else{
							String res = "";
							for ( String s : newEntry){
								res = res + " " + s;
							}
							// now we know: Caller is doctor; caller is doctor of patient p -> we can add Journal
							Journal journal  = factory.createNewJournal(p, journalID, patientID, nurseID, res);
							journals.add(journal);
							return "Journal with ID " + journalID + " added";
						}
					}
				}
				return "Unable to add journal, not your patient";
			}

		}
	return "Unable to add journal, only doctors can add new journal";
	}

	// p is person trying to run commands, journalID is the ID in the journal
	public String executeJournal(Person p, String journalID) {
		for (int i = 0; i<journals.size(); i++) {
			Journal journal = journals.get(i);
			if(journal.getJournalID().equals(journalID)) {
				boolean access = journal.execute(p.getUID(), p.getGID());
				if (access) {
					journals.remove(journal);
					return "Journal removed  ";
				}else {
					return "Access to delete journal " + journalID +" denied ";
				}

			}
		}
		return "No Journal found for Journal ID" + journalID + " ";
	}
}
