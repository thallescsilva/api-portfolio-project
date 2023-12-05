package br.com.codegroup.portfolio.model;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Projeto;

import java.util.List;

public record ProjetoMembrosResponse(
        String nome,
        String descricao,
        List<PessoaDTO> membros
) {
    public static ProjetoMembrosResponse from(Projeto projeto, List<PessoaDTO> membros) {
        return new ProjetoMembrosResponse(projeto.getNome(), projeto.getDescricao(), membros);
    }
}
