package ru.sadykov.dto;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {

}
