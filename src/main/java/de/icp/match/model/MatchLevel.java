package de.icp.match.model;

import de.icp.match.enums.MatchCategory;
import de.icp.match.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatchLevel {

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE},optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
    @NotNull
    @Enumerated(EnumType.STRING)
    MatchCategory matchCategory;
    @Lob
    Byte[] levelPicture;
    String description;
    @NotNull
    boolean isActivated;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}