package br.senai.sp.escolamvc.api;

import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/professor")


public class ProfessorRestController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/listar")
    public List<Professor> listar() {
        return professorRepository.findAll();
    }

    @PostMapping("/inserir")
    public Professor inserir(@RequestBody Professor professor) {
        return professorRepository.save(professor);
    }

    @PutMapping("/alterar")
    public Professor alterar(@RequestBody Professor professor) {
        return professorRepository.save(professor);
    }

    @DeleteMapping("/delete")
    public void delete(@RequestBody Professor professor) {
        professorRepository.delete(professor);
    }
}
