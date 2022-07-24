package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.vacancykeeper.domain.Role;
import ru.otus.vacancykeeper.domain.SUser;
import ru.otus.vacancykeeper.repository.SUserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final SUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SUser user = userRepository.findSUserByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User " + username + "not found"));
        return User.withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
                .build();
    }


    @Override
    public SUser getCurrentUser() {
        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findSUserByLogin(user.getUsername()).orElseThrow(() -> new ApplicationContextException("Unknown user " + user.getUsername()));
    }
}
