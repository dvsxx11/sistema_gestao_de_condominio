package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Encomenda;
import com.condominio.condominio_api.model.StatusEncomenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EncomendaRepository extends JpaRepository<Encomenda, Long> {
    List<Encomenda> findByMoradorId(Long moradorId);
    List<Encomenda> findByUnidadeId(Long unidadeId);
    List<Encomenda> findByStatus(StatusEncomenda status);
    List<Encomenda> findByMoradorIdAndStatus(Long moradorId, StatusEncomenda status);
}