public class Person {

    private final String name;
    private final String role;
    private final int division;

    public Person  (String name, String role, int division) {
        this.name = name;
        this.role = role;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getDivision() {
        return division;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", division=" + division +
                '}';
    }
}
