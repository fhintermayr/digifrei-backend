package de.icp.match.model;

import com.sun.istack.NotNull;
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
    String profession;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_name", referencedColumnName = "name")
    Department department;
    String roomNumber;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_preference_id", referencedColumnName = "id")
    UserPreference preference;
    @ManyToMany(mappedBy = "participants")
    Set<Event> participatingEvents;
    @ManyToMany(mappedBy = "participants")
    Set<Conversation> participatingConversations;
    @NotNull
    @Enumerated(EnumType.STRING)
    AccessRole accessRole;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}