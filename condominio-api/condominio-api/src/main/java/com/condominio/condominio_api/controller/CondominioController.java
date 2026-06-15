package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.model.Condominio;
import com.condominio.condominio_api.service.CondominioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/condominios")
@CrossOrigin(origins = "*")
public class CondominioController {

    @Autowired
    private CondominioService condominioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Condominio>> listarTodos() {
        return ResponseEntity.ok(condominioService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Condominio> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(condominioService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Condominio> criar(@Valid @RequestBody Condominio condominio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(condominioService.salvar(condominio));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Condominio> atualizar(@PathVariable Long id, @Valid @RequestBody Condominio condominio) {
        return ResponseEntity.ok(condominioService.atualizar(id, condominio));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        condominioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}