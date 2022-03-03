package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.weblibrary.domain.SUser;
import ru.otus.weblibrary.repository.SUserRepository;

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
        return User.withUsername(user.getLogin()).password(user.getPassword()).authorities("ROLE_ADMIN").build();
    }
}
