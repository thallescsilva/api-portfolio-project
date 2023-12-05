package br.com.codegroup.portfolio.controller;

import br.com.codegroup.portfolio.controller.ProjetoController;
import br.com.codegroup.portfolio.model.ProjetoResponse;
import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.service.PessoaService;
import br.com.codegroup.portfolio.service.ProjetoService;
import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.codegroup.portfolio.util.ProjetoMapper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProjetoControllerTest {

    @Mock
    private ProjetoService projetoService;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private ProjetoController projetoController;

    public ProjetoControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarProjeto() {
        ProjetoDTO projetoDTO = criarProjetoDtoFicticio();
        Projeto projeto = criarProjetoFicticio();
        when(pessoaService.consultarPessoaPorId(anyLong())).thenReturn(criarPessoaFicticia());
        when(projetoService.cadastrarProjeto(any())).thenReturn(projeto);

        ResponseEntity<ProjetoResponse> responseEntity = projetoController.cadastrarProjeto(projetoDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(toDto(projeto), responseEntity.getBody());
    }

    @Test
    void testAtualizarProjeto() {
        Long id = 1L;
        ProjetoDTO projetoDTO = criarProjetoDtoFicticio();
        Projeto projetoAtualizado = criarProjetoFicticio();
        when(projetoService.atualizarProjeto(id, projetoDTO)).thenReturn(projetoAtualizado);

        ResponseEntity<ProjetoResponse> responseEntity = projetoController.atualizarProjeto(id, projetoDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toDto(projetoAtualizado), responseEntity.getBody());
    }

    @Test
    void testListarProjetos() {
        List<Projeto> projetos = criarListaDeProjetosFicticia();
        when(projetoService.listarProjetos()).thenReturn(projetos);

        ResponseEntity<List<ProjetoResponse>> responseEntity = projetoController.listarProjetos();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toListDto(projetos), responseEntity.getBody());
    }

    @Test
    void testConsultarProjeto() {
        Long id = 1L;
        Projeto projeto = criarProjetoFicticio();
        when(projetoService.consultarProjetoPorId(id)).thenReturn(projeto);

        ResponseEntity<ProjetoResponse> responseEntity = projetoController.consultarProjeto(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toDto(projeto), responseEntity.getBody());
    }

    @Test
    void testExcluirProjeto() {
        Long id = 1L;
        doNothing().when(projetoService).excluirProjeto(id);

        ResponseEntity<Void> responseEntity = projetoController.excluirProjeto(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(projetoService, times(1)).excluirProjeto(id);
    }

    private ProjetoDTO criarProjetoDtoFicticio() {
        return new ProjetoDTO(1L,"Projeto A",
                LocalDate.of(2023,1,1),
                LocalDate.of(2023,12,31),
                LocalDate.of(2023,12,31),
                "Descrição do Projeto A",
                StatusProjeto.EM_ANDAMENTO,100000.0F, RiscoProjeto.ALTO,1L);
    }

    private Projeto criarProjetoFicticio() {
        return new Projeto(1L,"Projeto A",
                LocalDate.of(2023,1,1),
                LocalDate.of(2023,12,31),
                LocalDate.of(2023,12,31),
                "Descrição do Projeto A",
                StatusProjeto.EM_ANDAMENTO,100000.0F, RiscoProjeto.ALTO, criarPessoaFicticia());
    }

    private List<Projeto> criarListaDeProjetosFicticia() {
        List<Projeto> projetos = new ArrayList<>();
        projetos.add(criarProjetoFicticio());
        return projetos;
    }

    private Pessoa criarPessoaFicticia() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("NomeFicticio");
        pessoa.setCpf("123.456.789-00");
        pessoa.setDataNascimento(LocalDate.of(1990, 1, 1));
        pessoa.setFuncionario(true);
        return pessoa;
    }
}
