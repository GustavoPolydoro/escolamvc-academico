package br.senai.sp.escolamvc.controller;


//imports

import br.senai.sp.escolamvc.model.*;
import br.senai.sp.escolamvc.repository.AlunoRepository;
import br.senai.sp.escolamvc.repository.TurmaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {


    //ingeção de dependencia
    //ele meio que controla o banco de dados
    //é do próprio framework
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private TurmaRepository turmaRepository;


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

    //metodo de buscar no bncd
    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null) {
            return "redirect:/aluno";
        }
        List<Aluno> listaAlunos = alunoRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("alunos", listaAlunos);
        return "aluno/listagem";
    }


// ce continua aqui


    /*
     * Método de acesso à página http://localhost:8080/aluno/novo
     */
    @GetMapping("/novo")
    public String cadastrar(Model model) {

        // Adiciona um objeto aluno vazio para
        // ser carregado no formulário
        model.addAttribute("aluno", new Aluno());

        // Adiciona um objeto endereco vazio para
        // ser carregado no formulário
        model.addAttribute("endereco", new Endereco());




        // Retorna o template aluno/inserir.html
        return "aluno/inserir";
    }

    //metodo de salvar no bncd
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

    //ele verifica se o cpf e o email já foram cadastradas

    public BindingResult errosPersonalizadosInsercao(Aluno aluno, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (alunoRepository.findByEmail(aluno.getEmail()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um aluno cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (alunoRepository.findByCpf(aluno.getCpf()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um aluno cadastrado com este CPF");
        }
        return result;
    }


    /*
     * Método que direciona para templates/alunos/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o aluno no banco de dados
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o aluno no objeto model para ser carregado no formulário
        model.addAttribute("aluno", aluno);


        // Lista de turmas
        List<Turma> listaTurmas = turmaRepository.findAll();
        model.addAttribute("turmas", listaTurmas);

        //Retorna o template aluno/alterar.html
        return "aluno/alterar";
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


    //adiciona telefone
    @PostMapping("/addTelefone")
    public String addTelefone(Aluno aluno) {
        aluno.addTelefone(new Telefone());
        return "aluno/inserir :: telefones";
    }

    //remove tefelone
    @PostMapping("/removeTelefone")
    public String removeTelefone(Aluno aluno, @RequestParam("removeDynamicRow") Integer telefoneIndex) {
        aluno.getTelefones().remove(telefoneIndex.intValue());
        return "aluno/inserir :: telefones";
    }

    @PostMapping("/addTurma")
    public String addTurma(Aluno aluno, Model model) {
        List<Turma> listaTurmas = turmaRepository.findAll();
        model.addAttribute("turmas", listaTurmas);

        aluno.addTurma(new Turma());
        String turmas = "aluno/inserir :: turmas";
        return "aluno/inserir :: turmas";
    }

    @PostMapping("/removeTurma")
    public String removeTurma(Aluno aluno, @RequestParam("removeDynamicRow") Integer turmasIndex) {
        aluno.getTurmas().remove(turmasIndex.intValue());
        return "aluno/inserir :: turmas";
    }


}
