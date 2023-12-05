package br.com.codegroup.portfolio.model.dto;

import java.time.LocalDate;

public record PessoaDTO(
    Long id,
    String nome,
    LocalDate dataNascimento,
    String cpf,
    Boolean funcionario
) {}
