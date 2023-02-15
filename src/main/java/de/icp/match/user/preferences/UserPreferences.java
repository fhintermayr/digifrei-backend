package de.icp.match.user.preferences;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPreferences {

    @NotNull
    boolean prefersMale;
    @NotNull
    boolean prefersFemale;
    @NotNull
    boolean prefersDiverse;
    @NotNull
    byte smallestPreferredAge;
    @NotNull
    byte highestPreferredAge;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
