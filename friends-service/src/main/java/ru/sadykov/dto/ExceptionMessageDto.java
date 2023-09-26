package ru.sadykov.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Сообщение об ошибке")
public record ExceptionMessageDto(
        @Schema(
                description = "Текст сообщения",
                example = "Пользователь уже добавлен в друзья!",
                accessMode = Schema.AccessMode.READ_ONLY
        ) String message) {
}
