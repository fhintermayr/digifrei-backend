package de.icp.match.security.service;

import de.icp.match.user.model.User;
import de.icp.match.user.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String usernameNotFoundErrorMessage = String.format("User %s does not exist", username);

        User loadedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(usernameNotFoundErrorMessage));

        String usersAccessRoleAsString = loadedUser.getAccessRole().name();
        Set<SimpleGrantedAuthority> usersAuthorities = Set.of(new SimpleGrantedAuthority(usersAccessRoleAsString));

        return new org.springframework.security.core.userdetails.User(
                loadedUser.getUsername(),
                loadedUser.getPassword(),
                usersAuthorities
        );
    }
}
