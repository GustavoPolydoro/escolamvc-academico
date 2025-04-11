package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

// encapsular os dados e as regras que definem o comportamento do sistema.
// Geralmente, os Models correspondem a entidades do banco de dados e são
// utilizados para transferir dados entre a aplicação e o banco.
// viado tirao, trambiqueiro,patetico
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true)
    private Long id;
    private Integer DDD;
    private String numero;

}
