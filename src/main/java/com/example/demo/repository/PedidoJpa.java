package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Cliente;
import com.example.demo.domain.Pedido;

public interface PedidoJpa extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByCliente(Cliente cliente);

    @Query(" select p from Pedido p left join fetch p.itemsPedido where p.id = :id")
    Optional<Pedido> findByIdFetchItens(Integer id);
}
