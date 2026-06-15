package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.model.Unidade;
import com.condominio.condominio_api.service.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/unidades")
@CrossOrigin(origins = "*")
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Unidade>> listarTodos() {
        return ResponseEntity.ok(unidadeService.listarTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(unidadeService.buscarPorId(id));
    }

    @GetMapping("/condominio/{condominioId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Unidade>> listarPorCondominio(@PathVariable Long condominioId) {
        return ResponseEntity.ok(unidadeService.listarPorCondominio(condominioId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> criar(@Valid @RequestBody Unidade unidade) {
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeService.salvar(unidade));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Unidade> atualizar(@PathVariable Long id, @Valid @RequestBody Unidade unidade) {
        return ResponseEntity.ok(unidadeService.atualizar(id, unidade));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        unidadeService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}