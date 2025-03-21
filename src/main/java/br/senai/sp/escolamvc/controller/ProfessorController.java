package br.senai.sp.escolamvc.controller;

import br.senai.sp.escolamvc.model.Professor;
import br.senai.sp.escolamvc.model.Endereco;
import br.senai.sp.escolamvc.model.Telefone;
import br.senai.sp.escolamvc.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//metodo que leva pro responsavel

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private ProfessorRepository professorRepository;






    /*
     * Método que direciona para templates/professors/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de professors no banco de dados
        List<Professor> listaProfessors = professorRepository.findAll();

        // Adiciona a lista de professors no objeto model para ser carregado no template
        model.addAttribute("professores", listaProfessors);

        // Retorna o template professor/listagem.html
        return "professor/listagem";
    }

    //metodo de buscar os dados no banco

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null) {
            return "redirect:/professor";
        }
        List<Professor> listaProfessors = professorRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("professors",listaProfessors);
        return "professor/listagem";
    }


    /*
     * Método de acesso à página http://localhost:8080/professor/novo
     */
    @GetMapping("/novo")
    public String cadastrar(Model model){

        // Adiciona um objeto professor vazio para
        // ser carregado no formulário
        model.addAttribute("professor", new Professor());

        // Adiciona um objeto endereco vazio para
        // ser carregado no formulário
        model.addAttribute("endereco", new Endereco());

        // Lista de turmas
        //List<Turma> listaTurmas = turmaRepository.findAll();
        //model.addAttribute("turmas", listaTurmas);



        // Retorna o template professor/inserir.html
        return "professor/inserir";
    }

    //metodo de salvar
    @PostMapping("/salvar")
    public String salvarProfessor(@Valid Professor professor, BindingResult result,
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

    //aqui ele mostra se o email e cpf ja tão cadastrados

    public BindingResult errosPersonalizadosInsercao(Professor professor, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (professorRepository.findByEmail(professor.getEmail()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um professor cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (professorRepository.findByCpf(professor.getCpf()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um professor cadastrado com este CPF");
        }
        return result;
    }







    /*
     * Método que direciona para templates/professors/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o professor no banco de dados
        Professor professor = professorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o professor no objeto model para ser carregado no formulário
        model.addAttribute("professor", professor);

        // Retorna o template professor/alterar.html
        return "professor/alterar";
    }


    /*
     * Método que é invocado ao clicar no botão "Salvar" do template professors/alterar.html
     * O objeto professor é carregado com os dados informados no formulário.
     * O objeto result contém o resultado da validação do formulário.
     * O objeto attributes é utilizado para enviar uma mensagem para o template.
     */
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
        professorAtualizado.setEmail(professor.getEmail());
        professorAtualizado.setCpf(professor.getCpf());
        professorAtualizado.setEndereco(professor.getEndereco());
        professorAtualizado.setTelefones(professor.getTelefones());

        // Salva o professor no banco de dados
        professorRepository.save(professorAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Professor atualizado com sucesso!");

        // Redireciona para a página de listagem de professors
        return "redirect:/professor";
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

    //metodo que adiciona um telefone

    @PostMapping("/addTelefone")
    public String addTelefone(Professor professor) {
        professor.addTelefone(new Telefone());
        return "professor/inserir :: telefones";
    }

    //esse metodo remove um telefone

    @PostMapping("/removeTelefone")
    public String removeTelefone(Professor professor, @RequestParam("removeDynamicRow") Integer telefoneIndex) {
        professor.getTelefones().remove(telefoneIndex.intValue());
        return "professor/inserir :: telefones";
    }


}