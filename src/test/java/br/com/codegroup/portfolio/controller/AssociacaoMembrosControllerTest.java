package br.com.codegroup.portfolio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.codegroup.portfolio.service.AssociacaoMembrosService;

import br.com.codegroup.portfolio.model.ProjetoMembrosResponse;
import br.com.codegroup.portfolio.service.AssociacaoMembrosService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AssociacaoMembrosControllerTest {

    @Mock
    private AssociacaoMembrosService associacaoMembrosService;

    @InjectMocks
    private AssociacaoMembrosController associacaoMembrosController;

    public AssociacaoMembrosControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarAssociacoes() {
        List<ProjetoMembrosResponse> membrosResponse = criarListaDeProjetoMembrosResponseFicticia();
        when(associacaoMembrosService.listarAssociacoes()).thenReturn(membrosResponse);

        ResponseEntity<List<ProjetoMembrosResponse>> responseEntity = associacaoMembrosController.listarAssociacoes();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(membrosResponse, responseEntity.getBody());
    }

    @Test
    void testAssociarMembroAoProjeto() {
        Long idMembro = 1L;
        Long idProjeto = 2L;

        doNothing().when(associacaoMembrosService).associarMembroAoProjeto(idMembro, idProjeto);

        ResponseEntity<String> responseEntity = associacaoMembrosController.associarMembroAoProjeto(idMembro, idProjeto);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Associação realizada com sucesso", responseEntity.getBody());
        verify(associacaoMembrosService, times(1)).associarMembroAoProjeto(idMembro, idProjeto);
    }

    private List<ProjetoMembrosResponse> criarListaDeProjetoMembrosResponseFicticia() {
        List<ProjetoMembrosResponse> membrosResponseList = new ArrayList<>();
        ProjetoMembrosResponse projetoMembrosResponse = new ProjetoMembrosResponse("Projeto 1", "Projeto 1", criarListaDePessoasFicticias());
        membrosResponseList.add(projetoMembrosResponse);
        return membrosResponseList;
    }

    private PessoaDTO criarPessoaDtoFicticia() {
        return new PessoaDTO(null, "Nome Atualizado",
                LocalDate.of(1980, 1, 1),
                "987.654.321-00", false);
    }

    private List<PessoaDTO> criarListaDePessoasFicticias() {
        List<PessoaDTO> pessoas = new ArrayList<>();
        pessoas.add(criarPessoaDtoFicticia());
        return pessoas;
    }
}