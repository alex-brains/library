package by.alex.spring.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 250, message = "Full name should be between 5 and 250 characters")
    private String fullName;

    @Min(value = 1900, message = "Should be greater than 1900")
    private int yearsOfBirthday;

    public Person(String fullName, int yearsOfBirthday) {
        this.fullName = fullName;
        this.yearsOfBirthday = yearsOfBirthday;
    }

    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearsOfBirthday() {
        return yearsOfBirthday;
    }

    public void setYearsOfBirthday(int yearsOfBirthday) {
        this.yearsOfBirthday = yearsOfBirthday;
    }
}
