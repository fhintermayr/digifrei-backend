package de.icp.match.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "trainer")
public class Trainer extends User {
    public Trainer(String firstName, String lastName, String email, String password, Department department) {
        super(firstName, lastName, email, password, department);
    }
}