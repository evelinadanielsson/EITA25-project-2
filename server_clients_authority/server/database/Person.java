package database;

public class Person {

    private final String name;
    private final String role;
    private final String division;
    private final String serial_number;
    private final String CA;
    private final String UID;
    private final String GID;

    public Person  (String name, String CA, String serial_number, String UID, String GID, String role, String division) {
        this.name = name;
        this.CA = CA;
        this.serial_number = serial_number;
        this.UID = UID;
        this.GID = GID;
        this.division = division;
        this.role = role;

    }

    public String getName() {
        return name;
    }
    public String getUID() {
        return UID;
    }
    public String getGID() {
        return GID;
    }

   public String getRole() {
       return role;
   }

   public String getDivision() {
       return division;
   }

    public boolean equalsPerson(String CA, String serial_number) {
    	if(CA.equals(this.CA) && serial_number.equals(this.serial_number)) {
    		return true;
    	}
    	return false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", UID='" + UID + '\'' +
                ", GUI=" + GID +
                '}';
    }
}
