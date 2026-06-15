package com.condominio.condominio_api.repository;

import com.condominio.condominio_api.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {
    Optional<Morador> findByCpf(String cpf);
    Optional<Morador> findByEmail(String email);
    List<Morador> findByUnidadeId(Long unidadeId);
    List<Morador> findByAtivoTrue();
}