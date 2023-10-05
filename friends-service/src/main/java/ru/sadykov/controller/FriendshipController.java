package ru.sadykov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sadykov.dto.ExceptionMessageDto;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.service.FriendshipService;
import ru.sadykov.validation.UserExist;

@Tag(
        name = "FriendshipController",
        description = "Контроллер отвечает за: добавление пользователя в друзья, удаление пользователя из друзей"
)
@RestController
@RequestMapping("api/v1/friend")

@RequiredArgsConstructor
@Validated
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Operation(
            summary = "Добавить пользователя в друзья",
            description = "Метод отвечает за: отправку заявки на добавление в друзья, добавление в друзья",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = FriendshipDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Конфликт",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Не корректный запрос",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }
                    )
            }
    )
    @PostMapping("/{currentUserId}/{targetId}")
    public ResponseEntity<?> addAsFriend(
            @PathVariable @Parameter(description = "Текущий пользователь") Long currentUserId,
            @UserExist @PathVariable @Parameter(description = "Идентификатор пользователя, которому необходимо " +
                    "отправить заявку, либо заявку от которого необходимо одобрить") Long targetId) {
        FriendshipDto friendship = friendshipService.addAsFriend(currentUserId, targetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(friendship);
    }

    @Operation(
            summary = "Удалить пользователя из друзей",
            description = "Метод отвечает за: удаление пользователя из друзей, отклонение заявки в друзья",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешно",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = FriendshipDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Конфликт",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Не корректный запрос",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Пользователь не найден",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExceptionMessageDto.class)
                                    )
                            }
                    )
            }
    )
    @DeleteMapping("/{currentUserId}/{targetId}")
    public ResponseEntity<?> deleteFromFriends(
            @PathVariable @Parameter(description = "Текущий пользователь") Long currentUserId,
            @UserExist @PathVariable @Parameter(description = "Идентификатор пользователя, которого необходимо " +
                    "удалить из друзей, либо оставить в подписчиках") Long targetId) {
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(currentUserId, targetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(friendshipDto);
    }
}
