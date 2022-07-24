package ru.otus.vacancykeeper.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(schema = "vacancy", name = "task")
@NamedEntityGraph(name = "task-all-attribute-entity-graph",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("professional"), @NamedAttributeNode("area")})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private SUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prof_id", nullable = false)
    private Professional professional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @Column(name = "keywords")
    private String keywords;

}
