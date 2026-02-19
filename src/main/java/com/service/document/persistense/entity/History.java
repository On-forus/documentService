package com.service.document.persistense.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.service.document.usecases.dto.enums.Action;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "history")
public class History implements Serializable {

    private final static long serialVersionUID = 13412345124L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;
    @NotNull
    @Column(name = "action", nullable = false)
    @Enumerated(EnumType.STRING)
    private Action action;
    @NotNull
    @Column(name = "comments", nullable = false)
    private String comments;
    @NotNull
    @Column(name = "updateDate", nullable = false)
    private LocalDate updateDate;

    @JoinColumn(name = "history_id")
    @ManyToOne(fetch =  FetchType.LAZY)
    @JsonBackReference
    @NotNull
    private Document document;

}
