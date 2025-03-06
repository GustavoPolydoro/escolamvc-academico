package br.senai.sp.escolamvc.controller;


import br.senai.sp.escolamvc.model.Aluno;
import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
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
@RequestMapping("/professor")

public class ProfessorController {


    @Autowired
    ProfessorRepository professorRepository;


    @PostMapping("/buscar")
    public String buscar(@Param("nome") String nome, Model model) {
        if(nome == null){
            model.addAttribute("professores", new ArrayList<Professor>());
            return "professor/listagem";
        }
        List<Professor> professoresRecuperadosNoBanco = professorRepository.findProfessorByNomeContaining(nome);

        model.addAttribute("professores", professoresRecuperadosNoBanco);
        return "professor/listagem";
    }




    // Método para abrir o formulário html
    @GetMapping ("/novo") /*Isso aqui é o que vai no navegador*/
    /*http://localhost/professor/novo   e esse é o link*/
    public String novo(Model model) {

        model.addAttribute("professor", new Professor());

        return "/professor/inserir"; /*Isso aqui é o arquivo html que ele procura*/

    }

    /* Método para salvar os dados do formulário*/
    @PostMapping("/salvar")
    public String salvarAluno(@Valid Professor professor, BindingResult result,
                              RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template professors/inserir.html
        if (result.hasErrors()) {
            return "professor/inserir";
        }

        // Salva o professor no banco de dados
        professorRepository.save(professor);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Professor salvo com sucesso!");

        // Redireciona para a página de listagem de professors
        return "redirect:/professor/novo";
    }

    /*
     * Método que direciona para templates/professor/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de professors no banco de dados
        List<Professor> listaProfessor = professorRepository.findAll();

        // Adiciona a lista de professors no objeto model para ser carregado no template
        model.addAttribute("professores", listaProfessor);

        // Retorna o template prfessor/listagem.html
        return "professor/listagem";
    }

    /*
     * Método para excluir um professor
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o professor no banco de dados
        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o professor do banco de dados
        professorRepository.delete(professor);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Professor excluído com sucesso!");

        // Redireciona para a página de listagem de professors
        return "redirect:/professor";
    }

    /*
     * Método que direciona para templates/professors/alterar.html
     */
    @GetMapping("/alterar/{id}") /*Rota*/
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o professor no banco de dados
        Professor professor = professorRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Adiciona o professor no objeto model para ser carregado no formulário
        model.addAttribute("professor", professor);

        // Retorna o template professor/alterar.html
        return "professor/alterar"; /*Template*/
    }

    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, @Valid Professor professor,
                          BindingResult result, RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template professors/alterar.html
        if (result.hasErrors()) {
            return "professor/alterar";
        }



        // Busca o professor no banco de dados
        Professor professorAtualizado = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));


        // Seta os dados do professor
        professorAtualizado.setNome(professor.getNome());
        professorAtualizado.setCpf(professor.getCpf());
        professorAtualizado.setEmail(professor.getEmail());
        professorAtualizado.setDataNascimento(professor.getDataNascimento());

        // Salva o professor no banco de dados
        professorRepository.save(professorAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Professsor atualizado com sucesso!");

        // Redireciona para a página de listagem de professors
        return "redirect:/professor";
    }

}
