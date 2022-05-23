package ru.otus.vacancykeeper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.vacancykeeper.domain.User;
import ru.otus.vacancykeeper.repository.UserRepository;

/* Временная реализация до подключения аутентификации
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final long USER_ID = 1;

    private final UserRepository userRepository;

    @Override
    public User getCurrentUser() {
        return userRepository.findById(USER_ID).orElseThrow();
    }
}
