package br.com.codegroup.portfolio.service;


import br.com.codegroup.portfolio.exception.ProjetoNotFoundException;
import br.com.codegroup.portfolio.exception.RegraDeNegocioException;
import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.ProjetoRepository;
import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ProjetoService projetoService;

    @Test
    void testCadastrarProjeto() {
        Projeto projeto = criarProjeto();
        when(projetoRepository.save(any())).thenReturn(projeto);

        Projeto resultado = projetoService.cadastrarProjeto(new Projeto());

        verify(projetoRepository, times(1)).save(any());
        assertNotNull(resultado);
    }

    @Test
    void testAtualizarProjeto() {
        Long projetoId = 1L;
        ProjetoDTO projetoDTO = new ProjetoDTO(null, null, null, null, null, null, null, null, null, 2L);

        Projeto projetoExistente = new Projeto();
        Pessoa gerente = new Pessoa();
        when(projetoRepository.findById(projetoId)).thenReturn(Optional.of(projetoExistente));
        when(pessoaService.consultarPessoaPorId(projetoDTO.idGerente())).thenReturn(gerente);
        when(projetoRepository.save(any())).thenReturn(projetoExistente);

        Projeto resultado = projetoService.atualizarProjeto(projetoId, projetoDTO);

        verify(projetoRepository, times(1)).findById(projetoId);
        verify(pessoaService, times(1)).consultarPessoaPorId(projetoDTO.idGerente());
        verify(projetoRepository, times(1)).save(any());
        assertNotNull(resultado);
    }

    @Test
    void testListarProjetos() {
        List<Projeto> projetos = new ArrayList<>();
        projetos.add(new Projeto());
        when(projetoRepository.findAll()).thenReturn(projetos);

        List<Projeto> resultado = projetoService.listarProjetos();

        verify(projetoRepository, times(1)).findAll();
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    @Test
    void testConsultarProjetoPorId_Encontrado() {
        Long projetoId = 1L;
        Projeto projetoExistente = new Projeto();
        when(projetoRepository.findById(projetoId)).thenReturn(Optional.of(projetoExistente));

        Projeto resultado = projetoService.consultarProjetoPorId(projetoId);

        verify(projetoRepository, times(1)).findById(projetoId);
        assertNotNull(resultado);
    }

    @Test
    void testConsultarProjetoPorId_NaoEncontrado() {
        Long projetoId = 1L;
        when(projetoRepository.findById(projetoId)).thenReturn(Optional.empty());

        assertThrows(ProjetoNotFoundException.class, () -> projetoService.consultarProjetoPorId(projetoId));

        verify(projetoRepository, times(1)).findById(projetoId);
    }

    @Test
    void testExcluirProjeto_NaoPodeExcluir() {
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.INICIADO);
        when(projetoRepository.findById(projetoId)).thenReturn(Optional.of(projeto));

        assertThrows(RegraDeNegocioException.class, () -> projetoService.excluirProjeto(projetoId));

        verify(projetoRepository, times(1)).findById(projetoId);
        verify(projetoRepository, never()).deleteById(projetoId);
    }

    @Test
    void testExcluirProjeto_PodeExcluir() {
        Long projetoId = 1L;
        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.EM_ANALISE);
        when(projetoRepository.findById(projetoId)).thenReturn(Optional.of(projeto));

        projetoService.excluirProjeto(projetoId);

        verify(projetoRepository, times(1)).findById(projetoId);
        verify(projetoRepository, times(1)).deleteById(projetoId);
    }

    @Test
    void testPodeExcluir() {
        Projeto projeto1 = new Projeto();
        projeto1.setStatus(StatusProjeto.INICIADO);
        Projeto projeto2 = new Projeto();
        projeto2.setStatus(StatusProjeto.EM_ANDAMENTO);
        Projeto projeto3 = new Projeto();
        projeto3.setStatus(StatusProjeto.ENCERRADO);

        assertFalse(projetoService.podeExcluir(projeto1.getStatus()));
        assertFalse(projetoService.podeExcluir(projeto2.getStatus()));
        assertFalse(projetoService.podeExcluir(projeto3.getStatus()));
    }

    public static Projeto criarProjeto() {
        Projeto projeto = new Projeto();
        projeto.setId(1L);
        projeto.setNome("Projeto de Teste");
        projeto.setDataInicio(LocalDate.now().minusDays(7));
        projeto.setDataPrevisaoFim(LocalDate.now().plusDays(14));
        projeto.setDataFim(LocalDate.now().plusDays(21));
        projeto.setDescricao("Descrição do Projeto de Teste");
        projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
        projeto.setOrcamento(50000.0F);
        projeto.setRisco(RiscoProjeto.ALTO);

        Pessoa gerente = new Pessoa();
        gerente.setId(2L);
        gerente.setNome("Gerente do Projeto");
        gerente.setDataNascimento(LocalDate.of(1980, 1, 1));
        gerente.setCpf("123.456.789-00");
        gerente.setFuncionario(true);

        projeto.setGerente(gerente);

        return projeto;
    }
}
