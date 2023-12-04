package br.com.codegroup.portfolio.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codegroup.portfolio.model.dto.ProjetoDTO;
import br.com.codegroup.portfolio.model.entity.Projeto;
import br.com.codegroup.portfolio.service.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ProjetoDTO> cadastrarProjeto(@RequestBody ProjetoDTO projetoDTO) {
        Projeto projeto = modelMapper.map(projetoDTO, Projeto.class);
        projeto = projetoService.cadastrarProjeto(projeto);
        ProjetoDTO projetoCadastradoDTO = modelMapper.map(projeto, ProjetoDTO.class);
        return new ResponseEntity<>(projetoCadastradoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Projeto> atualizarProjeto(@PathVariable Long id, @RequestBody Projeto projeto) {
        Projeto projetoAtualizado = projetoService.atualizarProjeto(id, projeto);
        return ResponseEntity.ok(projetoAtualizado);
    }

    @GetMapping
    public ResponseEntity<List<ProjetoDTO>> listarProjetos() {
        List<Projeto> projetos = projetoService.listarProjetos();
        List<ProjetoDTO> projetoDTOs = modelMapper.map(projetos, new TypeToken<List<ProjetoDTO>>() {}.getType());
        return new ResponseEntity<>(projetoDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjetoDTO> consultarProjeto(@PathVariable Long id) {
        Projeto projeto = projetoService.consultarProjetoPorId(id);
        if (projeto != null) {
            ProjetoDTO projetoDTO = modelMapper.map(projeto, ProjetoDTO.class);
            return new ResponseEntity<>(projetoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProjeto(@PathVariable Long id) {
        projetoService.excluirProjeto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
