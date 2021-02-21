package database;

import java.util.HashMap;
import java.util.ArrayList;

public class API {
	
	public API() {
	}
	
	public String executeCmd(List<String> cmd, Stirng UID, String GID) {
        switch (cmd[0]) {
            case "read": return read(cmd.remove(0), UID, GID);
                break;
            case "write": return write(cmd.remove(0), UID, GID);
                break;
            case "remove": return remove(cmd.remove(0), UID, GID);
                break;
            default: return "Command not recognised."
                break;
        }
    
    public String read(List<String> cmd, Stirng UID, Stirng GID) {
        switch (cmd[0]) {
            case "read": 
                break;
            case "write": 
            default:
                break;
        }
    }

    public String remove(List<String> cmd, Stirng UID, Stirng GID) {
        switch (cmd[0]) {
            case "read": 
                break;
            case "write": 
            default:
                break;
        }
    }

}