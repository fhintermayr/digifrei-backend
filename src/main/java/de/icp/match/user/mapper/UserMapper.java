package de.icp.match.user.mapper;

import de.icp.match.dto.UserCreationDto;
import de.icp.match.dto.UserDto;
import de.icp.match.model.Conversation;
import de.icp.match.model.Event;
import de.icp.match.user.model.User;
import de.icp.match.user.preferences.UserPreferencesMapper;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {UserPreferencesMapper.class})
public interface UserMapper {


    User toUser(UserCreationDto userCreationDto);

    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    default Set<Integer> participatingEventsToParticipatingEventIds(Set<Event> participatingEvents) {
        return participatingEvents.stream().map(Event::getId).collect(Collectors.toSet());
    }

    default Set<Integer> participatingConversationsToParticipatingConversationIds(Set<Conversation> participatingConversations) {
        return participatingConversations.stream().map(Conversation::getId).collect(Collectors.toSet());
    }

    List<UserDto> toDto(List<User> user);

    List<User> toEntity(List<UserDto> userDto);
}