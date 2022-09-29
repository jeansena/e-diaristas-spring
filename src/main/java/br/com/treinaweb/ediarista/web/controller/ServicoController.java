package br.com.treinaweb.ediarista.web.controller;

import java.lang.ProcessBuilder.Redirect;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.treinaweb.ediarista.core.enums.Icone;
import br.com.treinaweb.ediarista.web.dtos.FlashMessage;
import br.com.treinaweb.ediarista.web.dtos.ServicoForm;

import br.com.treinaweb.ediarista.web.services.WebservicoService;

@Controller
@RequestMapping("/admin/servicos")
public class ServicoController {

    @Autowired
    private WebservicoService service;

    // listar todas lista do banco de dados
    @GetMapping
    public ModelAndView buscartodos() {
        var modelAndView = new ModelAndView("admin/servico/lista");
        // findAll() -- lista do banco
        modelAndView.addObject("servicos", service.buscarTodos());

        return modelAndView;
    }

    // chama a view de cadastro e com a variavel = "servico"
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        var modelAndView = new ModelAndView("admin/servico/form");

        modelAndView.addObject("form", new ServicoForm());

        return modelAndView;
    }

    // vai salvar no banco de dados atraves do POST..
    @PostMapping("/cadastrar")
    public String cadastrar(@Valid @ModelAttribute("form") ServicoForm form, BindingResult result,
            RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "admin/servico/form";
        }

        service.cadastrar(form);
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success", "Serviço cadastrado com sucesso!"));
        return "redirect:/admin/servicos";
    }

    // chama a view editar passando "id como argumento e variavel {id}"
    @GetMapping("/{id}/editar")
    public ModelAndView editar(@PathVariable Long id) {
        var modelAndView = new ModelAndView("admin/servico/form");

        modelAndView.addObject("form", service.buscarPorId(id));

        return modelAndView;
    }

    // editar usando o POST passando a variavel {id} e o Servico
    @PostMapping("/{id}/editar")
    public String editar(@PathVariable Long id, @Valid @ModelAttribute("form") ServicoForm form, BindingResult result,
            RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "admin/servico/form";
        }

        service.editar(form, id);
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success", "Serviço editado com sucesso!"));

        return "redirect:/admin/servicos";
    }

    // excluir passando o id como argumento.. e como variavel "{id}"
    @GetMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes attrs) {

        service.excluirPorId(id);
        attrs.addFlashAttribute("alert", new FlashMessage("alert-success", "Serviço excluido com sucesso!"));
        return "redirect:/admin/servicos";
    }

    // aqui esta se referenciando a class Icone que e um "Enum"
    @ModelAttribute("icones")
    public Icone[] getIcones() {
        return Icone.values();
    }

}
