package de.icp.match.user.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Apprentice extends User {

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, optional = false)
    @JoinColumn(name = "socio_edu_expert_id", nullable = false)
    private SocioEduExpert socioEduExpert;

    public Apprentice(String firstName, String lastName, String email, String password, Department department, SocioEduExpert socioEduExpert) {
        super(firstName, lastName, email, password, department);
        this.socioEduExpert = socioEduExpert;
    }

}
