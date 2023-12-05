package br.com.codegroup.portfolio.service;

import br.com.codegroup.portfolio.exception.RegraDeNegocioException;
import br.com.codegroup.portfolio.model.ProjetoMembrosResponse;
import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.MembroId;
import br.com.codegroup.portfolio.model.entity.Membros;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.MembrosRepository;
import br.com.codegroup.portfolio.util.PessoaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociacaoMembrosService {

    @Autowired
    private MembrosRepository membrosRepository;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private ProjetoService projetoService;

    public void associarMembroAoProjeto(Long idMembro, Long idProjeto) {
        if (pessoaService.pessoaEfuncionario(idMembro)) {
            MembroId membroId = new MembroId();
            membroId.setIdProjeto(idProjeto);
            membroId.setIdPessoa(idMembro);

            Membros membro = new Membros();
            membro.setId(membroId);

            membrosRepository.save(membro);
        } else {
            throw new RegraDeNegocioException("Apenas funcionários podem ser associados ao projeto.");
        }
    }

    public List<ProjetoMembrosResponse> listarAssociacoes() {
        List<Projeto> projetos = projetoService.listarProjetos();

        return projetos.stream()
                .filter(projeto -> membrosRepository.existsById_IdProjeto(projeto.getId()))
                .map(projeto -> {
                    List<Membros> membros = membrosRepository.findById_IdProjeto(projeto.getId());
                    List<PessoaDTO> membrosDTO = membros.stream()
                            .map(membro -> {
                                Pessoa pessoa = pessoaService.consultarPessoaPorId(membro.getId().getIdPessoa());
                                return PessoaMapper.toDto(pessoa);
                            })
                            .collect(Collectors.toList());
                    return ProjetoMembrosResponse.from(projeto, membrosDTO);
                })
                .collect(Collectors.toList());
    }
}
