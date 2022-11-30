package de.icp.match.model;

import com.sun.istack.NotNull;
import de.icp.match.enums.MatchCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
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