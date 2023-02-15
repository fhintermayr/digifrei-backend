package de.icp.match.model;

import de.icp.match.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conversation {

    @NotNull
    String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "conversation_participant",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    Set<User> participants;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
