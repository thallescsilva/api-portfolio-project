package br.com.codegroup.portfolio.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.ProjetoRepository;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Projeto cadastrarProjeto(Projeto projeto) {
        return projetoRepository.save(projeto);
    }

    public Projeto atualizarProjeto(Long id, Projeto projetoAtualizado) {
        Projeto projetoExistente = projetoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        modelMapper.map(projetoAtualizado, projetoExistente);

        return projetoRepository.save(projetoExistente);
    }

    public List<Projeto> listarProjetos() {
        return projetoRepository.findAll();
    }

    public Projeto consultarProjetoPorId(Long id) {
        return projetoRepository.findById(id).orElse(null);
    }

    public Projeto consultarProjetoPorNome(String nome) {
        return projetoRepository.findByNome(nome).orElse(null);
    }

    public List<Projeto> listarProjetosPorStatus(StatusProjeto status) {
        return projetoRepository.findByStatus(status);
    }

    public void excluirProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElse(null);
        if (projeto != null && podeExcluir(projeto.getStatus())) {
            projetoRepository.deleteById(id);
        }
    }

    private boolean podeExcluir(StatusProjeto status) {
        // Adapte esta lógica conforme necessário
        return status != StatusProjeto.INICIADO
                && status != StatusProjeto.EM_ANDAMENTO
                && status != StatusProjeto.ENCERRADO;
    }
}
