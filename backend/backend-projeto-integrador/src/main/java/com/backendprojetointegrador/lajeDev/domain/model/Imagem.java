package com.backendprojetointegrador.lajeDev.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@RequiredArgsConstructor
public class Imagem {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    @NonNull
    private String name;


    @NonNull
    private String contentType;

    @NonNull
    private Long contentLength;

    private String url;


    public Imagem(Long id, OffsetDateTime createdAt, String name,
                  String contentType, Long contentLength, String url) {

        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.url = url;
    }

    public Imagem() {
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
