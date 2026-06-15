package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Morador;
import com.condominio.condominio_api.repository.MoradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MoradorService {

    @Autowired
    private MoradorRepository moradorRepository;

    public List<Morador> listarTodos() {
        return moradorRepository.findAll();
    }

    public Morador buscarPorId(Long id) {
        return moradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Morador não encontrado"));
    }

    public boolean existsByCpf(String cpf) {
        return moradorRepository.findByCpf(cpf).isPresent();
    }

    public boolean existsByEmail(String email) {
        return moradorRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public Morador salvar(Morador morador) {
        return moradorRepository.save(morador);
    }

    @Transactional
    public void deletar(Long id) {
        Morador morador = buscarPorId(id);
        morador.setAtivo(false);
        moradorRepository.save(morador);
    }
}