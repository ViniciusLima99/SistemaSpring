package br.com.testeSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.testeSpring.dao.PessoaDao;
import br.com.testeSpring.model.Pessoa;
import jakarta.validation.Valid;

@Controller
public class PessoaController {
    @Autowired
    private PessoaDao pessoaRepositorio;

    @GetMapping("/inserirPessoa")
    public ModelAndView InserirPessoas(Pessoa pessoa) {
        ModelAndView mv = new ModelAndView(); 
        mv.setViewName("pessoas/formPessoa");
        mv.addObject("pessoa", new Pessoa());
        return mv;
    }

    @PostMapping("/inserirPessoa")
    public ModelAndView inserir(@Valid Pessoa pessoa, BindingResult br) {
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()) {
            mv.setViewName("pessoas/formPessoa");
            mv.addObject(pessoa);
        } else {
            mv.setViewName("redirect:/listaPessoas");
            pessoaRepositorio.save(pessoa);
        }
        
        return mv;
    }
    @GetMapping("/listaPessoas")
    public ModelAndView lista() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pessoas/listaPessoas");
        mv.addObject("Plist", pessoaRepositorio.findAll());

        return mv;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") Integer id) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pessoas/editar");
        Pessoa pessoa = pessoaRepositorio.getReferenceById(id);
        mv.addObject("pessoa", pessoa);
        return mv;
    }
    @GetMapping("/edit")
    public ModelAndView edit() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/listaPessoas");
        return mv;
    }
    @PostMapping("/edit")
    public ModelAndView edit(@Valid Pessoa pessoa, BindingResult br) {
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()) {
            mv.setViewName("pessoas/editar");
            mv.addObject(pessoa);
        } else {
            mv.setViewName("redirect:/listaPessoas");
            pessoaRepositorio.save(pessoa);
        }
        return mv;

    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pessoaRepositorio.deleteById(id);
        return "redirect:/listaPessoas";
    }
    @GetMapping("filtro")
    public ModelAndView filtro() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pessoas/filtro");
        mv.addObject("pessoas", new Pessoa());
        return mv;
    }

    @GetMapping("/ativos")
    public ModelAndView listaAtivos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pessoas/ativos");
        mv.addObject("ativos", pessoaRepositorio.findByStatusAtivo());
        return mv;
    }
    @GetMapping("/inativos")
    public ModelAndView listaInativos() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pessoas/inativos");
        mv.addObject("inativos", pessoaRepositorio.findByStatusInativo());
        return mv;
    } 

    @PostMapping("/pesquisar")
    public ModelAndView pesquisar(@RequestParam(required=false) String nome) {
        ModelAndView mv = new ModelAndView(); 
        List<Pessoa> listapessoa; 
        if(nome== null || nome.isEmpty()) {
            listapessoa = pessoaRepositorio.findAll();
        } else  {
            listapessoa = pessoaRepositorio.findByNomeContainingIgnoreCase(nome);
        }
        mv.addObject("listapessoa", listapessoa);        
        mv.setViewName("pessoas/pesquisa-resultado");
        return mv;
    }
}
