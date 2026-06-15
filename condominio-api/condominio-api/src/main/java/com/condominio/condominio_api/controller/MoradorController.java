package com.condominio.condominio_api.controller;

import com.condominio.condominio_api.dto.MoradorRequestDTO;
import com.condominio.condominio_api.dto.MoradorResponseDTO;
import com.condominio.condominio_api.model.Morador;
import com.condominio.condominio_api.model.TipoMorador;
import com.condominio.condominio_api.model.Unidade;
import com.condominio.condominio_api.model.Usuario;
import com.condominio.condominio_api.model.Role;
import com.condominio.condominio_api.service.MoradorService;
import com.condominio.condominio_api.service.UnidadeService;
import com.condominio.condominio_api.service.UsuarioService;
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
@RequestMapping("/api/moradores")
@CrossOrigin(origins = "*")
public class MoradorController {

    @Autowired
    private MoradorService moradorService;

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MoradorResponseDTO>> listarTodos() {
        List<Morador> moradores = moradorService.listarTodos();
        List<MoradorResponseDTO> response = moradores.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/meu-perfil")
    @PreAuthorize("hasRole('MORADOR')")
    public ResponseEntity<MoradorResponseDTO> meuPerfil(@AuthenticationPrincipal Usuario usuario) {
        Morador morador = moradorService.buscarPorId(usuario.getMorador().getId());
        return ResponseEntity.ok(convertToDTO(morador));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MoradorResponseDTO> buscarPorId(@PathVariable Long id) {
        Morador morador = moradorService.buscarPorId(id);
        return ResponseEntity.ok(convertToDTO(morador));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MoradorResponseDTO> criarMorador(@Valid @RequestBody MoradorRequestDTO request) {

        if (moradorService.existsByCpf(request.getCpf())) {
            throw new RuntimeException("Já existe um morador com este CPF");
        }

        if (moradorService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Já existe um morador com este e-mail");
        }

        Unidade unidade = unidadeService.buscarPorId(request.getUnidadeId());

        Morador morador = new Morador();
        morador.setNome(request.getNome());
        morador.setEmail(request.getEmail());
        morador.setTelefone(request.getTelefone());
        morador.setCelular(request.getCelular());
        morador.setCpf(request.getCpf());
        morador.setRg(request.getRg());
        morador.setDataNascimento(request.getDataNascimento());
        morador.setTipo(TipoMorador.valueOf(request.getTipo()));
        morador.setAtivo(true);
        morador.setUnidade(unidade);

        Morador novoMorador = moradorService.salvar(morador);

        String username = gerarUsername(morador.getNome());
        String senhaTemporaria = gerarSenhaTemporaria();

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(morador.getEmail());
        usuario.setPassword(usuarioService.encoder().encode(senhaTemporaria));
        usuario.setRole(Role.MORADOR);
        usuario.setMorador(novoMorador);
        usuario.setAtivo(true);
        usuario.setCreatedAt(LocalDateTime.now());
        usuario.setUpdatedAt(LocalDateTime.now());

        usuarioService.salvar(usuario);

        MoradorResponseDTO response = convertToDTO(novoMorador);
        response.setSenhaTemporaria(senhaTemporaria);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MoradorResponseDTO> atualizarMorador(@PathVariable Long id, @Valid @RequestBody MoradorRequestDTO request) {
        Morador morador = moradorService.buscarPorId(id);
        morador.setNome(request.getNome());
        morador.setEmail(request.getEmail());
        morador.setTelefone(request.getTelefone());
        morador.setCelular(request.getCelular());
        morador.setCpf(request.getCpf());
        morador.setRg(request.getRg());
        morador.setDataNascimento(request.getDataNascimento());
        morador.setTipo(TipoMorador.valueOf(request.getTipo()));

        if (request.getUnidadeId() != null && !morador.getUnidade().getId().equals(request.getUnidadeId())) {
            Unidade unidade = unidadeService.buscarPorId(request.getUnidadeId());
            morador.setUnidade(unidade);
        }

        Morador moradorAtualizado = moradorService.salvar(morador);
        return ResponseEntity.ok(convertToDTO(moradorAtualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        moradorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private MoradorResponseDTO convertToDTO(Morador morador) {
        MoradorResponseDTO dto = new MoradorResponseDTO();
        dto.setId(morador.getId());
        dto.setNome(morador.getNome());
        dto.setEmail(morador.getEmail());
        dto.setTelefone(morador.getTelefone());
        dto.setCelular(morador.getCelular());
        dto.setCpf(morador.getCpf());
        dto.setTipo(morador.getTipo().toString());
        if (morador.getUnidade() != null) {
            dto.setUnidadeNumero(morador.getUnidade().getNumero());
            if (morador.getUnidade().getCondominio() != null) {
                dto.setCondominioNome(morador.getUnidade().getCondominio().getNome());
            }
        }
        return dto;
    }

    private String gerarUsername(String nome) {
        String base = nome.toLowerCase().replaceAll(" ", ".");
        String username = base;
        int contador = 1;
        while (usuarioService.existsByUsername(username)) {
            username = base + contador;
            contador++;
        }
        return username;
    }

    private String gerarSenhaTemporaria() {
        return "temp@" + (int)(Math.random() * 10000);
    }
}