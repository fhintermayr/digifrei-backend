package de.icp.match.user.model;

import de.icp.match.request.model.ExemptionRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Apprentice extends User {

    @ManyToOne(optional = false)
    @JoinColumn(name = "socio_edu_expert_id", nullable = false)
    private SocioEduExpert socioEduExpert;

    @OneToMany(mappedBy = "applicant", orphanRemoval = true)
    private List<ExemptionRequest> exemptionRequests;

    public Apprentice(String firstName, String lastName, String email, String password, Department department, SocioEduExpert socioEduExpert) {
        super(firstName, lastName, email, password, department);
        this.socioEduExpert = socioEduExpert;
    }

}
