package br.com.testeSpring.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.testeSpring.model.Pessoa;
import br.com.testeSpring.model.Usuario;
import br.com.testeSpring.service.ServiceUsuario;
import br.com.testeSpring.util.Util;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import br.com.testeSpring.Exceptions.CriptoExistException;
import br.com.testeSpring.Exceptions.EmailExistsException;
import br.com.testeSpring.Exceptions.ServiceException2;


@Controller
public class UsuarioController {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @GetMapping("/")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Login/login");
        mv.addObject("usuario", new Usuario());
        return mv;
    }
    @GetMapping("/cadastro")
    public ModelAndView cadastrar() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("Login/cadastro");
        mv.addObject("usuario", new Usuario());
        return mv;
    } 

    @PostMapping("/salvarUsuario")
    public String salvarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "Login/cadastro"; 
        }
    
        try {
            serviceUsuario.salvarUsuario(usuario);
        } catch (EmailExistsException e) {
            model.addAttribute("emailError", e.getMessage());
            return "Login/cadastro"; 
        } catch (CriptoExistException e) {
            model.addAttribute("cryptoError", e.getMessage());
            return "Login/cadastro"; 
        } catch (IllegalArgumentException e) {
            model.addAttribute("inputError", e.getMessage());
            return "Login/cadastro"; 
        } catch (Exception e) {
            model.addAttribute("genericError", "Erro ao salvar usuário.");
            return "Login/cadastro"; 
        }
    
        return "redirect:/login";
    }

    @GetMapping("/index")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");  //ele busca em templates pelo nome 
        mv.addObject("pessoas", new Pessoa());
        return mv;
    }

    @PostMapping("/")
    public ModelAndView login(@Valid Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceException2 {
        ModelAndView mv = new ModelAndView();
    
        if (br.hasErrors()) {
            mv.setViewName("Login/login");
            return mv;
        }
    
        Usuario userLogin = serviceUsuario.loginUser(usuario.getUserN(), Util.md5(usuario.getSenha()));
        if (userLogin == null) {
            br.rejectValue("userN", "error.usuario", "Usuário ou senha incorretos.");
            mv.setViewName("Login/login");
            return mv;
        }
    
        session.setAttribute("usuarioLogado", userLogin);
        return index(); 
    }

    @PostMapping("/logout") 
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return login();
    }

}
