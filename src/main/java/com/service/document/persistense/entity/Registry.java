package com.service.document.persistense.entity;

import com.service.document.usecases.dto.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Table(name = "registry")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Registry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "registry_id_seq_gen")
    @SequenceGenerator(
            name = "registry_id_seq_gen",
            allocationSize = 50,
            sequenceName = "registry_id_seq",
            schema = "public",
            initialValue = 1)

    private Long id;
    @NotNull
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;
    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    @NotNull
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @Column(name = "createDate", updatable = false, nullable = false)
    private LocalDate createDate;
    @NotNull
    @Column(name = "updateDate", nullable = false)
    private LocalDate updateDate;
}
