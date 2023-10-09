package ru.sadykov.localization;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Component
public class LocalizationExceptionMessage {

    private final ExceptionMessageSource ms = new ExceptionMessageSource();
    private final Locale locale;

    private final String addYourselfExc;
    private final String addAsFriendExc;
    private final String addReapplicationExc;
    private final String addAreYouASubExc;
    private final String addLeaveInSubExc;
    private final String deleteYourselfExc;
    private final String deleteUserAreSubExc;
    private final String deleteFriendExc;
    private final String invalidRequestExc;
    private final String friendshipNotFoundExc;


    public LocalizationExceptionMessage() {
        this.locale = Locale.getDefault();

        addYourselfExc = ms.getMessage("add.yourself");
        addAsFriendExc = ms.getMessage("add.as.friend");
        addReapplicationExc = ms.getMessage("add.reapplication");
        addAreYouASubExc = ms.getMessage("add.are.you.a.sub");
        addLeaveInSubExc = ms.getMessage("add.leave.in.sub");

        deleteYourselfExc = ms.getMessage("delete.yourself");
        deleteUserAreSubExc = ms.getMessage("delete.subscriber");
        deleteFriendExc = ms.getMessage("delete.friend");

        friendshipNotFoundExc = ms.getMessage("Запись о дружбе не найдена!");

        invalidRequestExc = ms.getMessage("invalid.request");
    }
}
