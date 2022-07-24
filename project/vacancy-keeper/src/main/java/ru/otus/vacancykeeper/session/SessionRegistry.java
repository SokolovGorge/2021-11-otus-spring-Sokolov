package ru.otus.vacancykeeper.session;

import org.springframework.security.core.userdetails.UserDetails;

public interface SessionRegistry {

    String registerSession(final String login);

    UserDetails getUserBySession(final String sessionId);
}
