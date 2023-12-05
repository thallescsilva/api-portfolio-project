package br.com.codegroup.portfolio.controller;

import br.com.codegroup.portfolio.model.ProjetoResponse;
import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Pessoa;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.service.PessoaService;
import br.com.codegroup.portfolio.service.ProjetoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.codegroup.portfolio.util.ProjetoMapper.*;


@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    private final PessoaService pessoaService;

    public ProjetoController(ProjetoService projetoService, PessoaService pessoaService) {
        this.projetoService = projetoService;
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<ProjetoResponse> cadastrarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        Projeto projeto = toEntity(projetoDTO);

        Pessoa gerente = pessoaService.consultarPessoaPorId(projetoDTO.idGerente());
        projeto.setGerente(gerente);

        projeto = projetoService.cadastrarProjeto(projeto);

        return new ResponseEntity<>(toDto(projeto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjetoResponse> atualizarProjeto(@PathVariable Long id, @RequestBody ProjetoDTO projetoDTO) {
        Projeto projetoAtualizado = projetoService.atualizarProjeto(id, projetoDTO);
        return ResponseEntity.ok(toDto(projetoAtualizado));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponse>> listarProjetos() {
        List<Projeto> projetos = projetoService.listarProjetos();
        return new ResponseEntity<>(toListDto(projetos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponse> consultarProjeto(@PathVariable Long id) {
        Projeto projeto = projetoService.consultarProjetoPorId(id);
        return new ResponseEntity<>(toDto(projeto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProjeto(@PathVariable Long id) {
        projetoService.excluirProjeto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
