package de.icp.match.user.preferences;

import de.icp.match.dto.UserPreferencesDto;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserPreferencesMapper {
    UserPreferences toEntity(UserPreferencesDto userPreferencesDto);

    UserPreferencesDto toDto(UserPreferences userPreferences);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserPreferences partialUpdate(UserPreferencesDto userPreferencesDto, @MappingTarget UserPreferences userPreferences);

}