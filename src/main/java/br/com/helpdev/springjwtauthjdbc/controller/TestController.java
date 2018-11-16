package br.com.helpdev.springjwtauthjdbc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("Sem necessidade de autenticação");
    }

    @GetMapping("/home")
    public ResponseEntity<String> getHome() {
        return ResponseEntity.ok("Somente com autenticação");
    }

}
