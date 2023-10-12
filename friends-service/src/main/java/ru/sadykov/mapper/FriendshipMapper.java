package ru.sadykov.mapper;

import org.mapstruct.Mapper;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    FriendshipDto toFriendshipDto(Friendship friendship);
}
