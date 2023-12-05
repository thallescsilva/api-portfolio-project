package br.com.codegroup.portfolio.service;

import br.com.codegroup.portfolio.exception.RegraDeNegocioException;
import br.com.codegroup.portfolio.model.ProjetoMembrosResponse;
import br.com.codegroup.portfolio.model.entity.MembroId;
import br.com.codegroup.portfolio.model.entity.Membros;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.repository.MembrosRepository;
import br.com.codegroup.portfolio.util.enumeration.RiscoProjeto;
import br.com.codegroup.portfolio.util.enumeration.StatusProjeto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AssociacaoMembrosServiceTest {

    @InjectMocks
    private AssociacaoMembrosService associacaoMembrosService;

    @Mock
    private MembrosRepository membrosRepository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private ProjetoService projetoService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void associarMembroAoProjeto_PessoaEFuncionario_AssociacaoFeita() {
        Long idMembro = 1L;
        Long idProjeto = 1L;
        MembroId membroId = new MembroId(idProjeto, idMembro);
        Membros membro = new Membros();
        membro.setId(membroId);

        when(pessoaService.pessoaEfuncionario(idMembro)).thenReturn(true);
        when(membrosRepository.save(any())).thenReturn(membro);

        assertDoesNotThrow(() -> associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto));

        verify(membrosRepository, times(1)).save(any());
    }

    @Test
    void associarMembroAoProjeto_PessoaNaoEFuncionario_RegraDeNegocioExceptionLancada() {
        Long idMembro = 1L;
        Long idProjeto = 1L;

        when(pessoaService.pessoaEfuncionario(idMembro)).thenReturn(false);

        RegraDeNegocioException exception = assertThrows(
                RegraDeNegocioException.class,
                () -> associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto)
        );
        assertEquals("Apenas funcionários podem ser associados ao projeto.", exception.getMessage());

        verifyNoInteractions(membrosRepository);
    }

    @Test
    void listarAssociacoes_ProjetosComMembros_RetornaListaDeAssociacoes() {
        List<Projeto> projetos = criarProjetosComMembros();
        List<Membros> membros = criarMembros();
        Pessoa pessoa = new Pessoa(1L, "João Batista", LocalDate.of(1994, 10, 5), "123.123.123-12", true);

        when(projetoService.listarProjetos()).thenReturn(projetos);
        when(membrosRepository.existsById_IdProjeto(anyLong())).thenReturn(true);
        when(membrosRepository.findById_IdProjeto(anyLong())).thenReturn(membros);
        when(pessoaService.consultarPessoaPorId(anyLong())).thenReturn(pessoa);

        List<ProjetoMembrosResponse> resultado = associacaoMembrosService.listarAssociacoes();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(projetos.size(), resultado.size());
    }

    @Test
    void listarAssociacoes_ProjetosSemMembros_RetornaListaVazia() {
        List<Projeto> projetos = criarProjetosSemMembros();

        when(projetoService.listarProjetos()).thenReturn(projetos);
        when(membrosRepository.existsById_IdProjeto(anyLong())).thenReturn(false);

        List<ProjetoMembrosResponse> resultado = associacaoMembrosService.listarAssociacoes();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    private List<Projeto> criarProjetosComMembros() {
        Pessoa gerente1 = new Pessoa(1L, "João Gerente", LocalDate.of(1980, 1, 1), "123.456.789-00", true);
        Projeto projeto1 = new Projeto(1L, "Projeto A", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31),
                null, "Descrição do Projeto A", StatusProjeto.INICIADO, 100000.0F, RiscoProjeto.BAIXO, gerente1);

        return Collections.singletonList(projeto1);
    }

    private List<Projeto> criarProjetosSemMembros() {
        Pessoa gerente2 = new Pessoa(3L, "Ana Gerente", LocalDate.of(1975, 3, 10), "111.222.333-44", true);

        Projeto projeto2 = new Projeto(2L, "Projeto B", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 12, 31),
                null, "Descrição do Projeto B", StatusProjeto.INICIADO, 80000.0F, RiscoProjeto.MEDIO, gerente2);

        return Collections.singletonList(projeto2);
    }

    private List<Membros> criarMembros() {
        Pessoa membro2 = new Pessoa(4L, "Carlos Membro", LocalDate.of(1985, 8, 20), "555.666.777-88", true);
        Pessoa membro3 = new Pessoa(5L, "Juliana Membro", LocalDate.of(1992, 6, 5), "999.888.777-66", true);

        Projeto projeto3 = new Projeto(3L, "Projeto C", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 11, 30),
                null, "Descrição do Projeto C", StatusProjeto.INICIADO, 120000.0F, RiscoProjeto.ALTO, null);

        Membros associacao2 = new Membros(new MembroId(projeto3.getId(), membro2.getId()));
        Membros associacao3 = new Membros(new MembroId(projeto3.getId(), membro3.getId()));

        return Arrays.asList(associacao2, associacao3);
    }
}