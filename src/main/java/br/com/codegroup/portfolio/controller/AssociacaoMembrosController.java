package br.com.codegroup.portfolio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.codegroup.portfolio.service.AssociacaoMembrosService;

@RestController
@RequestMapping("/membros")
public class AssociacaoMembrosController {

    @Autowired
    private AssociacaoMembrosService associacaoMembrosService;

    @PostMapping("/associar")
    public ResponseEntity<String> associarMembroAoProjeto(
            @RequestParam Long idMembro,
            @RequestParam Long idProjeto) {

        associacaoMembrosService.associarMembroAoProjeto(idMembro, idProjeto);
        return new ResponseEntity<>("Associação realizada com sucesso", HttpStatus.OK);
    }
}