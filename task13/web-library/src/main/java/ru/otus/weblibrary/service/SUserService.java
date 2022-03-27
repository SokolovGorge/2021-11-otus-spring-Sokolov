package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.weblibrary.domain.SUser;
import ru.otus.weblibrary.repository.SUserRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SUserService implements UserDetailsService {

    private final SUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SUser user = repository.findSUserByLogin(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRoles().stream().map(r -> r.getName()).collect(Collectors.toList()).toArray(new String[0]))
                .build();
    }
}
