package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.model.Despesa;
import com.condominio.condominio_api.model.Receita;
import com.condominio.condominio_api.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/financeiro")
@CrossOrigin(origins = "*")
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    @GetMapping("/despesas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Despesa>> listarDespesas() {
        return ResponseEntity.ok(financeiroService.listarDespesas());
    }

    @GetMapping("/despesas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Despesa> buscarDespesaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(financeiroService.buscarDespesaPorId(id));
    }

    @PostMapping("/despesas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Despesa> criarDespesa(@Valid @RequestBody Despesa despesa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiroService.salvarDespesa(despesa));
    }

    @PutMapping("/despesas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Despesa> atualizarDespesa(@PathVariable Long id, @Valid @RequestBody Despesa despesa) {
        return ResponseEntity.ok(financeiroService.atualizarDespesa(id, despesa));
    }

    @DeleteMapping("/despesas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id) {
        financeiroService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/receitas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Receita>> listarReceitas() {
        return ResponseEntity.ok(financeiroService.listarReceitas());
    }

    @GetMapping("/receitas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Receita> buscarReceitaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(financeiroService.buscarReceitaPorId(id));
    }

    @PostMapping("/receitas")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Receita> criarReceita(@Valid @RequestBody Receita receita) {
        return ResponseEntity.status(HttpStatus.CREATED).body(financeiroService.salvarReceita(receita));
    }

    @PutMapping("/receitas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Receita> atualizarReceita(@PathVariable Long id, @Valid @RequestBody Receita receita) {
        return ResponseEntity.ok(financeiroService.atualizarReceita(id, receita));
    }

    @DeleteMapping("/receitas/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletarReceita(@PathVariable Long id) {
        financeiroService.deletarReceita(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> calcularSaldo() {
        return ResponseEntity.ok(financeiroService.calcularSaldo());
    }
}