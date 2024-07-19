package com.example.demo.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Cliente;
import com.example.demo.domain.ItemPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Produto;
import com.example.demo.enums.StatusPedido;
import com.example.demo.exception.PedidoNaoEncontradoException;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.repository.ClientesJpa;
import com.example.demo.repository.ItemPedidoJpa;
import com.example.demo.repository.PedidoJpa;
import com.example.demo.repository.ProdutoJpa;
import com.example.demo.rest.dto.ItensPedidoDTO;
import com.example.demo.rest.dto.PedidoDTO;
import com.example.demo.service.PedidoService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoJpa pedidoRepository;
    private final ItemPedidoJpa itemPedidoRepository;
    private final ClientesJpa clienteRepository;
    private final ProdutoJpa produtoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedido) {

        Cliente c = clienteRepository.findById(pedido.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado " + pedido.getCliente()));

        Pedido p = new Pedido();
        p.setCliente(c);
        p.setStatus(StatusPedido.REALIZADO);
        p.setTotal(pedido.getTotal());
        p.setDataPedido(LocalDate.now());
        List<ItemPedido> ip = this.converterItens(p, pedido.getItens());
        pedidoRepository.save(p);
        itemPedidoRepository.saveAll(ip);
        p.setItemsPedido(ip);
        return p;
    }

    private List<ItemPedido> converterItens(Pedido pedido, List<ItensPedidoDTO> itens) {
        if (itens.isEmpty()) {
            throw new RegraNegocioException("Lista de itens do pedido vazia");
        }

        return itens.stream()
                .map(dto -> {
                    Produto produto = produtoRepository.findById(dto.getProduto())
                            .orElseThrow(() -> new RegraNegocioException(
                                    "Produto não encontrado com o código: " + dto.getProduto()));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);

                    return itemPedido; // Retorna o ItemPedido criado
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

   
    @Override
    public void atualizarPedido(Integer id, StatusPedido statusNovo) {
       pedidoRepository.findById(id)
                .map(p -> {
                    p.setStatus(statusNovo);
                    pedidoRepository.save(p);
                    return p;
                })
                .orElseThrow(() -> new PedidoNaoEncontradoException("Pedido não encontrado"));

       
    }

}
