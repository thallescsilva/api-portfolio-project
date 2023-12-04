package br.com.codegroup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.ProjetoRepository;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;

@SpringBootTest
class ProjetoServiceTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @InjectMocks
    private ProjetoService projetoService;
    
    @Test
    void cadastrarProjeto_ProjetoValido_CadastroBemSucedido() {
        Projeto projeto = new Projeto();

        when(projetoRepository.save(projeto)).thenReturn(projeto);

        Projeto resultado = projetoService.cadastrarProjeto(projeto);

        verify(projetoRepository, times(1)).save(projeto);

        assertNotNull(resultado);
    }

    @Test
    void cadastrarProjeto_ProjetoInvalido_ExcecaoLancada() {
        Projeto projeto = new Projeto(); 

        when(projetoRepository.save(projeto)).thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class,
                () -> projetoService.cadastrarProjeto(projeto));

        verify(projetoRepository, times(1)).save(projeto);
    }

     @Test
    void listarProjetos_ListaNaoVazia_RetornaLista() {
        List<Projeto> projetos = new ArrayList<>();
        when(projetoRepository.findAll()).thenReturn(projetos);

        List<Projeto> resultado = projetoService.listarProjetos();

        verify(projetoRepository, times(1)).findAll();

        assertNotNull(resultado);
    }

    @Test
    void consultarProjetoPorId_ProjetoExistente_RetornaProjeto() {
        Long idProjeto = 1L;

        Projeto projeto = new Projeto();
        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.of(projeto));

        Projeto resultado = projetoService.consultarProjetoPorId(idProjeto);

        verify(projetoRepository, times(1)).findById(idProjeto);

        assertNotNull(resultado);
    }

    @Test
    void consultarProjetoPorId_ProjetoNaoExistente_RetornaNull() {
        Long idProjeto = 1L;

        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.empty());

        Projeto resultado = projetoService.consultarProjetoPorId(idProjeto);

        verify(projetoRepository, times(1)).findById(idProjeto);

        assertNull(resultado);
    }

    @Test
    void excluirProjeto_ProjetoExistenteEStatusPermite_ExclusaoBemSucedida() {
        Long idProjeto = 1L;

        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.INICIADO);
        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.of(projeto));

        projetoService.excluirProjeto(idProjeto);

        verify(projetoRepository, times(1)).findById(idProjeto);
        
        verify(projetoRepository, times(1)).deleteById(idProjeto);
    }

    @Test
    void excluirProjeto_ProjetoExistenteEStatusNaoPermite_ExclusaoNaoPermitida() {
        Long idProjeto = 1L;

        Projeto projeto = new Projeto();
        projeto.setStatus(StatusProjeto.EM_ANDAMENTO);
        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.of(projeto));

        assertThrows(RuntimeException.class,
                () -> projetoService.excluirProjeto(idProjeto));

        verify(projetoRepository, times(1)).findById(idProjeto);
        
        verify(projetoRepository, never()).deleteById(idProjeto);
    }

    @Test
    void excluirProjeto_ProjetoNaoExistente_ExclusaoNaoPermitida() {
        Long idProjeto = 1L;

        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> projetoService.excluirProjeto(idProjeto));

        verify(projetoRepository, times(1)).findById(idProjeto);
        
        verify(projetoRepository, never()).deleteById(idProjeto);
    }

    @Test
    void atualizarProjeto_ProjetoExistente_AtualizacaoBemSucedida() {
        Long idProjeto = 1L;
        Projeto projetoAtualizado = new Projeto();

        Projeto projetoExistente = new Projeto();
        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.of(projetoExistente));
        when(projetoRepository.save(projetoExistente)).thenReturn(projetoExistente);

        Projeto resultado = projetoService.atualizarProjeto(idProjeto, projetoAtualizado);

        verify(projetoRepository, times(1)).findById(idProjeto);
        
        verify(projetoRepository, times(1)).save(projetoExistente);

        assertNotNull(resultado);
    }

    @Test
    void atualizarProjeto_ProjetoNaoExistente_ExcecaoLancada() {
        Long idProjeto = 1L;
        Projeto projetoAtualizado = new Projeto();

        when(projetoRepository.findById(idProjeto)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> projetoService.atualizarProjeto(idProjeto, projetoAtualizado));

        verify(projetoRepository, times(1)).findById(idProjeto);
        
        verify(projetoRepository, never()).save(Mockito.any());
    }

}
