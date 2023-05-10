package de.icp.match.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Apprentice extends Employee {

    private String doc;


    public Apprentice(String firstName, String lastName, String email, String password, String doc) {
        super(firstName, lastName, email, password);
        this.doc = doc;
    }

    public Apprentice() {

    }
}
