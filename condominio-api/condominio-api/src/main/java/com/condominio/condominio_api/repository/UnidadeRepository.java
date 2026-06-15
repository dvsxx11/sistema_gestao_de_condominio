package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
    List<Unidade> findByCondominioId(Long condominioId);
}