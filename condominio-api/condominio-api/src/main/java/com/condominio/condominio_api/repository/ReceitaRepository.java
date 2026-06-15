package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    List<Receita> findByCondominioId(Long condominioId);
    List<Receita> findByDataRecebimentoBetween(LocalDate inicio, LocalDate fim);
}