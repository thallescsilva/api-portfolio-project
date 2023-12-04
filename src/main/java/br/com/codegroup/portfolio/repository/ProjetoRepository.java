package br.com.codegroup.portfolio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    Optional<Projeto> findByNome(String nome);
    List<Projeto> findByStatus(StatusProjeto status);
}