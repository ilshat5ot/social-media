package ru.sadykov.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

@Entity
@Table(name = "photo_meta_data")
@EntityListeners(AuditingEntityListener.class)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhotoMetaData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoName;
    private MediaType mediaType;
    private boolean isArchive;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

}
