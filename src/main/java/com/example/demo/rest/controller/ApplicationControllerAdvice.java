package com.example.demo.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.PedidoNaoEncontradoException;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.rest.dto.ApiErrors;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String msgErro = ex.getMessage();
        if(msgErro.isEmpty()){
            msgErro = "Erro inseperado";
        }
        return new ApiErrors(msgErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex){
        String msgErro = ex.getMessage();
        if(msgErro.isEmpty()){
            msgErro = "Erro inseperado";
        }
        return new ApiErrors(msgErro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> erros =  ex.getBindingResult().getAllErrors().stream().map( e-> e.getDefaultMessage()).collect(Collectors.toList());
        return new ApiErrors(erros);
    }
}