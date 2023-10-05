package ru.sadykov.service;

import ru.sadykov.dto.FriendshipDto;

public interface FriendshipService {

    /**
     * Метод позволяет отправить заявку в друзья или одобрить данную заявку.
     * @param currentUserId - Идентификатор пользователя, который отправляет заявку, либо одобряет заявку.
     * @param userId        - Идентификатор пользователя, которому необходимо отправить заявку, либо заявку от
     *                      которого необходимо одобрить.
     * @return              - Возвращает объект FriendshipDto, представляющий запись о добавленной дружбе.
     */
    FriendshipDto addAsFriend(Long currentUserId, Long userId);
}


