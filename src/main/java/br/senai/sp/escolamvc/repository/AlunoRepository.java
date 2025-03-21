package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*O Repository fornece uma interface entre sua aplicação e o banco de dados,
facilitando operações de persistência de dados.
 */


public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    Aluno findByEmail(String email);

    Aluno findByCpf(String cpf);

    Optional<Aluno> findByEmailOrCpf(String email, String cpf);


    // Pesquisa o email e o id seja diferente
    // do id que está sendo alterado
    Aluno findByEmailAndIdNot(String email, Long id);

    // Pesquisa o cpf e o id seja diferente
    // do id que está sendo alterado
    Aluno findByCpfAndIdNot(String cpf, Long id);


    List<Aluno> findAlunosByNomeContaining(String nome);

    Aluno findAlunoByCpf(String cpf);

    List<Aluno> findAlunosByNomeContainingOrCpfContaining(String nome, String cpf);
}
