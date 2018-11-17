package br.com.helpdev.springjwtauthjdbc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ApiSecurityController {

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Sem necessidade de autenticação");
    }


}
