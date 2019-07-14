package com.dvilinsk.testermatch.DTO;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TesterDTO {

    @Id
    private String firstName;
    private String lastName;
    private int numBugs;

    //For JPA
    public TesterDTO() {}

    public TesterDTO(String firstName, String lastName, int numBugs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numBugs = numBugs;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getNumBugs() {
        return numBugs;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + numBugs;
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TesterDTO)) {
            return false;
        } else {
            TesterDTO other = (TesterDTO) obj;
            return other.getLastName().equals(this.lastName) && other.getFirstName().equals(this.firstName)
                    && other.getNumBugs() == this.getNumBugs();
        }
    }
}
