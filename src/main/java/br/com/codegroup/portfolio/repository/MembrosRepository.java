package br.com.codegroup.portfolio.repository;

import br.com.codegroup.portfolio.model.entity.MembroId;
import br.com.codegroup.portfolio.model.entity.Membros;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembrosRepository extends JpaRepository<Membros, MembroId> {

    List<Membros> findById_IdProjeto(Long id);

    boolean existsById_IdProjeto(Long id);
}
