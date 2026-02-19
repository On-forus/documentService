package com.service.document.usecases.mapper;

import com.service.document.persistense.entity.Document;
import com.service.document.persistense.entity.Registry;
import com.service.document.usecases.dto.DocumentCreateRequestDTO;
import com.service.document.usecases.dto.DocumentCreateResponseDTO;
import com.service.document.usecases.dto.DocumentHistoryDTO;
import com.service.document.usecases.dto.DocumentIdStatusDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentMapper {
    Document requestToEntity(DocumentCreateRequestDTO documentCreateRequestDTO);

    DocumentCreateResponseDTO entityToResponse(Document document);

    DocumentHistoryDTO entityToDocumentHistoryDto(Document document);

    List<DocumentHistoryDTO> listEntityToDocumentHistoryDto(List<Document> documents);

    List<DocumentIdStatusDTO> listDocumentsToIdStatusDto(List<Document> documents);
    @Mapping(target = "id", ignore = true)
    Registry documentToRegistry(Document document);

    List<Document> requestListToDocumnetList(List<DocumentCreateRequestDTO> documentCreateRequestDTOs);


    List<DocumentCreateResponseDTO> listDocumentToListResponse(List<Document> documents);

    default List<Long> fromDocumentToLong(List<Document> document) {
        if ( document == null ) {
            return null;
        }

        List<Long> list = new ArrayList<Long>( document.size() );
        for ( Document long1 : document ) {
            list.add( long1.getId());
        }

        return list;
    }

}
