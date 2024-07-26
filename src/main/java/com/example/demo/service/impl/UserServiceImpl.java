package com.example.demo.service.impl;

import com.example.demo.domain.Usuario;
import com.example.demo.repository.UsuarioJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioJpa usuarioRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepo.findByLogin(username).orElseThrow(()->  new UsernameNotFoundException("Usuario não encontrado") );

        String[] roles = usuario.isAdmin() ? new String[] {"ADMIN","USER"} : new String[] {"USER"};
        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();

       /* if(!username.equals("fulano")){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }
        return User
                .builder()
                .username("fulano")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN","USER")
                .build();*/
    }

    public Usuario salvar(Usuario user) {
        return usuarioRepo.save(user);
    }
}
