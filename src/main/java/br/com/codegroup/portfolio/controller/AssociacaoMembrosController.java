package br.com.codegroup.portfolio.controller;

import br.com.codegroup.portfolio.model.ProjetoMembrosResponse;
import br.com.codegroup.portfolio.service.AssociacaoMembrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/membros")
public class AssociacaoMembrosController {

    @Autowired
    private AssociacaoMembrosService associacaoMembrosService;

    @GetMapping
    public ResponseEntity<List<ProjetoMembrosResponse>> listarAssociacoes() {
        List<ProjetoMembrosResponse> membrosResponse = associacaoMembrosService.listarAssociacoes();
        return new ResponseEntity<>(membrosResponse, HttpStatus.OK);
    }

    @PostMapping("/associar")
    public ResponseEntity<String> associarMembroAoProjeto(
            @RequestParam Long idMembro,
            @RequestParam Long idProjeto) {

        associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto);
        return new ResponseEntity<>("Associação realizada com sucesso", HttpStatus.OK);
    }
}