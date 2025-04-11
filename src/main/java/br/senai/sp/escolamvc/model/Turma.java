package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// encapsular os dados e as regras que definem o comportamento do sistema.
// Geralmente, os Models correspondem a entidades do banco de dados e são
// utilizados para transferir dados entre a aplicação e o banco.

public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true)
    private Long id;
    private String nomeCurso;

    @Basic
    @Temporal(TemporalType.DATE)
    private java.sql.Date dt_inicio;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dt_fim;

}
