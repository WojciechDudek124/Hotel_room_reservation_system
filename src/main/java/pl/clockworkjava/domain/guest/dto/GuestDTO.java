package pl.clockworkjava.domain.guest.dto;

public class GuestDTO {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;

    public GuestDTO(int id, String firstName, String lastName, int age, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }
}
