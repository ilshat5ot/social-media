package ru.sadykov.dto;

import ru.sadykov.validators.Violation;
import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {

}