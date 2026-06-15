package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.AreaComum;
import com.condominio.condominio_api.model.Morador;
import com.condominio.condominio_api.model.ReservaAreaComum;
import com.condominio.condominio_api.model.StatusReserva;
import com.condominio.condominio_api.repository.ReservaAreaComumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaAreaComumRepository reservaRepository;

    @Autowired
    private AreaComumService areaComumService;

    @Autowired
    private MoradorService moradorService;

    public List<ReservaAreaComum> listarTodas() {
        return reservaRepository.findAll();
    }

    public ReservaAreaComum buscarPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    public List<ReservaAreaComum> listarPorMorador(Long moradorId) {
        return reservaRepository.findByMoradorId(moradorId);
    }

    public boolean verificarDisponibilidade(Long areaId, LocalDateTime inicio, LocalDateTime fim) {
        List<ReservaAreaComum> reservas = reservaRepository.findByAreaComumIdAndDataHoraInicioBetween(areaId, inicio, fim);
        return reservas.isEmpty();
    }

    @Transactional
    public ReservaAreaComum salvar(ReservaAreaComum reserva, Long areaComumId, Long moradorId) {
        AreaComum area = areaComumService.buscarPorId(areaComumId);
        Morador morador = moradorService.buscarPorId(moradorId);

        if (!verificarDisponibilidade(areaComumId, reserva.getDataHoraInicio(), reserva.getDataHoraFim())) {
            throw new RuntimeException("Este horário já está reservado");
        }

        reserva.setAreaComum(area);
        reserva.setMorador(morador);
        reserva.setUnidade(morador.getUnidade());
        reserva.setStatus(StatusReserva.PENDENTE);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public ReservaAreaComum atualizarStatus(Long id, StatusReserva status) {
        ReservaAreaComum reserva = buscarPorId(id);
        reserva.setStatus(status);
        return reservaRepository.save(reserva);
    }

    @Transactional
    public ReservaAreaComum cancelarReserva(Long id) {
        return atualizarStatus(id, StatusReserva.CANCELADA);
    }

    @Transactional
    public void deletar(Long id) {
        reservaRepository.deleteById(id);
    }
}