package br.com.codegroup.portfolio.model.entity;

import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
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