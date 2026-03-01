package com.service.document.persistense.entity;

import com.service.document.usecases.dto.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "documents")
@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Document implements Serializable {

    private final static long serialVersionUID = 13444445124L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "documents_id_seq_gen")
    @SequenceGenerator(
            name = "documents_id_seq_gen",
            allocationSize = 100,
            sequenceName = "documents_id_seq",
            schema = "public")

    @Column(name = "id")
    private Long id;
    @Version
    Long version;
    @NotNull
    @Column(name = "uuid", updatable = false, nullable = false)
    @JdbcType(VarcharJdbcType.class)
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
    @Column(name = "createDate", nullable = false)
    private LocalDate createDate;
    @NotNull
    @Column(name = "updateDate", nullable = false)
    private LocalDate updateDate;

    @OneToMany(mappedBy = "document", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<History> history;

}
