package br.com.codegroup.portfolio.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.codegroup.portfolio.service.AssociacaoMembrosService;

class AssociacaoMembrosControllerTest {

    @Mock
    private AssociacaoMembrosService associacaoMembrosService;

    @InjectMocks
    private AssociacaoMembrosController associacaoMembrosController;

    @Test
    void associarMembroAoProjeto_PessoaFuncionario_AssociacaoBemSucedida() {
        Long idMembro = 1L;
        Long idProjeto = 2L;

        Mockito.doNothing().when(associacaoMembrosService).associarMembroAoProjeto(idMembro, idProjeto);

        ResponseEntity<String> response = associacaoMembrosController.associarMembroAoProjeto(idMembro, idProjeto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Associação realizada com sucesso", response.getBody());
    }

    @Test
    void associarMembroAoProjeto_PessoaNaoFuncionario_ExcecaoLancada() {
        Long idMembro = 1L;
        Long idProjeto = 2L;

        Mockito.doThrow(RuntimeException.class).when(associacaoMembrosService).associarMembroAoProjeto(idMembro, idProjeto);

        ResponseEntity<String> response = associacaoMembrosController.associarMembroAoProjeto(idMembro, idProjeto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // ou outro código adequado

        assertEquals("Membro ou projeto não encontrado", response.getBody());
    }
}
