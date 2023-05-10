package de.icp.match.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Apprentice extends User {

    private String doc;


    public Apprentice(String firstName, String lastName, String email, String password, Department department, String doc) {
        super(firstName, lastName, email, password, department);
        this.doc = doc;
    }

    public Apprentice() {

    }
}
