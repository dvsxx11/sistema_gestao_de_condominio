package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.AreaComum;
import com.condominio.condominio_api.repository.AreaComumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AreaComumService {

    @Autowired
    private AreaComumRepository areaComumRepository;

    public List<AreaComum> listarTodas() {
        return areaComumRepository.findAll();
    }

    public AreaComum buscarPorId(Long id) {
        return areaComumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Área comum não encontrada"));
    }

    public List<AreaComum> listarPorCondominio(Long condominioId) {
        return areaComumRepository.findByCondominioId(condominioId);
    }

    public List<AreaComum> listarAtivas() {
        return areaComumRepository.findByAtivaTrue();
    }

    @Transactional
    public AreaComum salvar(AreaComum areaComum) {
        return areaComumRepository.save(areaComum);
    }

    @Transactional
    public AreaComum atualizar(Long id, AreaComum areaAtualizada) {
        AreaComum area = buscarPorId(id);
        area.setNome(areaAtualizada.getNome());
        area.setDescricao(areaAtualizada.getDescricao());
        area.setCapacidadeMaxima(areaAtualizada.getCapacidadeMaxima());
        area.setValorLocacao(areaAtualizada.getValorLocacao());
        area.setPrecisaAgendamento(areaAtualizada.getPrecisaAgendamento());
        area.setAtiva(areaAtualizada.getAtiva());
        return areaComumRepository.save(area);
    }

    @Transactional
    public void deletar(Long id) {
        areaComumRepository.deleteById(id);
    }
}