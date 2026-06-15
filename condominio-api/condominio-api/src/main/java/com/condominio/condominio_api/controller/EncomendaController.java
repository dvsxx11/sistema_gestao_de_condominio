package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.dto.EncomendaRequestDTO;
import com.condominio.condominio_api.dto.EncomendaResponseDTO;
import com.condominio.condominio_api.model.Encomenda;
import com.condominio.condominio_api.model.StatusEncomenda;
import com.condominio.condominio_api.model.Usuario;
import com.condominio.condominio_api.service.EncomendaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/encomendas")
@CrossOrigin(origins = "*")
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EncomendaResponseDTO>> listarTodas() {
        List<Encomenda> encomendas = encomendaService.listarTodas();
        List<EncomendaResponseDTO> response = encomendas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<EncomendaResponseDTO>> listarMinhasEncomendas(@AuthenticationPrincipal Usuario usuario) {
        Long moradorId = usuario.getMorador().getId();
        List<Encomenda> encomendas = encomendaService.listarPorMorador(moradorId);
        List<EncomendaResponseDTO> response = encomendas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/minha-unidade")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<EncomendaResponseDTO>> listarEncomendasMinhaUnidade(@AuthenticationPrincipal Usuario usuario) {
        Long unidadeId = usuario.getMorador().getUnidade().getId();
        List<Encomenda> encomendas = encomendaService.listarPorUnidade(unidadeId);
        List<EncomendaResponseDTO> response = encomendas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/retirar")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<EncomendaResponseDTO> retirarEncomenda(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Long moradorId = usuario.getMorador().getId();
        Encomenda encomenda = encomendaService.confirmarRetirada(id, moradorId);
        return ResponseEntity.ok(convertToDTO(encomenda));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EncomendaResponseDTO> registrarEncomenda(@Valid @RequestBody EncomendaRequestDTO request) {
        Encomenda encomenda = new Encomenda();
        encomenda.setCodigoRastreio(request.getCodigoRastreio());
        encomenda.setTransportadora(request.getTransportadora());
        encomenda.setObservacao(request.getObservacao());

        Encomenda novaEncomenda = encomendaService.registrarEncomenda(encomenda, request.getMoradorId());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(novaEncomenda));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EncomendaResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusEncomenda status) {
        Encomenda encomenda = encomendaService.atualizarStatus(id, status);
        return ResponseEntity.ok(convertToDTO(encomenda));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EncomendaResponseDTO> buscarPorId(@PathVariable Long id) {
        Encomenda encomenda = encomendaService.buscarPorId(id);
        return ResponseEntity.ok(convertToDTO(encomenda));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EncomendaResponseDTO>> listarPorStatus(@PathVariable StatusEncomenda status) {
        List<Encomenda> encomendas = encomendaService.listarPorStatus(status);
        List<EncomendaResponseDTO> response = encomendas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        encomendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private EncomendaResponseDTO convertToDTO(Encomenda encomenda) {
        EncomendaResponseDTO dto = new EncomendaResponseDTO();
        dto.setId(encomenda.getId());
        dto.setCodigoRastreio(encomenda.getCodigoRastreio());
        dto.setTransportadora(encomenda.getTransportadora());
        dto.setDataRecebimento(encomenda.getDataRecebimento());
        dto.setDataRetirada(encomenda.getDataRetirada());
        dto.setStatus(encomenda.getStatus().toString());
        dto.setObservacao(encomenda.getObservacao());
        dto.setMoradorNome(encomenda.getMorador() != null ? encomenda.getMorador().getNome() : null);
        dto.setUnidadeNumero(encomenda.getUnidade() != null ? encomenda.getUnidade().getNumero() : null);
        return dto;
    }
}