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
    try{
        switch (cmd.get(0)) {
            case "read": return db.readJournal(person, cmd.get(1));
            case "write": return db.writeJournal(person, cmd.get(1), cmd.subList(2, cmd.size()));
            case "remove": return db.executeJournal(person, cmd.get(1));
            case "add": return db.addJournal(person, cmd.get(1), cmd.get(2), cmd.get(3), cmd.subList(4, cmd.size())); // person = doctor, 1 = journal id, 2= patient id, 3 = nurse id, rest = text
            default: return "Command not recognised.";
        }
    }catch (Exception e){
      return "Command not recognised.";
    }
  }

}
