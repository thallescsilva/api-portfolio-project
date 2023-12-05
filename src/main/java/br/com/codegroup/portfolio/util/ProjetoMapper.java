package br.com.codegroup.portfolio.util;

import br.com.codegroup.portfolio.model.ProjetoResponse;
import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;

import java.util.List;
import java.util.stream.Collectors;

public class ProjetoMapper {

    public static Projeto toEntity(ProjetoDTO dto) {
        Projeto projeto = new Projeto();

        projeto.setId(dto.id());
        projeto.setNome(dto.nome());
        projeto.setDataInicio(dto.dataInicio());
        projeto.setDataPrevisaoFim(dto.dataPrevisaoFim());
        projeto.setDataFim(dto.dataFim());
        projeto.setDescricao(dto.descricao());
        projeto.setStatus(dto.status());
        projeto.setOrcamento(dto.orcamento());
        projeto.setRisco(dto.risco());

        return projeto;
    }
    
    public static ProjetoResponse toDto(Projeto entity) {
        return new ProjetoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getDataInicio(),
                entity.getDataPrevisaoFim(),
                entity.getDataFim(),
                entity.getDescricao(),
                entity.getStatus(),
                entity.getOrcamento(),
                entity.getRisco(),
                PessoaMapper.toDto(entity.getGerente())
        );
    }

    public static List<ProjetoResponse> toListDto(List<Projeto> entities) {
        return entities.stream()
                .map(ProjetoMapper::toDto)
                .collect(Collectors.toList());
    }
}
