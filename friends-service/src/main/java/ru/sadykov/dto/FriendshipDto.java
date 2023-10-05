package ru.sadykov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.sadykov.entity.enums.RelationshipStatus;

@Builder
@Schema(description = "Запись о дружбе")
public record FriendshipDto(
        @Schema(description = "Идентификатор записи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
        @Schema(description = "Идентификатор пользователя, который отправляет заявку, либо одобряет заявку.",
                example = "1", accessMode = Schema.AccessMode.READ_ONLY)
        Long sourceUserId,
        @Schema(description = "Идентификатор пользователя, которому необходимо отправить заявку, " +
                "либо заявку от которого необходимо одобрить.", example = "2", accessMode = Schema.AccessMode.READ_ONLY)
        Long targetUserId,
        @Schema(description = "Статус отношения между пользователями", example = "FRIEND",
                accessMode = Schema.AccessMode.READ_ONLY)
        RelationshipStatus relationshipStatus) {
}
