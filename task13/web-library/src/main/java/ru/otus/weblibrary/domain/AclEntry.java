package ru.otus.weblibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "acl_entry")
public class AclEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acl_object_identity", nullable = false)
    private AclObjectIdentity aclObjectIdentity;

    @Column(name = "ace_order", nullable = false)
    private Long aceOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sid", nullable = false)
    private AclSid aclSid;

    @Column(name = "mask", nullable = false)
    private Long mask;

    @Column(name = "granting", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean granting;

    @Column(name = "audit_success", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean auditSuccess;

    @Column(name = "audit_failure", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean auditFailure;

}
