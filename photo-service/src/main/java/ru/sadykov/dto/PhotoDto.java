package ru.sadykov.dto;

import lombok.Builder;
import org.springframework.http.MediaType;

@Builder
public record PhotoDto(String fileName,
                       MediaType mediaType,
                       byte[] fileAsByteArrays) {
}
