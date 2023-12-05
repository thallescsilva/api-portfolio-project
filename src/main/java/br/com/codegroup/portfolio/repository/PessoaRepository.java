package br.com.codegroup.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codegroup.portfolio.model.entity.Pessoa;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    boolean existsByIdAndFuncionarioTrue(Long id);

    Optional<Pessoa> findByNome(String nome);

}
