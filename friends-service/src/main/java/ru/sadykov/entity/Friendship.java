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

import java.time.LocalDateTime;

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

    @Column(nullable = false)
    private Long sourceUserId;

    @Column(nullable = false)
    private Long targetUserId;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RelationshipStatus relationshipStatus;

    @Column(nullable = false)
    private LocalDateTime timeOfCreation;

    @Column( name = "is_archive", nullable = false)
    private boolean archive;
}
