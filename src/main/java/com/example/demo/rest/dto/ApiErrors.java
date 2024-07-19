package com.example.demo.rest.dto;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {

    @Getter
    private List<String> errors;

    public ApiErrors(List<String> mensagem) {
        this.errors = mensagem;
    }

    public ApiErrors(String mensagem) {
        this.errors = Arrays.asList(mensagem);
    }

}
