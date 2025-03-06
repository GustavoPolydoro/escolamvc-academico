package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;
import lombok.Data;


@DiscriminatorValue(value="P")
@Data
@Entity
public class Professor extends Pessoa{

}
