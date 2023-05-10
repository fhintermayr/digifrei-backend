package de.icp.match.user.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Apprentice extends User {

    private String doc;


    public Apprentice(String firstName, String lastName, String email, String password, String doc) {
        super(firstName, lastName, email, password);
        this.doc = doc;
    }

    public Apprentice() {

    }
}
