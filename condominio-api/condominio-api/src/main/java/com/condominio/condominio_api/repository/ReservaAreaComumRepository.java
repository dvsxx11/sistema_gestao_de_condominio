package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.ReservaAreaComum;
import com.condominio.condominio_api.model.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaAreaComumRepository extends JpaRepository<ReservaAreaComum, Long> {
    List<ReservaAreaComum> findByMoradorId(Long moradorId);
    List<ReservaAreaComum> findByAreaComumIdAndDataHoraInicioBetween(Long areaId, LocalDateTime inicio, LocalDateTime fim);
    List<ReservaAreaComum> findByStatus(StatusReserva status);
}