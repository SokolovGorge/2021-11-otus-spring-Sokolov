package ru.otus.weblibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.weblibrary.consts.GrantStrategy;
import ru.otus.weblibrary.consts.ObjectIdentityType;
import ru.otus.weblibrary.domain.AclEntry;
import ru.otus.weblibrary.domain.AclObjectIdentity;
import ru.otus.weblibrary.domain.AclSid;
import ru.otus.weblibrary.exceptions.ApplicationException;
import ru.otus.weblibrary.repository.AclEntryRepository;
import ru.otus.weblibrary.repository.AclObjectIdentityRepository;
import ru.otus.weblibrary.repository.AclSidRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AclServiceImpl implements AclService {

    private final AclSidRepository aclSidRepository;
    private final AclObjectIdentityRepository objectIdentityRepository;
    private final AclEntryRepository aclEntryRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void addGrantsOnObjectIdentity(ObjectIdentityType objectIdentityType, Long objectIdentityId, GrantStrategy grantStrategy) {
        AclObjectIdentity objectIdentity = new AclObjectIdentity();
        objectIdentity.setObjectIdClass(objectIdentityType.getId());
        objectIdentity.setObjectIdIdentity(objectIdentityId);
        objectIdentity.setOwnerSid(getCurrentSids().get(0).getId());
        objectIdentity.setEntriesInheriting(false);
        objectIdentity = objectIdentityRepository.save(objectIdentity);
        long order = 1;
        for (AclSid sid : getSidsByStrategy(grantStrategy)) {
            AclEntry entry = new AclEntry();
            entry.setAclObjectIdentity(objectIdentity);
            entry.setAceOrder(order++);
            entry.setAclSid(sid);
            entry.setMask(1L);
            entry.setGranting(true);
            entry.setAuditFailure(true);
            entry.setAuditSuccess(true);
            aclEntryRepository.save(entry);
        }

    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void removeObjectIdentity(ObjectIdentityType objectIdentityType, Long objectIdentityId) {
        objectIdentityRepository.removeByObjectIdClassAndObjectIdIdentity(objectIdentityType.getId(), objectIdentityId);
    }

    private List<AclSid> getSidsByStrategy(GrantStrategy grantStrategy) {
        switch (grantStrategy) {
            case ALL_SID:
                return aclSidRepository.findAll();
            case CURRENT_SID:
                return getCurrentSids();
            default:
                throw new ApplicationException("Unknown strategy " + grantStrategy);
        }
    }
    private List<AclSid> getCurrentSids() {
        List<AclSid> result = new ArrayList<>();
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().forEach(authority -> result.addAll(aclSidRepository.getAclSidBySid(authority.getAuthority())));
        if (result.isEmpty()) {
            throw new ApplicationException("Current user hasn't any sids");
        }
        return result;
    }

}
