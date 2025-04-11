package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

/*O Repository fornece uma interface entre sua aplicação e o banco de dados,
facilitando operações de persistência de dados.
 */


public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
