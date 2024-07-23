package com.example.demo.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.context.annotation.Role;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.domain.Cliente;
import com.example.demo.repository.ClientesJpa;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private ClientesJpa clientes;

    public ClienteController(ClientesJpa clientes) {
        this.clientes = clientes;
    }

    @GetMapping("{id}")
    public Cliente getClienteById(@PathVariable Integer id) {
        Optional<Cliente> cliente = clientes.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cliente não encontrado");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente save(@RequestBody @Valid Cliente cliente) {
        return clientes.save(cliente);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        try {
            Optional<Cliente> c = clientes.findById(id);
            if (c.isPresent()) {
                clientes.delete(c.get());
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível deletar o cliente");
        }
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {

        try {
            Optional<Cliente> c = clientes.findById(id);
            if (c.isPresent()) {
                cliente.setId(c.get().getId());
                clientes.save(cliente);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível atualizar o cliente");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public List<Cliente> find(Cliente filtro) {

        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example example = Example.of(filtro, matcher);
            List<Cliente> lista = clientes.findAll(example);
            return lista;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Não foi possível listar o cliente");
        }

    }

}
