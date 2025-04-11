package br.senai.sp.escolamvc.controller;


//imports
import br.senai.sp.escolamvc.model.Responsavel;
import br.senai.sp.escolamvc.model.Endereco;
import br.senai.sp.escolamvc.model.Telefone;
import br.senai.sp.escolamvc.repository.ResponsavelRepository;
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
@RequestMapping("/responsavel")
public class ResponsavelController {

    @Autowired
    private ResponsavelRepository responsavelRepository;

    /*
     * Método que direciona para templates/responsavels/listagem.html
     */
    @GetMapping
    public String listagem(Model model) {

        // Busca a lista de responsavels no banco de dados
        List<Responsavel> listaResponsavels = responsavelRepository.findAll();

        // Adiciona a lista de responsavels no objeto model para ser carregado no template
        model.addAttribute("responsaveles", listaResponsavels);

        // Retorna o template responsavel/listagem.html
        return "responsavel/listagem";
    }

    //metodo de buscar os dados no banco

    @PostMapping("/buscar")
    public String buscar(Model model, @Param("nome") String nome) {
        if (nome == null) {
            return "redirect:/responsavel";
        }
        List<Responsavel> listaResponsavels = responsavelRepository.findByNomeContainingIgnoreCase(nome);
        model.addAttribute("responsaveles",listaResponsavels);
        return "responsavel/listagem";
    }


    /*
     * Método de acesso à página http://localhost:8080/responsavel/novo
     */
    @GetMapping("/novo")
    public String cadastrar(Model model){

        // Adiciona um objeto responsavel vazio para
        // ser carregado no formulário
        model.addAttribute("responsavel", new Responsavel());

        // Adiciona um objeto endereco vazio para
        // ser carregado no formulário
        model.addAttribute("endereco", new Endereco());



        // Retorna o template responsavel/inserir.html
        return "responsavel/inserir";
    }

    //metodo de salvar
    @PostMapping("/salvar")
    public String salvarResponsavel(@Valid Responsavel responsavel, BindingResult result,
                                  RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template responsavels/inserir.html
        if (result.hasErrors()) {
            return "responsavel/inserir";
        }



        // Salva o responsavel no banco de dados
        responsavelRepository.save(responsavel);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem", "Responsavel salvo com sucesso!");

        // Redireciona para a página de listagem de responsavels
        return "redirect:/responsavel/novo";
    }

    //aqui ele mostra se o email e cpf ja tão cadastrados

    public BindingResult errosPersonalizadosInsercao(Responsavel responsavel, BindingResult result) {

        // Verifica se o e-mail já está cadastrado
        if (responsavelRepository.findByEmail(responsavel.getEmail()) != null) {
            result.rejectValue("email", "email.existente",
                    "Já existe um responsavel cadastrado com este e-mail");
        }

        // Verifica se o CPF já está cadastrado
        if (responsavelRepository.findByCpf(responsavel.getCpf()) != null) {
            result.rejectValue("cpf", "cpf.existente",
                    "Já existe um responsavel cadastrado com este CPF");
        }
        return result;
    }







    /*
     * Método que direciona para templates/responsavels/alterar.html
     */
    @GetMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, Model model) {

        // Busca o responsavel no banco de dados
        Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));

        // Adiciona o responsavel no objeto model para ser carregado no formulário
        model.addAttribute("responsavel", responsavel);

        // Retorna o template responsavel/alterar.html
        return "responsavel/alterar";
    }


    /*
     * Método que é invocado ao clicar no botão "Salvar" do template responsavels/alterar.html
     * O objeto responsavel é carregado com os dados informados no formulário.
     * O objeto result contém o resultado da validação do formulário.
     * O objeto attributes é utilizado para enviar uma mensagem para o template.
     */
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable("id") Long id, @Valid Responsavel responsavel,
                          BindingResult result, RedirectAttributes attributes) {

        // Se houver erro de validação, retorna para o template responsavels/alterar.html
        if (result.hasErrors()) {
            return "responsavel/alterar";
        }


        // Busca o responsavel no banco de dados
        Responsavel responsavelAtualizado = responsavelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID inválido"));


        // Seta os dados do responsavel

        responsavelAtualizado.setNome(responsavel.getNome());
        responsavelAtualizado.setEmail(responsavel.getEmail());
        responsavelAtualizado.setCpf(responsavel.getCpf());
        responsavelAtualizado.setEndereco(responsavel.getEndereco());
        responsavelAtualizado.setTelefones(responsavel.getTelefones());

        // Salva o responsavel no banco de dados
        responsavelRepository.save(responsavelAtualizado);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Responsavel atualizado com sucesso!");

        // Redireciona para a página de listagem de responsavels
        return "redirect:/responsavel";
    }

    /*
     * Método para excluir um responsavel
     */
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id,
                          RedirectAttributes attributes) {

        // Busca o responsavel no banco de dados
        Responsavel responsavel = responsavelRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("ID inválido"));

        // Exclui o responsavel do banco de dados
        responsavelRepository.delete(responsavel);

        // Adiciona uma mensagem que será exibida no template
        attributes.addFlashAttribute("mensagem",
                "Responsavel excluído com sucesso!");

        // Redireciona para a página de listagem de responsavels
        return "redirect:/responsavel";
    }

    //metodo que adiciona um telefone

    @PostMapping("/addTelefone")
    public String addTelefone(Responsavel responsavel) {
        responsavel.addTelefone(new Telefone());
        return "responsavel/inserir :: telefones";
    }

    //esse metodo remove um telefone

    @PostMapping("/removeTelefone")
    public String removeTelefone(Responsavel responsavel, @RequestParam("removeDynamicRow") Integer telefoneIndex) {
        responsavel.getTelefones().remove(telefoneIndex.intValue());
        return "responsavel/inserir :: telefones";
    }


}