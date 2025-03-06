package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@DiscriminatorValue(value="A")
@Data
@Entity
public class Aluno extends Pessoa {

    @NotNull(message = "O campo matrícula deve ser preenchido")
    private String matricula;


}

