package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.dto.ReservaRequestDTO;
import com.condominio.condominio_api.dto.ReservaResponseDTO;
import com.condominio.condominio_api.model.ReservaAreaComum;
import com.condominio.condominio_api.model.StatusReserva;
import com.condominio.condominio_api.model.Usuario;
import com.condominio.condominio_api.service.ReservaService;
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
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReservaResponseDTO>> listarTodas() {
        List<ReservaAreaComum> reservas = reservaService.listarTodas();
        List<ReservaResponseDTO> response = reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<ReservaResponseDTO>> listarMinhasReservas(@AuthenticationPrincipal Usuario usuario) {
        Long moradorId = usuario.getMorador().getId();
        List<ReservaAreaComum> reservas = reservaService.listarPorMorador(moradorId);
        List<ReservaResponseDTO> response = reservas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        ReservaAreaComum reserva = reservaService.buscarPorId(id);

        if (usuario.getRole().toString().equals("MORADOR") && !reserva.getMorador().getId().equals(usuario.getMorador().getId())) {
            throw new RuntimeException("Você só pode visualizar suas próprias reservas");
        }

        return ResponseEntity.ok(convertToDTO(reserva));
    }

    @PostMapping
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<ReservaResponseDTO> criarReserva(@Valid @RequestBody ReservaRequestDTO request, @AuthenticationPrincipal Usuario usuario) {
        ReservaAreaComum reserva = new ReservaAreaComum();
        reserva.setDataHoraInicio(request.getDataHoraInicio());
        reserva.setDataHoraFim(request.getDataHoraFim());
        reserva.setMotivo(request.getMotivo());

        ReservaAreaComum novaReserva = reservaService.salvar(reserva, request.getAreaComumId(), usuario.getMorador().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(novaReserva));
    }

    @PutMapping("/{id}/cancelar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<ReservaResponseDTO> cancelarReserva(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        ReservaAreaComum reserva = reservaService.buscarPorId(id);

        if (usuario.getRole().toString().equals("MORADOR") && !reserva.getMorador().getId().equals(usuario.getMorador().getId())) {
            throw new RuntimeException("Você só pode cancelar suas próprias reservas");
        }

        ReservaAreaComum reservaCancelada = reservaService.cancelarReserva(id);
        return ResponseEntity.ok(convertToDTO(reservaCancelada));
    }

    @PutMapping("/{id}/confirmar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> confirmarReserva(@PathVariable Long id) {
        ReservaAreaComum reserva = reservaService.atualizarStatus(id, StatusReserva.CONFIRMADA);
        return ResponseEntity.ok(convertToDTO(reserva));
    }

    @PutMapping("/{id}/realizar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReservaResponseDTO> realizarReserva(@PathVariable Long id) {
        ReservaAreaComum reserva = reservaService.atualizarStatus(id, StatusReserva.REALIZADA);
        return ResponseEntity.ok(convertToDTO(reserva));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private ReservaResponseDTO convertToDTO(ReservaAreaComum reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setAreaComumNome(reserva.getAreaComum().getNome());
        dto.setDataHoraInicio(reserva.getDataHoraInicio());
        dto.setDataHoraFim(reserva.getDataHoraFim());
        dto.setMotivo(reserva.getMotivo());
        dto.setStatus(reserva.getStatus().toString());
        dto.setMoradorNome(reserva.getMorador().getNome());
        dto.setUnidadeNumero(reserva.getUnidade().getNumero());
        return dto;
    }
}