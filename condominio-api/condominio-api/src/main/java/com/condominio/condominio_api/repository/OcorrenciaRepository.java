package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Ocorrencia;
import com.condominio.condominio_api.model.StatusOcorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    List<Ocorrencia> findByMoradorId(Long moradorId);
    List<Ocorrencia> findByStatus(StatusOcorrencia status);
}