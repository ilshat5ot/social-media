package ru.sadykov.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "photo")

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int weight;
    private int height;
    private String binaryContentId;

    @ManyToOne
    @JoinColumn(name = "meta_data_id", referencedColumnName = "id", nullable = false)
    private PhotoMetaData photoMetaData;
}
