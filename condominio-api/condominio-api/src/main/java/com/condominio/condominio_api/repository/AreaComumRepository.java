package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.AreaComum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AreaComumRepository extends JpaRepository<AreaComum, Long> {
    List<AreaComum> findByCondominioId(Long condominioId);
    List<AreaComum> findByAtivaTrue();
}