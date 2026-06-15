package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.model.Veiculo;
import com.condominio.condominio_api.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.condominio.condominio_api.model.Usuario;
import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
@CrossOrigin(origins = "*")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Veiculo>> listarTodos() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/meus-veiculos")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<Veiculo>> meusVeiculos(@AuthenticationPrincipal Usuario usuario) {
        Long moradorId = usuario.getMorador().getId();
        return ResponseEntity.ok(veiculoService.listarPorMorador(moradorId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Veiculo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(veiculoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<Veiculo> criar(@Valid @RequestBody Veiculo veiculo, @AuthenticationPrincipal Usuario usuario) {
        if (usuario.getRole().toString().equals("MORADOR")) {
            veiculo.setMorador(usuario.getMorador());
            veiculo.setUnidade(usuario.getMorador().getUnidade());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(veiculoService.salvar(veiculo));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<Veiculo> atualizar(@PathVariable Long id, @Valid @RequestBody Veiculo veiculo, @AuthenticationPrincipal Usuario usuario) {
        Veiculo veiculoExistente = veiculoService.buscarPorId(id);

        if (usuario.getRole().toString().equals("MORADOR") && !veiculoExistente.getMorador().getId().equals(usuario.getMorador().getId())) {
            throw new RuntimeException("Você só pode editar seus próprios veículos");
        }

        return ResponseEntity.ok(veiculoService.atualizar(id, veiculo));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Veiculo veiculo = veiculoService.buscarPorId(id);

        if (usuario.getRole().toString().equals("MORADOR") && !veiculo.getMorador().getId().equals(usuario.getMorador().getId())) {
            throw new RuntimeException("Você só pode deletar seus próprios veículos");
        }

        veiculoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}