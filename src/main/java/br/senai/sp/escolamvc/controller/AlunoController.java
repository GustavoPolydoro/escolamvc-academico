package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/aluno")

public class AlunoController {


    @Autowired
    AlunoRepository alunoRepository;

    @PostMapping("/buscar")
    public String buscar(@Param("nome") String nome, Model model) {
        if(nome == null){
            model.addAttribute("alunos", new ArrayList<Aluno>());
            return "aluno/listagem";
        }
        List<Aluno> alunosRecuperadosNoBanco = alunoRepository.findAlunosByNomeContaining(nome);

        model.addAttribute("alunos", alunosRecuperadosNoBanco);
        return "aluno/listagem";
    }

    // Método para abrir o formulário html
    @GetMapping ("/novo") /*Isso aqui é o que vai no navegador*/
    /*http://localhost/aluno/novo   e esse é o link*/
    public String novo(Model model) {

        model.addAttribute("aluno", new Aluno());

        return "/aluno/inserir"; /*Isso aqui é o arquivo html que ele procura*/

    }

    /* Método para salvar os dados do formulário*/
    @PostMapping("/salvar")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result,
                              RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/inserir.html
        if (result.hasErrors()) {
            return "aluno/inserir";
        }

        // Salva o aluno no banco de dados
        alunoRepository.save(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno/novo";
    }

    /*
     * Método que direciona para templates/alunos/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de alunos no banco de dados
        List<Aluno> listaAlunos = alunoRepository.findAll();

        // Adiciona a lista de alunos no objeto model para ser carregado no template
        model.addAttribute("alunos", listaAlunos);

        // Retorna o template aluno/listagem.html
        return "aluno/listagem";
    }

    /*
     * Método para excluir um aluno
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o aluno do banco de dados
        alunoRepository.delete(aluno);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno excluído com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }

    /*
     * Método que direciona para templates/alunos/alterar.html
     */
    @GetMapping("/alterar/{id}") /*Rota*/
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("aluno", aluno);

        // Retorna o template aluno/alterar.html
        return "aluno/alterar"; /*Template*/
    }

    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, @Valid Aluno aluno,
                          BindingResult result, RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template alunos/alterar.html
        if (result.hasErrors()) {
            return "aluno/alterar";
        }



        // Busca o aluno no banco de dados
        Aluno alunoAtualizado = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));


        // Seta os dados do aluno
        alunoAtualizado.setMatricula(aluno.getMatricula());
        alunoAtualizado.setNome(aluno.getNome());
        alunoAtualizado.setCpf(aluno.getCpf());
        alunoAtualizado.setEmail(aluno.getEmail());
        alunoAtualizado.setDataNascimento(aluno.getDataNascimento());

        // Salva o aluno no banco de dados
        alunoRepository.save(alunoAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Aluno atualizado com sucesso!");

        // Redireciona para a página de listagem de alunos
        return "redirect:/aluno";
    }

}
