package ru.otus.vacancykeeper.session;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.otus.vacancykeeper.service.UserServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InMemorySessionRegistry  implements SessionRegistry {

    private static final Map<String, UserDetails> SESSIONS = new HashMap<>();

    private final UserServiceImpl userServiceImpl;

    @Override
    public String registerSession(String login) {
        if (null == login || login.isEmpty()) {
            throw new RuntimeException("Login need to be provided");
        }
        final String sessionId = generateSessionId();
        final UserDetails userDetails = userServiceImpl.loadUserByUsername(login);
        SESSIONS.put(sessionId, userDetails);
        return sessionId;
    }

    @Override
    public UserDetails getUserBySession(String sessionId) {
        return SESSIONS.get(sessionId);
    }

    private String generateSessionId() {
        return new String(Base64.getEncoder()
                .encode(UUID.randomUUID().toString()
                        .getBytes(StandardCharsets.UTF_8)
                )
        );
    }
}
