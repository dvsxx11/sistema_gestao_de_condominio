package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.dto.OcorrenciaRequestDTO;
import com.condominio.condominio_api.dto.OcorrenciaResponseDTO;
import com.condominio.condominio_api.model.Ocorrencia;
import com.condominio.condominio_api.model.StatusOcorrencia;
import com.condominio.condominio_api.model.TipoOcorrencia;
import com.condominio.condominio_api.model.Usuario;
import com.condominio.condominio_api.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ocorrencias")
@CrossOrigin(origins = "*")
public class OcorrenciaController {

    @Autowired
    private OcorrenciaService ocorrenciaService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OcorrenciaResponseDTO>> listarTodas() {
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarTodas();
        List<OcorrenciaResponseDTO> response = ocorrencias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/minhas")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<List<OcorrenciaResponseDTO>> listarMinhasOcorrencias(@AuthenticationPrincipal Usuario usuario) {
        Long moradorId = usuario.getMorador().getId();
        List<Ocorrencia> ocorrencias = ocorrenciaService.listarPorMorador(moradorId);
        List<OcorrenciaResponseDTO> response = ocorrencias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MORADOR')")
    public ResponseEntity<OcorrenciaResponseDTO> buscarPorId(@PathVariable Long id, @AuthenticationPrincipal Usuario usuario) {
        Ocorrencia ocorrencia = ocorrenciaService.buscarPorId(id);

        if (usuario.getRole().toString().equals("MORADOR") && !ocorrencia.getMorador().getId().equals(usuario.getMorador().getId())) {
            throw new RuntimeException("Você só pode visualizar suas próprias ocorrências");
        }

        return ResponseEntity.ok(convertToDTO(ocorrencia));
    }

    @PostMapping
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<OcorrenciaResponseDTO> criarOcorrencia(@Valid @RequestBody OcorrenciaRequestDTO request, @AuthenticationPrincipal Usuario usuario) {
        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTitulo(request.getTitulo());
        ocorrencia.setDescricao(request.getDescricao());
        ocorrencia.setDataOcorrencia(request.getDataOcorrencia() != null ? request.getDataOcorrencia() : LocalDateTime.now());
        ocorrencia.setTipo(TipoOcorrencia.valueOf(request.getTipo()));
        ocorrencia.setStatus(StatusOcorrencia.ABERTA);

        Ocorrencia novaOcorrencia = ocorrenciaService.salvar(ocorrencia, usuario.getMorador().getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(novaOcorrencia));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OcorrenciaResponseDTO> atualizarStatus(@PathVariable Long id, @RequestParam StatusOcorrencia status) {
        Ocorrencia ocorrencia = ocorrenciaService.atualizarStatus(id, status);
        return ResponseEntity.ok(convertToDTO(ocorrencia));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ocorrenciaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private OcorrenciaResponseDTO convertToDTO(Ocorrencia ocorrencia) {
        OcorrenciaResponseDTO dto = new OcorrenciaResponseDTO();
        dto.setId(ocorrencia.getId());
        dto.setTitulo(ocorrencia.getTitulo());
        dto.setDescricao(ocorrencia.getDescricao());
        dto.setDataOcorrencia(ocorrencia.getDataOcorrencia());
        dto.setTipo(ocorrencia.getTipo().toString());
        dto.setStatus(ocorrencia.getStatus().toString());
        dto.setMoradorNome(ocorrencia.getMorador().getNome());
        return dto;
    }
}