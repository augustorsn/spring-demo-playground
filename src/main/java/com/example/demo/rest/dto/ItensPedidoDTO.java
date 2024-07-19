package com.example.demo.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItensPedidoDTO {
    private Integer produto;
    private Integer quantidade;    
}
