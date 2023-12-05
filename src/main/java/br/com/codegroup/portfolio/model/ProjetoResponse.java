package br.com.codegroup.portfolio.model;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;

import java.time.LocalDate;

public record ProjetoResponse (
        Long id,
        String nome,
        LocalDate dataInicio,
        LocalDate dataPrevisaoFim,
        LocalDate dataFim,
        String descricao,
        StatusProjeto status,
        Float orcamento,
        RiscoProjeto risco,
        PessoaDTO gerente
) {}
