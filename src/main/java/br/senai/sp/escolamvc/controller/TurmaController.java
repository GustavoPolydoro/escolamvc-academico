package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Turma;
import br.senai.sp.escolamvc.repository.TurmaRepository;
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

import java.util.List;

@Controller
@RequestMapping("/turma")

public class TurmaController {

    @Autowired
    private TurmaRepository turmaRepository;

    @GetMapping
    public String turma(Model model) {
        List<Turma> listaTurmas = turmaRepository.findAll();
        model.addAttribute("turmas", listaTurmas);

        return "turma/listagem";}

    @GetMapping("/novo")
    public String novaTurma(Model model) {

        model.addAttribute("turma", new Turma());

        return "turma/inserir";
    }

    /*
     * Método que direciona para templates/turma/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o turma no banco de dados
        Turma turma = turmaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o turma no objeto model para ser carregado no formulário
        model.addAttribute("turma", turma);

        // Retorna o template turma/alterar.html
        return "turma/alterar";
    }

    @PostMapping("/salvar")
    public String salvarTurma(@Valid Turma turma, BindingResult result,
                                    RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template turma/inserir.html
        if (result.hasErrors()) {
            return "turma/inserir";
        }



        // Salva o turma no banco de dados
        turmaRepository.save(turma);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Turma salva com sucesso!");

        // Redireciona para a página de listagem de turmas
        return "redirect:/turma/novo";
    }

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nomeCurso") String nomeCurso) {
        if (nomeCurso == null) {
            return "redirect:/turma";
        }
        List<Turma> listaTurmas = turmaRepository.findByNomeCursoContainingIgnoreCase(nomeCurso);
        model.addAttribute("turmas",listaTurmas);
        return "turma/listagem";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca a turma no banco de dados
        Turma turma = turmaRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o turma do banco de dados
        turmaRepository.delete(turma);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Turma excluída com sucesso!");

        // Redireciona para a página de listagem de turmas
        return "redirect:/turma";
    }
}
