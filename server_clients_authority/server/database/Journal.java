package database;

import java.util.ArrayList;

public class Journal {
	private ACL acl;
	private ArrayList<String> contents;
	private String patientID;
	
	public Journal(ACL acl, String patientID, ArrayList<String> contents) {
		this.acl = acl;
		this.patientID = patientID;
		this.contents = contents;
	}
	
	public String getPatientID() {
		return patientID;
	}
	
	public ArrayList<String> read(String UID, String GID){
		if(acl.access(UID, GID, "R")) {
			return contents;
		}else {
			ArrayList<String> error = new ArrayList<String>();
			error.add("Access denied"); // How will server handle this? Could throw exception instead
			return error;
		}
	}
	
	// Called when client attempts to write to Journal
	public boolean write(String UID, String GID, String content){
		if(acl.access(UID, GID, "W")) {
			contents.add(content);
			return true; // content added
		}else {
			return false;
			// How will server handle this? Could throw exception instead or return false
		}
		
	}
	
	public boolean execute(String UID, String GID){
		if(acl.access(UID, GID, "E")) {
			return true; // This object can be deleted by client
		}else {
			return false; 
			// How will server handle this? Could throw exception instead or return false
		}
		
	}

}
