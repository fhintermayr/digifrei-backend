package de.icp.match.model;

import com.sun.istack.NotNull;
import de.icp.match.enums.MatchState;
import de.icp.match.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MatchProposal {

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposed_for_user_id", referencedColumnName = "id")
    User proposedForUser;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "proposed_user_id", referencedColumnName = "id")
    User proposedUser;
    @NotNull
    LocalDateTime proposedAt;
    @NotNull
    @Enumerated(EnumType.STRING)
    MatchState matchState;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}