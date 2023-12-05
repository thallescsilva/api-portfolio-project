package br.com.codegroup.portfolio.controller;

import br.com.codegroup.portfolio.model.dto.PessoaDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.codegroup.portfolio.util.PessoaMapper.toDto;
import static br.com.codegroup.portfolio.util.PessoaMapper.toListDto;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarPessoa(@RequestBody Pessoa pessoa) {
        Pessoa pessoaCadastrada = pessoaService.cadastrarPessoa(pessoa);
        return new ResponseEntity<>(pessoaCadastrada, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PessoaDTO>> listarPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return new ResponseEntity<>(toListDto(pessoas), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> consultarPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.consultarPessoaPorId(id);
        return new ResponseEntity<>(toDto(pessoa), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PessoaDTO> atualizarPessoa(@PathVariable Long id, @RequestBody PessoaDTO pessoaDTO) {
        Pessoa pessoaAtualizada = pessoaService.atualizarPessoa(id, pessoaDTO);
        return new ResponseEntity<>(toDto(pessoaAtualizada), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPessoa(@PathVariable Long id) {
        pessoaService.excluirPessoa(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
