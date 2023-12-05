package br.com.codegroup.portfolio.controller;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.service.PessoaService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static br.com.codegroup.portfolio.util.PessoaMapper.toDto;
import static br.com.codegroup.portfolio.util.PessoaMapper.toListDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PessoaControllerTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    public PessoaControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarPessoa() {
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaService.cadastrarPessoa(pessoa)).thenReturn(pessoa);

        ResponseEntity<Pessoa> responseEntity = pessoaController.cadastrarPessoa(pessoa);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(pessoa, responseEntity.getBody());
    }

    @Test
    void testListarPessoas() {
        List<Pessoa> pessoas = criarListaDePessoasFicticia();
        when(pessoaService.listarPessoas()).thenReturn(pessoas);

        ResponseEntity<List<PessoaDTO>> responseEntity = pessoaController.listarPessoas();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toListDto(pessoas), responseEntity.getBody());
    }

    @Test
    void testConsultarPessoa() {
        Long id = 1L;
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaService.consultarPessoaPorId(id)).thenReturn(pessoa);

        ResponseEntity<PessoaDTO> responseEntity = pessoaController.consultarPessoa(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toDto(pessoa), responseEntity.getBody());
    }

    @Test
    void testAtualizarPessoa() {
        Long id = 1L;
        PessoaDTO pessoaDTO = criarPessoaDtoFicticio();
        Pessoa pessoaAtualizada = criarPessoaFicticia();
        when(pessoaService.atualizarPessoa(id, pessoaDTO)).thenReturn(pessoaAtualizada);

        ResponseEntity<PessoaDTO> responseEntity = pessoaController.atualizarPessoa(id, pessoaDTO);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(toDto(pessoaAtualizada), responseEntity.getBody());
    }

    @Test
    void testExcluirPessoa() {
        Long id = 1L;
        doNothing().when(pessoaService).excluirPessoa(id);

        ResponseEntity<Void> responseEntity = pessoaController.excluirPessoa(id);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(pessoaService, times(1)).excluirPessoa(id);
    }

    private Pessoa criarPessoaFicticia() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(1L);
        pessoa.setNome("John Doe");
        pessoa.setCpf("123456789");
        pessoa.setDataNascimento(LocalDate.of(1990,1,1));
        pessoa.setFuncionario(true);
        return pessoa;
    }

    private PessoaDTO criarPessoaDtoFicticio() {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "John Doe", LocalDate.of(1995, 10, 15),
                "1990-01-01",true);
        return pessoaDTO;
    }

    private List<Pessoa> criarListaDePessoasFicticia() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(criarPessoaFicticia());
        return pessoas;
    }
}
