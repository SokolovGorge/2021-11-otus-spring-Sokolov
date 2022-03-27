package ru.otus.weblibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "acl_object_identity")
public class AclObjectIdentity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_id_class", nullable = false)
    private Long objectIdClass;

    @Column(name = "object_id_identity", nullable = false)
    private Long objectIdIdentity;

    @Column(name = "parent_object")
    private Long parentObject;

    @Column(name = "owner_sid")
    private Long ownerSid;

    @Column(name = "entries_inheriting", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean  entriesInheriting;

}
