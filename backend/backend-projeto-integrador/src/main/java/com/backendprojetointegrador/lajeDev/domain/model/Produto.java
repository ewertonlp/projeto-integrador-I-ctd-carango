package com.backendprojetointegrador.lajeDev.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Produto {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String vin;

    @ManyToMany
    @JoinTable(name = "produto_has_caracteristicas", joinColumns = @JoinColumn(name = "produto_id",
            referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "caracteristica_id",
            referencedColumnName = "id"))
    private List<Caracteristica> caracteristicas;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
}
