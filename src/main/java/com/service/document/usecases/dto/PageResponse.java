package com.service.document.usecases.dto;

import lombok.Value;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metaData;

    @Value
    public static class Metadata{
        int page;
        int size;
        long totalElements;
    }

}
