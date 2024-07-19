package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Cliente;


public interface ClientesJpa  extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNomeLike(String nome);
    boolean existsByNome(String nome);

    @Query(value="select c from Cliente c where c.nome like :nome")
    List<Cliente> encontratPorNome(@Param("nome") String nome);

    @Query(value="select * from cliente  where nome like %:nome%",nativeQuery = true)
    List<Cliente> encontrarPorNomeNativeSql(@Param("nome") String nome);

    @Transactional
    void deleteByNomeLike(String nome);

    @Query(value="select c from Cliente c left join fetch c.pedidos p where c.id =:id ")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);
}
