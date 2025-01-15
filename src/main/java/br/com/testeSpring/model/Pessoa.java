package br.com.testeSpring.model;

import jakarta.persistence.Id;

import br.com.testeSpring.Enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pessoas")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

        @Column(name = "nome", nullable = false)
    @Size(min = 4, max = 35, message = "O nome deve ter entre 4 e 35 caracteres")
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @Column(name = "idade", nullable = false)
    @Min(value = 0, message = "A idade deve ser no mínimo 0")
    @NotNull(message = "A idade não pode ser nula")
    private Integer idade;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O status não pode ser nulo")
    
    private Status status;




    /**
     * @return Integer return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return Integer return the idade
     */
    public Integer getIdade() {
        return idade;
    }

    /**
     * @param idade the idade to set
     */
    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    /**
     * @return Enum return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

}
