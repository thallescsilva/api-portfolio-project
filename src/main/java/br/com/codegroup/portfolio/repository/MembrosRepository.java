package br.com.codegroup.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codegroup.portfolio.model.entity.Membros;

public interface MembrosRepository extends JpaRepository<Membros, Long> {
}
