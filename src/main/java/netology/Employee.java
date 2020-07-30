package netology;

public class Employee {
    public Long id;
    public String firstName;
    public String lastName;
    public String country;
    public Integer age;

    public Employee() {
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }
}
