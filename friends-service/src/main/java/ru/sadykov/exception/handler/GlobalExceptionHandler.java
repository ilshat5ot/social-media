package ru.sadykov.exception.handler;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sadykov.dto.ExceptionMessageDto;
import ru.sadykov.dto.ValidationErrorResponse;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.exception.exceptions.InvalidRequestException;
import ru.sadykov.exception.exceptions.UnfriendingException;
import ru.sadykov.validators.Violation;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AddingAsAFriendException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleAddingAsAFriendException(AddingAsAFriendException exception) {
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(UnfriendingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleUnfriendingException(UnfriendingException exception) {
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionMessageDto handleInvalidRequestException(InvalidRequestException exception) {
        return new ExceptionMessageDto(exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()
                        )
                )
                .toList();
        return new ValidationErrorResponse(violations);
    }
}
