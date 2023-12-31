package br.com.codegroup.portfolio.service;

import br.com.codegroup.portfolio.exception.ProjetoNotFoundException;
import br.com.codegroup.portfolio.exception.RegraDeNegocioException;
import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.ProjetoRepository;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private PessoaService pessoaService;

    public Projeto cadastrarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto atualizarProjeto(Long id, ProjetoDTO projetoAtualizado) {
        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(ProjetoNotFoundException::new);

        Pessoa gerente = pessoaService.consultarPessoaPorId(projetoAtualizado.idGerente());
        projetoExistente.setGerente(gerente);

        projetoExistente.setNome(projetoAtualizado.nome());
        projetoExistente.setDataInicio(projetoAtualizado.dataInicio());
        projetoExistente.setDataPrevisaoFim(projetoAtualizado.dataPrevisaoFim());
        projetoExistente.setDataFim(projetoAtualizado.dataFim());
        projetoExistente.setDescricao(projetoAtualizado.descricao());
        projetoExistente.setStatus(projetoAtualizado.status());
        projetoExistente.setOrcamento(projetoAtualizado.orcamento());
        projetoExistente.setRisco(projetoAtualizado.risco());

        return projetoRepository.save(projetoExistente);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto consultarProjetoPorId(Long id) {
        return projetoRepository.findById(id)
                .orElseThrow(ProjetoNotFoundException::new);
    }

    public void excluirProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id)
                .orElseThrow(ProjetoNotFoundException::new);

        if (!podeExcluir(projeto.getStatus())) {
            throw new RegraDeNegocioException("Projetos não podem ser excluídos dentro dos status (INICIADO, EM ANDAMENTO, ENCERRADO)");
        }

        projetoRepository.deleteById(id);
    }

    public boolean podeExcluir(StatusProjeto status) {
        return status != StatusProjeto.INICIADO
                && status != StatusProjeto.EM_ANDAMENTO
                && status != StatusProjeto.ENCERRADO;
    }
}
