package de.icp.match.security.service;

import de.icp.match.user.model.User;
import de.icp.match.user.service.UserQueryService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserQueryService userQueryService;

    public CurrentUserService(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }


    public User getCurrentlyAuthenticatedUser() {
        String currentUsersName = getAuthenticatedUsersName();

        return userQueryService.loadUserByEmail(currentUsersName);
    }


    private String getAuthenticatedUsersName() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        }
        else{
            throw new RuntimeException("There is no authenticated user");
        }


    }
}
