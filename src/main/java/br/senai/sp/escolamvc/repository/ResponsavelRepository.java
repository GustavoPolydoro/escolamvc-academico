package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*O Repository fornece uma interface entre sua aplicação e o banco de dados,
facilitando operações de persistência de dados.
 */


public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {

    //aqui dentro vem métodos personalizados

    List<Responsavel> findByNomeContainingIgnoreCase(String nome);

    Responsavel findByEmail(String email);

    Responsavel findByCpf(String cpf);


    // Pesquisa o email e o id seja diferente
    // do id que está sendo alterado
    Responsavel findByEmailAndIdNot(String email, Long id);

    // Pesquisa o cpf e o id seja diferente
    // do id que está sendo alterado
    Responsavel findByCpfAndIdNot(String cpf, Long id);

    List<Responsavel> findResponsavelsByNomeContaining(String nome);

    Responsavel findResponsavelByCpf(String cpf);

    List<Responsavel> findResponsavelsByNomeContainingOrCpfContaining(String nome, String cpf);
}
