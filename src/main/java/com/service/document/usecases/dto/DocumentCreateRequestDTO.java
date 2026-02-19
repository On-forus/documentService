package com.service.document.usecases.dto;

public record DocumentCreateRequestDTO(
        String author,
        String title
) {
}
