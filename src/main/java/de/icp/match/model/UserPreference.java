package de.icp.match.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserPreference {

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
