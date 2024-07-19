package com.example.demo.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.example.demo.domain.Produto;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.repository.ProdutoJpa;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoJpa produtos;

    public ProdutoController(ProdutoJpa produtos) {
        this.produtos = produtos;
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id) {
        Produto p = produtos.findById(id)
                .orElseThrow(() -> new RegraNegocioException("produto não encontrado"));
        return p;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto saveProduto(@RequestBody @Valid Produto produto) {

        produtos.save(produto);
        return produto;

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {

        Produto p = produtos.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Não foi possível deletar o produto"));
        produtos.delete(p);

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {

        try {
            Optional<Produto> p = produtos.findById(id);
            if (p.isPresent()) {
                produto.setId(p.get().getId());
                produtos.save(produto);
            }
        } catch (Exception e) {
            throw new RegraNegocioException("Não foi possível atualizar o produto");
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping
    public List<Produto> find(Produto filtro) {
        try {
            ExampleMatcher matcher = ExampleMatcher.matching()
                    .withIgnoreCase()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
            Example example = Example.of(filtro, matcher);
            List<Produto> lista = produtos.findAll(example);
            return lista;
        } catch (Exception e) {
            throw new RegraNegocioException("Não foi possível listar o produto");
        }
    }

}
