package com.example.demo.rest.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoPedidoDTO {
    private Integer codPedido;
    private String nomeCliente;
    private String dataPedido;
    private String status;
    private String cpf;
    private BigDecimal total;
    private List<InfoItensPedidoDTO> itens;
}
