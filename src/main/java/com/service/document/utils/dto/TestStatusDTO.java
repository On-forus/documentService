package com.service.document.utils.dto;

import com.service.document.usecases.dto.enums.ResultStatus;

public record TestStatusDTO(
        Integer attempts,
        ResultStatus status
) {
}
