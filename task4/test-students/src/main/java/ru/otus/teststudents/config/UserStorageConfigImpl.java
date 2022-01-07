package ru.otus.teststudents.config;

import org.springframework.stereotype.Component;

@Component
public class UserStorageConfigImpl implements UserStorageConfig {

    private String userName;

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getUserName() {
        return userName;
    }
}
