package br.com.codegroup.portfolio.service;

import br.com.codegroup.portfolio.exception.PessoaNotFoundException;
import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private PessoaService pessoaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCadastrarPessoa() {
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoa);

        assertNotNull(pessoaCadastrada);
        assertEquals(pessoa.getId(), pessoaCadastrada.getId());
        assertEquals(pessoa.getNome(), pessoaCadastrada.getNome());
    }

    @Test
    void testListarPessoas() {
        List<Pessoa> pessoas = criarListaDePessoasFicticias();
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<Pessoa> listaRetornada = pessoaService.listarPessoas();

        assertNotNull(listaRetornada);
        assertEquals(pessoas.size(), listaRetornada.size());
        assertEquals(pessoas.get(0).getId(), listaRetornada.get(0).getId());
        assertEquals(pessoas.get(0).getNome(), listaRetornada.get(0).getNome());
    }

    @Test
    void testConsultarPessoaPorId_Existente() {
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        Pessoa pessoaConsultada = pessoaService.consultarPessoaPorId(1L);

        assertNotNull(pessoaConsultada);
        assertEquals(pessoa.getId(), pessoaConsultada.getId());
        assertEquals(pessoa.getNome(), pessoaConsultada.getNome());
    }

    @Test
    void testConsultarPessoaPorId_NaoExistente() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PessoaNotFoundException.class, () -> pessoaService.consultarPessoaPorId(1L));
    }

    @Test
    void testConsultarPessoaPorNome_Existente() {
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaRepository.findByNome(anyString())).thenReturn(Optional.of(pessoa));

        Pessoa pessoaConsultada = pessoaService.consultarPessoaPorNome("Nome Existente");

        assertNotNull(pessoaConsultada);
        assertEquals(pessoa.getId(), pessoaConsultada.getId());
        assertEquals(pessoa.getNome(), pessoaConsultada.getNome());
    }

    @Test
    void testConsultarPessoaPorNome_NaoExistente() {
        when(pessoaRepository.findByNome(anyString())).thenReturn(Optional.empty());

        assertThrows(PessoaNotFoundException.class, () -> pessoaService.consultarPessoaPorNome("NomeInexistente"));
    }

    @Test
    void testExcluirPessoa_Existente() {
        Pessoa pessoa = criarPessoaFicticia();
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoa));

        assertDoesNotThrow(() -> pessoaService.excluirPessoa(1L));
        verify(pessoaRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void testExcluirPessoa_NaoExistente() {
        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(PessoaNotFoundException.class, () -> pessoaService.excluirPessoa(1L));
        verify(pessoaRepository, never()).deleteById(anyLong());
    }

    @Test
    void testAtualizarPessoa() {
        Pessoa pessoaExistente = criarPessoaFicticia();
        PessoaDTO pessoaAtualizada = criarPessoaDtoFicticia();

        when(pessoaRepository.findById(anyLong())).thenReturn(Optional.of(pessoaExistente));
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaExistente);

        Pessoa pessoaAtualizadaEntidade = pessoaService.atualizarPessoa(1L, pessoaAtualizada);

        assertNotNull(pessoaAtualizadaEntidade);
        assertEquals(pessoaExistente.getId(), pessoaAtualizadaEntidade.getId());
        assertEquals(pessoaAtualizada.nome(), pessoaAtualizadaEntidade.getNome());
        assertEquals(pessoaAtualizada.cpf(), pessoaAtualizadaEntidade.getCpf());
        assertEquals(pessoaAtualizada.dataNascimento(), pessoaAtualizadaEntidade.getDataNascimento());
        assertEquals(pessoaAtualizada.funcionario(), pessoaAtualizadaEntidade.isFuncionario());
    }

    @Test
    void testPessoaEfuncionario_True() {
        when(pessoaRepository.existsByIdAndFuncionarioTrue(anyLong())).thenReturn(true);

        assertTrue(pessoaService.pessoaEfuncionario(1L));
    }

    @Test
    void testPessoaEfuncionario_False() {
        when(pessoaRepository.existsByIdAndFuncionarioTrue(anyLong())).thenReturn(false);

        assertFalse(pessoaService.pessoaEfuncionario(1L));
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

    private PessoaDTO criarPessoaDtoFicticia() {
        return new PessoaDTO(null, "Nome Atualizado",
                LocalDate.of(1980, 1, 1),
                "987.654.321-00", false);
    }

    private List<Pessoa> criarListaDePessoasFicticias() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(criarPessoaFicticia());
        return pessoas;
    }
}
