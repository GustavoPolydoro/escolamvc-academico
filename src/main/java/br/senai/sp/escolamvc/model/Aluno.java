package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
// A de Aluno
//pq la no banco, vai aparecer que o valor dele é "A", ent da pra diferenciar ele dos outros
//sendo que os outros tbm tem

@DiscriminatorValue(value = "A")
public class Aluno extends Pessoa{


    private Long matricula;

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

}