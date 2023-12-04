package br.com.codegroup.portfolio.model.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class MembroId implements Serializable {

    @Column(name = "idprojeto")
    private Long idProjeto;

    @Column(name = "idpessoa")
    private Long idPessoa;
}