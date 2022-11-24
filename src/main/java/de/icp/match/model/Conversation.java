package de.icp.match.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
