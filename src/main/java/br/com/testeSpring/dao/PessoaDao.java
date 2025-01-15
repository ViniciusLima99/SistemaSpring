package br.com.testeSpring.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.testeSpring.model.Pessoa;

public interface PessoaDao extends JpaRepository<Pessoa, Integer> {

    @Query("select y from Pessoa y where y.status = 'ATIVO' ")
    public List<Pessoa> findByStatusAtivo();

    @Query("select y from Pessoa y where y.status = 'INATIVO' ")
    public List<Pessoa> findByStatusInativo();

    public List<Pessoa> findByNomeContainingIgnoreCase(String nome);
}
