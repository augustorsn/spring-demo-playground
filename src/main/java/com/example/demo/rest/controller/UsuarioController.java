package com.example.demo.rest.controller;

import com.example.demo.domain.Usuario;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.repository.UsuarioJpa;
import com.example.demo.repository.UsuarioJpa;
import com.example.demo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario user) {
        String senhaCrypt = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaCrypt);
        return userService.salvar(user);

    }

}
