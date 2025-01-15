package br.com.testeSpring.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.testeSpring.dao.UsuarioDao;
import br.com.testeSpring.model.Usuario;
import br.com.testeSpring.util.Util;
import br.com.testeSpring.Exceptions.EmailExistsException;
import br.com.testeSpring.Exceptions.ServiceException2;
import br.com.testeSpring.Exceptions.CriptoExistException; 

@Service
public class ServiceUsuario {
    @Autowired
    private UsuarioDao repositorioUsuario;

    public void salvarUsuario(Usuario user) throws Exception {
        try {
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                throw new IllegalArgumentException("O campo email não pode ser nulo ou vazio.");
            }
    
            if (repositorioUsuario.findByEmail(user.getEmail()) != null) {
                throw new EmailExistsException("Já existe um email cadastrado para: " + user.getEmail());
            }
    
            user.setSenha(Util.md5(user.getSenha()));
    
            repositorioUsuario.save(user);
        } catch (NoSuchAlgorithmException e) {
            throw new CriptoExistException("Erro na criptografia da senha.");
        }
    }

    public Usuario loginUser(String user, String senha) throws ServiceException2 {
        Usuario userLogin = repositorioUsuario.buscarLogin(user, senha);
        return userLogin;
    }


}
