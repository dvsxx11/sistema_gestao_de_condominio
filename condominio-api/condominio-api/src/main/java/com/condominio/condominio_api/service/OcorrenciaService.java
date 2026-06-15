package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Morador;
import com.condominio.condominio_api.model.Ocorrencia;
import com.condominio.condominio_api.model.StatusOcorrencia;
import com.condominio.condominio_api.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private MoradorService moradorService;

    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    public Ocorrencia buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ocorrência não encontrada"));
    }

    public List<Ocorrencia> listarPorMorador(Long moradorId) {
        return ocorrenciaRepository.findByMoradorId(moradorId);
    }

    public List<Ocorrencia> listarPorStatus(StatusOcorrencia status) {
        return ocorrenciaRepository.findByStatus(status);
    }

    @Transactional
    public Ocorrencia salvar(Ocorrencia ocorrencia, Long moradorId) {
        Morador morador = moradorService.buscarPorId(moradorId);
        ocorrencia.setMorador(morador);
        ocorrencia.setUnidade(morador.getUnidade());
        ocorrencia.setStatus(StatusOcorrencia.ABERTA);
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public Ocorrencia atualizarStatus(Long id, StatusOcorrencia status) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrencia.setStatus(status);
        return ocorrenciaRepository.save(ocorrencia);
    }

    @Transactional
    public void deletar(Long id) {
        ocorrenciaRepository.deleteById(id);
    }
}