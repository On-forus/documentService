package com.service.document.usecases.dto;

import com.service.document.usecases.dto.enums.Status;

import java.time.LocalDate;

public record DocumentFilter(
        Status status,
        String author,
        LocalDate createDate
) {
}
