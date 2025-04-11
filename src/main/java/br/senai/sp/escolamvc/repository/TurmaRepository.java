package br.senai.sp.escolamvc.repository;

import br.senai.sp.escolamvc.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    //método de encontrar turmas pelo  nome, ai lá no método de buscar
    //ele chama isso aqui

    List<Turma> findByNomeCursoContainingIgnoreCase(String nomeCurso);
}
