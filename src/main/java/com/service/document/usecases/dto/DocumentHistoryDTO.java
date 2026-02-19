package com.service.document.usecases.dto;

import com.service.document.persistense.entity.History;
import com.service.document.usecases.dto.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record DocumentHistoryDTO(
        Long id,
        UUID uuid,
        String author,
        String title,
        Enum<Status> status,
        LocalDate createDate,
        LocalDate updateDate,
        List<History> history
) {
}
