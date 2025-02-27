package br.com.testeSpring.dao;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.testeSpring.model.Usuario;

public interface  UsuarioDao extends JpaRepository<Usuario, Long> {
    @Query("select i from Usuario i where i.email = :email")
    public Usuario findByEmail(String email); 

    @Query("select j from Usuario j where j.userN = :userN and j.senha = :senha")
    public Usuario buscarLogin(String userN, String senha);
}
