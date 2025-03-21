package br.senai.sp.escolamvc.api;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")
public class AlunoRestController {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/listar")
    public List<Aluno> listar(){
        return alunoRepository.findAll();
    }

    @PostMapping("/inserir")
    public void inserir(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
    }

    @PutMapping("/alterar")
    public void alterar(@RequestBody Aluno aluno){
        alunoRepository.save(aluno);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluir(@PathVariable Long id){
        alunoRepository.deleteById(id);
    }

    //Inserir Vários
    @PostMapping("/inserir-varios")
    public void inserirVarios(@RequestBody List<Aluno> alunos){
        alunoRepository.saveAll(alunos);
    }

    // Buscar por Id
    @GetMapping("/buscar/{id}")
    public Aluno buscarPorId(@PathVariable Long id){
        return alunoRepository.findById(id).get();
    }


    //Buscar por Nome
    @GetMapping("/buscar-por-nome/{nome}")
    public List<Aluno> buscarPorNome(@PathVariable String nome){
        return alunoRepository.findAlunosByNomeContaining(nome);
    }

    //Buscar por CPF
    @GetMapping("/buscar-por-cpf/{cpf}")
    public Aluno buscarPorCpf(@PathVariable String cpf){
        return alunoRepository.findAlunoByCpf(cpf);
    }

    //Buscar por Nome ou CPF
    @GetMapping("/buscar-por-nome-ou-cpf/{nome}/{cpf}")
    public List<Aluno> buscarPorNomeOuCpf(
            @PathVariable String nome,
            @PathVariable String cpf
    ){
        return alunoRepository
                .findAlunosByNomeContainingOrCpfContaining(nome, cpf);
    }




}
