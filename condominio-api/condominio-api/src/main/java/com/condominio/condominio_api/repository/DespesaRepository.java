package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Despesa;
import com.condominio.condominio_api.model.StatusDespesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    List<Despesa> findByCondominioId(Long condominioId);
    List<Despesa> findByStatus(StatusDespesa status);
    List<Despesa> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);
}