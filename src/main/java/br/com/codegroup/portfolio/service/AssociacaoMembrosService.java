package br.com.codegroup.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codegroup.portfolio.model.entity.MembroId;
import br.com.codegroup.portfolio.model.entity.Membros;
import br.com.codegroup.portfolio.repository.MembrosRepository;
import br.com.codegroup.portfolio.repository.PessoaRepository;

@Service
public class AssociacaoMembrosService {

    @Autowired
    private MembrosRepository membrosRepository;

    @Autowired
    private PessoaRepository pessoaRepository; 

    public void associarMembroAoProjeto(Long idMembro, Long idProjeto) {
        if (pessoaRepository.existsByIdAndFuncionarioTrue(idMembro)) {
            MembroId membroId = new MembroId();
            membroId.setIdProjeto(idProjeto);
            membroId.setIdPessoa(idMembro);

            Membros membro = new Membros();
            membro.setId(membroId);

            membrosRepository.save(membro);
        } else {
            throw new RuntimeException("Apenas funcionários podem ser associados ao projeto.");
        }
    }
}
