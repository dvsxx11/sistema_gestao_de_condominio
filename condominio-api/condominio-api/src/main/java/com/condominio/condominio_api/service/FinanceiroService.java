package com.condominio.condominio_api.service;

import com.condominio.condominio_api.model.Despesa;
import com.condominio.condominio_api.model.Receita;
import com.condominio.condominio_api.repository.DespesaRepository;
import com.condominio.condominio_api.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class FinanceiroService {

    @Autowired
    private DespesaRepository despesaRepository;

    @Autowired
    private ReceitaRepository receitaRepository;

    public List<Despesa> listarDespesas() {
        return despesaRepository.findAll();
    }

    public Despesa buscarDespesaPorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));
    }

    @Transactional
    public Despesa salvarDespesa(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    @Transactional
    public Despesa atualizarDespesa(Long id, Despesa despesaAtualizada) {
        Despesa despesa = buscarDespesaPorId(id);
        despesa.setDescricao(despesaAtualizada.getDescricao());
        despesa.setValor(despesaAtualizada.getValor());
        despesa.setDataVencimento(despesaAtualizada.getDataVencimento());
        despesa.setDataPagamento(despesaAtualizada.getDataPagamento());
        despesa.setCategoria(despesaAtualizada.getCategoria());
        despesa.setStatus(despesaAtualizada.getStatus());
        despesa.setFornecedor(despesaAtualizada.getFornecedor());
        despesa.setDocumento(despesaAtualizada.getDocumento());
        return despesaRepository.save(despesa);
    }

    @Transactional
    public void deletarDespesa(Long id) {
        despesaRepository.deleteById(id);
    }

    public List<Receita> listarReceitas() {
        return receitaRepository.findAll();
    }

    public Receita buscarReceitaPorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    @Transactional
    public Receita salvarReceita(Receita receita) {
        return receitaRepository.save(receita);
    }

    @Transactional
    public Receita atualizarReceita(Long id, Receita receitaAtualizada) {
        Receita receita = buscarReceitaPorId(id);
        receita.setDescricao(receitaAtualizada.getDescricao());
        receita.setValor(receitaAtualizada.getValor());
        receita.setDataRecebimento(receitaAtualizada.getDataRecebimento());
        receita.setTipo(receitaAtualizada.getTipo());
        return receitaRepository.save(receita);
    }

    @Transactional
    public void deletarReceita(Long id) {
        receitaRepository.deleteById(id);
    }

    public Double calcularSaldo() {
        BigDecimal totalReceitas = receitaRepository.findAll().stream()
                .map(Receita::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDespesas = despesaRepository.findAll().stream()
                .map(Despesa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalReceitas.subtract(totalDespesas).doubleValue();
    }
}