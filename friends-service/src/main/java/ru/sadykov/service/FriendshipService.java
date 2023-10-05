package ru.sadykov.service;

import ru.sadykov.dto.FriendshipDto;

public interface FriendshipService {

    /**
     * Метод отправляет заявку в друзья или одобряет созданную заявку.
     *
     * @param currentUserId - Идентификатор пользователя, который отправляет заявку, либо одобряет заявку.
     * @param userId        - Идентификатор пользователя, которому необходимо отправить заявку, либо заявку от
     *                      которого необходимо одобрить.
     * @return              - Возвращает объект FriendshipDto, представляющий запись о добавленной дружбе.
     */
    FriendshipDto addAsFriend(Long currentUserId, Long userId);


    /**
     * Метод удаляет из друзей или оставляет в подписчиках
     *
     * @param currentUserId - Идентификатор пользователя, который удаляет из друзей, либо перевод в подписчики.
     * @param targetId      - Идентификатор пользователя, которого удаляют из друзей, либо отклоняют заявку в друзья.
     * @return              - Возвращает объект FriendshipDto, представляющий запись об удаление из друзей.
     */
    FriendshipDto deleteFromFriends(Long currentUserId, Long targetId);
}


