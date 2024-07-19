package com.example.demo.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.ItemPedido;
import com.example.demo.domain.Pedido;
import com.example.demo.enums.StatusPedido;
import com.example.demo.rest.dto.AtualizacaoStatusPedidoDTO;
import com.example.demo.rest.dto.InfoItensPedidoDTO;
import com.example.demo.rest.dto.InfoPedidoDTO;
import com.example.demo.rest.dto.PedidoDTO;
import com.example.demo.service.impl.PedidoServiceImpl;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
    private PedidoServiceImpl service;

    public PedidoController(PedidoServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO pedido) {

        Pedido pedidoNew = service.salvar(pedido);
        return pedidoNew.getId();

    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public InfoPedidoDTO getPedido(@PathVariable Integer id) {

        return service
                .obterPedidoCompleto(id)
                .map(p -> converter(p))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "pedido não encontrado"));

    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatus(@PathVariable Integer id,@RequestBody AtualizacaoStatusPedidoDTO atualizacao) {
        String novoStatus = atualizacao.getNovoStatus();
         service.atualizarPedido(id,StatusPedido.valueOf(novoStatus));
    
    }

    private InfoPedidoDTO converter(Pedido p) {
        return InfoPedidoDTO
                .builder()
                .codPedido(p.getId())
                .dataPedido(p.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .cpf(p.getCliente().getCpf())
                .status(p.getStatus().name())
                .nomeCliente(p.getCliente().getNome())
                .total(p.getTotal())
                .itens(converterItens(p.getItemsPedido()))
                .build();

    }

    private List<InfoItensPedidoDTO> converterItens(List<ItemPedido> ip) {
        if (CollectionUtils.isEmpty(ip)) {
            return Collections.emptyList();
        }

        return ip.stream().map(
                item -> InfoItensPedidoDTO
                        .builder()
                        .nomeProduto(item.getProduto().getDescricao())
                        .preçoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()

        ).collect(Collectors.toList());

    }

}
