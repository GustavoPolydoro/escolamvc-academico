package br.senai.sp.escolamvc.api;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchTransactionManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aluno")


public class AlunoRestController {
    @Autowired
    private AlunoRepository alunoRepository;

    //Lista os alunos do banco de dados em um arquivo json
    @GetMapping("/listar")
    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    //Insere alguem pelos campos, sem o id pq não precisa

    @PostMapping("/inserir")
    public Aluno inserir(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    //altera os dados de um aluno a partir do id dele, no json tem q colocar os campos e os valores

    @PutMapping("/alterar")
    public Aluno alterar(@RequestBody Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    //Deleta um aluno pelo id, tem q colocar o id dele no json

    @DeleteMapping("/delete")
    public void delete(@RequestBody Aluno aluno) {
        alunoRepository.delete(aluno);
    }

    //Inserir vários, com um array json
    @PostMapping("/inserir-varios")
    public void inserirVarios(@RequestBody List<Aluno>  aluno) {
        alunoRepository.saveAll(aluno);
    }

    //Buscar por id
    @GetMapping("/buscar/{id}")
    public Aluno buscarPorId(@PathVariable Long id) {
        return alunoRepository.findById(id).get();
    }

    //buscar por nome
    @GetMapping("/buscar-por-nome/{nome}")
    public List<Aluno> buscarPorNome(@PathVariable String nome) {
        return alunoRepository.findAlunosByNomeContaining(nome);
    }

    //buscar por cpf
    @GetMapping("/buscar-por-cpf/{cpf}")
    public Aluno buscarPorCpf(@PathVariable String cpf) {
        return alunoRepository.findAlunosBycpf(cpf);
    }

    //Buscar por nome ou cpf
    @GetMapping("/buscar-por-nome-ou-cpf/{nome}/{cpf}")
    public List<Aluno> buscarPorNomeOuCpf(@PathVariable String nome, @PathVariable String cpf) {
        return alunoRepository.findAlunosByNomeContainsOrCpfContaining(nome, cpf);
    }


}
