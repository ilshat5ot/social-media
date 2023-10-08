package ru.sadykov.dto;

import lombok.Builder;

@Builder
public record PhotoDto(String fileName,
                       int width,
                       int height,
                       byte[] fileAsByteArrays) {
}
