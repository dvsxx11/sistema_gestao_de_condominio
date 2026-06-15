package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Condominio;
import com.condominio.condominio_api.repository.CondominioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CondominioService {

    @Autowired
    private CondominioRepository condominioRepository;

    public List<Condominio> listarTodos() {
        return condominioRepository.findAll();
    }

    public Condominio buscarPorId(Long id) {
        return condominioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Condomínio não encontrado"));
    }

    @Transactional
    public Condominio salvar(Condominio condominio) {
        return condominioRepository.save(condominio);
    }

    @Transactional
    public Condominio atualizar(Long id, Condominio condominioAtualizado) {
        Condominio condominio = buscarPorId(id);
        condominio.setNome(condominioAtualizado.getNome());
        condominio.setCnpj(condominioAtualizado.getCnpj());
        condominio.setEndereco(condominioAtualizado.getEndereco());
        condominio.setCidade(condominioAtualizado.getCidade());
        condominio.setEstado(condominioAtualizado.getEstado());
        condominio.setCep(condominioAtualizado.getCep());
        condominio.setTelefone(condominioAtualizado.getTelefone());
        condominio.setEmail(condominioAtualizado.getEmail());
        condominio.setTotalBlocos(condominioAtualizado.getTotalBlocos());
        condominio.setTotalUnidades(condominioAtualizado.getTotalUnidades());
        return condominioRepository.save(condominio);
    }

    @Transactional
    public void deletar(Long id) {
        condominioRepository.deleteById(id);
    }
}