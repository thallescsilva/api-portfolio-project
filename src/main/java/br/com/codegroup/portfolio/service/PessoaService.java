package br.com.codegroup.portfolio.service;

import br.com.codegroup.portfolio.exception.PessoaNotFoundException;
import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa consultarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);
    }

    public Pessoa consultarPessoaPorNome(String nome) {
        return pessoaRepository.findByNome(nome)
                .orElseThrow(PessoaNotFoundException::new);
    }

    public void excluirPessoa(Long id) {
        pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);

        pessoaRepository.deleteById(id);
    }

    public Pessoa atualizarPessoa(Long id, PessoaDTO pessoaAtualizada) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(PessoaNotFoundException::new);

        pessoaExistente.setNome(pessoaAtualizada.nome());
        pessoaExistente.setCpf(pessoaAtualizada.cpf());
        pessoaExistente.setDataNascimento(pessoaAtualizada.dataNascimento());
        pessoaExistente.setFuncionario(pessoaAtualizada.funcionario());

        return pessoaRepository.save(pessoaExistente);
    }

    public boolean pessoaEfuncionario(Long id) {
        return pessoaRepository.existsByIdAndFuncionarioTrue(id);
    }

}
