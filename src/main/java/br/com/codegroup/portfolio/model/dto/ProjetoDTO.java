package br.com.codegroup.portfolio.model.dto;

import java.time.LocalDate;

import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;

public record ProjetoDTO(
    Long id,
    String nome,
    LocalDate dataInicio,
    LocalDate dataPrevisaoFim,
    LocalDate dataFim,
    String descricao,
    StatusProjeto status,
    Float orcamento,
    RiscoProjeto risco,
    Long idGerente
) {}
