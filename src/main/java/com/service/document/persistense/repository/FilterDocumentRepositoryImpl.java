package com.service.document.persistense.repository;


import com.service.document.persistense.entity.Document;
import com.service.document.usecases.dto.DocumentFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterDocumentRepositoryImpl implements FilterDocumentRepository {

    private final EntityManager entityManager;
    @Override
    public List<Document> findAllByFilter(DocumentFilter documentFilter) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Document> criteria = criteriaBuilder.createQuery(Document.class);
        Root<Document> document = criteria.from(Document.class);
//      criteria.select(document).where(document.get("id").in(listId)); select from list of id(additional)


        List<Predicate> predicates = new ArrayList<>();

        if(documentFilter.author()!=null && !documentFilter.author().isBlank()){
            predicates.add(criteriaBuilder.like(document.get("author"), documentFilter.author()));
        }
        if(documentFilter.status()!=null && !documentFilter.status().name().isBlank()){
            predicates.add(criteriaBuilder.equal(document.get("status"), documentFilter.status()));
        }
        if(documentFilter.createDate()!=null && !documentFilter.createDate().toString().isBlank()){
            predicates.add(criteriaBuilder.lessThan(document.get("createDate"), documentFilter.createDate()));
        }

        criteria.where(predicates.toArray(Predicate[]::new));
        return entityManager.createQuery(criteria).getResultList();
    }
}
