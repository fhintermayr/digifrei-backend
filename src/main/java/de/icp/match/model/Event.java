package de.icp.match.model;

import com.sun.istack.NotNull;
import de.icp.match.enums.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, optional = false)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    User creator;
    @NotNull
    String title;
    String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    EventCategory category;
    @NotNull
    LocalDateTime startTime;
    String location;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "event_participant",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    Set<User> participants;
    int participantLimit;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
