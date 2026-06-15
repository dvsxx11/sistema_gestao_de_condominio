package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Veiculo;
import com.condominio.condominio_api.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public List<Veiculo> listarPorMorador(Long moradorId) {
        return veiculoRepository.findByMoradorId(moradorId);
    }

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        if (veiculo.getPlaca() != null && veiculoRepository.findByPlaca(veiculo.getPlaca()).isPresent()) {
            throw new RuntimeException("Já existe um veículo com esta placa");
        }
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public Veiculo atualizar(Long id, Veiculo veiculoAtualizado) {
        Veiculo veiculo = buscarPorId(id);
        veiculo.setPlaca(veiculoAtualizado.getPlaca());
        veiculo.setModelo(veiculoAtualizado.getModelo());
        veiculo.setMarca(veiculoAtualizado.getMarca());
        veiculo.setCor(veiculoAtualizado.getCor());
        veiculo.setAno(veiculoAtualizado.getAno());
        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }
}