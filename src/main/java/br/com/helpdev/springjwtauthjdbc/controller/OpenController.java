package br.com.helpdev.springjwtauthjdbc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open")
public class OpenController {

    @GetMapping("")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("[OK - 200] GET /open");
    }


}
