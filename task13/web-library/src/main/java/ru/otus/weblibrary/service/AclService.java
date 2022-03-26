package ru.otus.weblibrary.service;

import ru.otus.weblibrary.consts.GrantStrategy;
import ru.otus.weblibrary.consts.ObjectIdentityType;

public interface AclService {

    void addGrantsOnObjectIdentity(ObjectIdentityType objectIdentityType, Long objectIdentityId, GrantStrategy grantStrategy);

    void removeObjectIdentity(ObjectIdentityType objectIdentityType, Long objectIdentityId);
}
