package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Encomenda;
import com.condominio.condominio_api.model.Morador;
import com.condominio.condominio_api.model.StatusEncomenda;
import com.condominio.condominio_api.repository.EncomendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private MoradorService moradorService;

    public List<Encomenda> listarTodas() {
        return encomendaRepository.findAll();
    }

    public Encomenda buscarPorId(Long id) {
        return encomendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Encomenda não encontrada"));
    }

    public List<Encomenda> listarPorMorador(Long moradorId) {
        return encomendaRepository.findByMoradorId(moradorId);
    }

    public List<Encomenda> listarPorUnidade(Long unidadeId) {
        return encomendaRepository.findByUnidadeId(unidadeId);
    }

    public List<Encomenda> listarPorStatus(StatusEncomenda status) {
        return encomendaRepository.findByStatus(status);
    }

    @Transactional
    public Encomenda registrarEncomenda(Encomenda encomenda, Long moradorId) {
        Morador morador = moradorService.buscarPorId(moradorId);
        encomenda.setMorador(morador);
        encomenda.setUnidade(morador.getUnidade());
        encomenda.setDataRecebimento(LocalDateTime.now());
        encomenda.setStatus(StatusEncomenda.RECEBIDA);
        return encomendaRepository.save(encomenda);
    }

    @Transactional
    public Encomenda confirmarRetirada(Long encomendaId, Long moradorId) {
        Encomenda encomenda = buscarPorId(encomendaId);

        if (!encomenda.getMorador().getId().equals(moradorId)) {
            throw new RuntimeException("Esta encomenda não pertence a este morador");
        }

        if (encomenda.getStatus() != StatusEncomenda.RECEBIDA) {
            throw new RuntimeException("Encomenda já foi retirada ou extraviada");
        }

        encomenda.setStatus(StatusEncomenda.RETIRADA);
        encomenda.setDataRetirada(LocalDateTime.now());

        return encomendaRepository.save(encomenda);
    }

    @Transactional
    public Encomenda atualizarStatus(Long id, StatusEncomenda status) {
        Encomenda encomenda = buscarPorId(id);
        encomenda.setStatus(status);

        if (status == StatusEncomenda.RETIRADA) {
            encomenda.setDataRetirada(LocalDateTime.now());
        }

        return encomendaRepository.save(encomenda);
    }

    @Transactional
    public void deletar(Long id) {
        encomendaRepository.deleteById(id);
    }
}