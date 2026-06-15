package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Unidade;
import com.condominio.condominio_api.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeRepository unidadeRepository;

    public List<Unidade> listarTodos() {
        return unidadeRepository.findAll();
    }

    public Unidade buscarPorId(Long id) {
        return unidadeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));
    }

    public List<Unidade> listarPorCondominio(Long condominioId) {
        return unidadeRepository.findByCondominioId(condominioId);
    }

    @Transactional
    public Unidade salvar(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    @Transactional
    public Unidade atualizar(Long id, Unidade unidadeAtualizada) {
        Unidade unidade = buscarPorId(id);
        unidade.setNumero(unidadeAtualizada.getNumero());
        unidade.setBloco(unidadeAtualizada.getBloco());
        unidade.setAndar(unidadeAtualizada.getAndar());
        unidade.setArea(unidadeAtualizada.getArea());
        unidade.setQuartos(unidadeAtualizada.getQuartos());
        unidade.setVagasGaragem(unidadeAtualizada.getVagasGaragem());
        unidade.setCondominio(unidadeAtualizada.getCondominio());
        return unidadeRepository.save(unidade);
    }

    @Transactional
    public void deletar(Long id) {
        unidadeRepository.deleteById(id);
    }
}