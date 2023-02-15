package de.icp.match.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChatMessage {
    @ManyToOne(optional = false)
    @JoinColumn(name = "conversation_id", referencedColumnName = "id")
    Conversation conversation;
    @NotNull
    String messageContent;
    @NotNull
    LocalDateTime sentAt;
    @NotNull
    boolean isMessageSeen;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
}
