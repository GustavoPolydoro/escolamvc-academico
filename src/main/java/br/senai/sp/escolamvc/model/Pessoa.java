package br.senai.sp.escolamvc.model;




/*pq esse é o maior de todos???????
pq esse model pessoa vale para todos os outros models
pq esses campos estão sempre se repetindo nas classes
ent fizemos um big model pros outros models nao ficarem repetitivos :)
*/





import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "tipo",
        length = 1,
        discriminatorType = DiscriminatorType.STRING
)
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique=true)
    private Long id;


    //aqui tem algumas mensagens de erro para os campos vazios
    @NotEmpty(message = "O CPF deve ser informado")
    @CPF(message = "O CPF informado é inválido")
    private String cpf;

    @NotEmpty(message = "O nome deve ser informado")
    private String nome;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @NotEmpty(message = "O e-mail deve ser informado")
    @Email(message = "O e-mail informado é inválido")
    private String email;


    @Basic
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;
    private String senha;

    @ManyToMany
    private List<Turma> turmas = new ArrayList<Turma>();


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pessoa_id")

    private List<Telefone> telefones = new ArrayList<Telefone>();

    public void addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
    }


    public void addTurma(Turma turma) {
        this.turmas.add(turma);
    }

    public void removeTurma(Turma turma) {
        this.turmas.remove(turma);
    }

}
