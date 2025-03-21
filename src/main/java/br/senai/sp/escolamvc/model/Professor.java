package br.senai.sp.escolamvc.model;

import jakarta.persistence.*;

// P porque é o professor, então lá no banco de dados vai aparecer um P
@Entity
@DiscriminatorValue(value = "P")
public class Professor extends Pessoa{

}
