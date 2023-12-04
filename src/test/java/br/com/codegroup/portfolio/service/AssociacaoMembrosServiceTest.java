package br.com.codegroup.portfolio.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.codegroup.portfolio.repository.MembrosRepository;
import br.com.codegroup.portfolio.repository.PessoaRepository;

@SpringBootTest
class AssociacaoMembrosServiceTest {

    @Mock
    private MembrosRepository membrosRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @InjectMocks
    private AssociacaoMembrosService associacaoMembrosService;

    @Test
    void associarMembroAoProjeto_PessoaFuncionario_AssociacaoBemSucedida() {
        Long idMembro = 1L;
        Long idProjeto = 2L;

        Mockito.when(pessoaRepository.existsByIdAndFuncionarioTrue(idMembro)).thenReturn(true);

        associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto);

        Mockito.verify(membrosRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void associarMembroAoProjeto_PessoaNaoFuncionario_ExcecaoLancada() {
        Long idMembro = 1L;
        Long idProjeto = 2L;

        Mockito.when(pessoaRepository.existsByIdAndFuncionarioTrue(idMembro)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto));

        Mockito.verify(membrosRepository, Mockito.never()).save(Mockito.any());
    }
}