package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*O Repository fornece uma interface entre sua aplicação e o banco de dados,
facilitando operações de persistência de dados.
 */


public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    //Aqui dentro vem métodos personalizados

    List<Professor> findByNomeContainingIgnoreCase(String nome);

    Professor findByEmail(String email);

    Professor findByCpf(String cpf);


    // Pesquisa o email e o id seja diferente
    // do id que está sendo alterado
    Professor findByEmailAndIdNot(String email, Long id);

    // Pesquisa o cpf e o id seja diferente
    // do id que está sendo alterado
    Professor findByCpfAndIdNot(String cpf, Long id);

    List<Professor> findProfessorsByNomeContaining(String nome);

    Professor findProfessorByCpf(String cpf);

    List<Professor> findProfessorsByNomeContainingOrCpfContaining(String nome, String cpf);
}
