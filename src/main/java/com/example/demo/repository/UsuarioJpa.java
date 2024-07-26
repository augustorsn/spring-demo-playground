package com.example.demo.repository;

import com.example.demo.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UsuarioJpa extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
