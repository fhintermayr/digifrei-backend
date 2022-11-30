package de.icp.match.model;

import com.sun.istack.NotNull;
import de.icp.match.enums.AccessRole;
import de.icp.match.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    @Column(unique = true)
    String username;
    @NotNull
    String password;
    LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Lob
    Byte[] profilePicture;
    @NotNull
    String profession;
    @NotNull
    String department;
    String roomNumber;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_preferences_id", referencedColumnName = "id")
    UserPreferences preferences;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH} ,mappedBy = "participants")
    Set<Event> participatingEvents;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH} ,mappedBy = "participants")
    Set<Conversation> participatingConversations;
    @NotNull
    @Enumerated(EnumType.STRING)
    AccessRole accessRole;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}