package com.example.demo.service;

import java.util.Optional;

import com.example.demo.domain.Pedido;
import com.example.demo.enums.StatusPedido;
import com.example.demo.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO pedido);
    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizarPedido(Integer id, StatusPedido novoStatus);
}
