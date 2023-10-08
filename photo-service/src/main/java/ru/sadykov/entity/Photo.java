package ru.sadykov.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collation = "photo")
public class Photo {
    @Id
    private String id;
    private String fileName;
    private byte[] originalPhoto;
    private Map<String, List<PhotoSize>> resizePhoto;
    private LocalDateTime dateTime;
}
