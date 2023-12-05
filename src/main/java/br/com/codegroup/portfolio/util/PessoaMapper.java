package br.com.codegroup.portfolio.util;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;

import java.util.List;
import java.util.stream.Collectors;

public class PessoaMapper {

    public static Pessoa toEntity(PessoaDTO dto) {
        Pessoa pessoa = new Pessoa();

        pessoa.setId(dto.id());
        pessoa.setNome(dto.nome());
        pessoa.setDataNascimento(dto.dataNascimento());
        pessoa.setCpf(dto.cpf());
        pessoa.setFuncionario(dto.funcionario());

        return pessoa;
    }

    public static PessoaDTO toDto(Pessoa entity) {
        return new PessoaDTO(
                entity.getId(),
                entity.getNome(),
                entity.getDataNascimento(),
                entity.getCpf(),
                entity.isFuncionario()
        );
    }

    public static List<PessoaDTO> toListDto(List<Pessoa> entities) {
        return entities.stream()
                .map(PessoaMapper::toDto)
                .collect(Collectors.toList());
    }
}
