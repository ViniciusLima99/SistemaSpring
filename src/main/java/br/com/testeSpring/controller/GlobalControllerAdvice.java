package br.com.testeSpring.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import br.com.testeSpring.model.Pessoa;

import org.springframework.web.bind.annotation.ControllerAdvice;
@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addPessoas(Model model) {
        model.addAttribute("pessoas", new Pessoa());
    }
}