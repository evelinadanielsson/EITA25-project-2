package database;

import java.util.HashMap;
import java.util.ArrayList;

public class ACL {

	// Note: Not implemented here yet: Deny entries in ACL, Multiple group associations

	private HashMap<String, ArrayList<String>> UIDs;
	/*Structure inner ArrayList:
	[R, W, E], Allows all access, read, write, execute/delete
	[R, -, -], Allows read access
	*/
	private HashMap<String, ArrayList<String>> GIDs;

	public ACL() {
		UIDs = new HashMap<String, ArrayList<String>>();
		GIDs = new HashMap<String, ArrayList<String>>();
	}


	public void addUID(String UID, ArrayList<String> perm) {
		//If an existing key is passed then the previous value gets returned. If a new pair is passed, then NULL is returned.
		UIDs.put(UID, perm);
	}

	public void addGID(String GID, ArrayList<String> perm) {
		//If an existing key is passed then the previous value gets returned. If a new pair is passed, then NULL is returned.

		GIDs.put(GID, perm);
	}

	public boolean access(String UID, String GID, String type) {
		int index = 0;
		if(type.equals("R")) {
			index = 0;
		}else if(type.equals("W")) {
			index = 1;
		}else if(type.equals("E")) {
			index = 2;
		}else {
			return false; // if bad call
		}

		// UID has type access?
		ArrayList<String> perm = UIDs.get(UID);
		if(perm != null) {
			if(perm.get(index).equals(type)) {
				 return true;
			 }
		}

		// GID has type access?
		perm = GIDs.get(GID);
		if(perm != null) {
			if(perm.get(index).equals(type)) {
				return true;
			}
		}

		return false;
	}

}
