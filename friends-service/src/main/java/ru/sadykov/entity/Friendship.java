package ru.sadykov.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sadykov.entity.enums.RelationshipStatus;

@Entity
@Table(name = "friendship")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RelationshipStatus relationshipStatus;

    @Column(nullable = false)
    private Long targetUser;

    @Column(nullable = false)
    private Long sourceUser;

    @Column(nullable = false)
    private String timeOfCreation;

    @Column(nullable = false)
    private boolean isArchive;
}
