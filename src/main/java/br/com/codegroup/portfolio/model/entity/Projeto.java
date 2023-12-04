package br.com.codegroup.portfolio.model.entity;

import java.time.LocalDate;

import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "projeto")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;

    @Column(name = "data_previsao_fim")
    private LocalDate dataPrevisaoFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    private Float orcamento;

    @Enumerated(EnumType.STRING)
    private RiscoProjeto risco;

    @ManyToOne
    @JoinColumn(name = "idgerente")
    private Pessoa gerente;
}