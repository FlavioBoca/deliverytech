package com.deliverytech.delivery_api.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery_api.dto.request.LoginRequest;
import com.deliverytech.delivery_api.dto.request.RegisterRequest;
import com.deliverytech.delivery_api.dto.response.LoginResponse;
import com.deliverytech.delivery_api.model.Role;
import com.deliverytech.delivery_api.model.Usuario;
import com.deliverytech.delivery_api.repository.UsuarioRepository;
import com.deliverytech.delivery_api.security.JwtUtil;
import com.deliverytech.delivery_api.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            String token =  jwtUtil.generateToken(authentication.getName());

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(authentication.getName());
            response.setMessage("Login realizado com sucesso");

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Credenciais inválidas", e);
        }
    }

    //Espaço reservador para inserção de tokens na blacklist
    @Override
    public void logout(String token) {

    }

    //Método para buscar um usuário específico -  por ID
    @Override
    public Object buscarPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
          if(usuario.isPresent()) {
                return usuario.get();
          }
          throw new RuntimeException("Usuário não encontrado " + id);
    }

    //Método para inativar um usuário
    @Override
    public void inativarUsuario(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setAtivo(false);
            usuarioRepository.save(usuario);
        } else {
            throw new RuntimeException("Usuário não encontrado " + id);
        }
    }
    
    //Métodos para verificação e busca por email
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }

    @Override
    public UserDetails buscarPorEmail(String email) {
        UserDetails usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado" + email);
        }
        return usuario;
    }

    // Método para salvar
    @Override
    public Usuario salvar(RegisterRequest registerRequest) {
        if (existePorEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + registerRequest.getEmail());
        }

        Usuario usuario = new Usuario();
        usuario.setNome(registerRequest.getNome());
        usuario.setEmail(registerRequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(registerRequest.getSenha()));
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        if (registerRequest.getRole() == null) {
            usuario.setRole(Role.USER);            
        } else {
            usuario.setRole(registerRequest.getRole());
        }
    
        return usuarioRepository.save(usuario);
    }


    
}
