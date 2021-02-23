package database;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class API {
    private Database db;
	
	public API(Database db) {
        this.db = db;
	}
	
	public String executeCmd(List<String> cmd, Person person) {
        switch (cmd.get(0)) {
            case "read": return db.readJournal(person, cmd.get(1));
            case "write": return db.writeJournal(person, cmd.get(1), cmd.get(2));
            case "remove": return db.executeJournal(person, cmd.get(1));
            default: return "Command not recognised.";
        }
    }

}