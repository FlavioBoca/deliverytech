package com.deliverytech.delivery_api.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.repository.UsuarioRepository;
import com.deliverytech.delivery_api.dto.request.RegisterRequest;
import com.deliverytech.delivery_api.dto.response.LoginResponse;
import com.deliverytech.delivery_api.dto.response.UserResponse;
import com.deliverytech.delivery_api.dto.request.LoginRequest;
import com.deliverytech.delivery_api.service.AuthService;
import com.deliverytech.delivery_api.model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements UserDetailsService, AuthService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = usuarioRepository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return usuario;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        throw new UnsupportedOperationException("Login não implementado");
    }

    @Override
    public Usuario register(RegisterRequest request) {
        throw new UnsupportedOperationException("Registro não implementado");
    }

    @Override
    public UserResponse getCurrentUser() {
        throw new UnsupportedOperationException("Usuário atual não implementado");
    }

}