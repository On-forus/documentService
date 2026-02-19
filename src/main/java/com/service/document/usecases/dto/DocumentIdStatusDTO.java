package com.service.document.usecases.dto;

import com.service.document.usecases.dto.enums.ResultStatus;
import lombok.Builder;

@Builder
public record DocumentIdStatusDTO (
        Long id,
        ResultStatus resultStatus
){

}
