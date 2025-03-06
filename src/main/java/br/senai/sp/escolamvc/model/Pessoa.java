package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.constraints.NotEmpty;

import java.sql.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name ="tipo",length = 1, discriminatorType = DiscriminatorType.STRING)
@Entity
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @CPF(message = "CPF inválido!")
    @NotEmpty(message = "O campo CPF deve ser preenchido")
    private String cpf;

    @Email(message = "Email inválido!")
    private String email;

    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;


}
