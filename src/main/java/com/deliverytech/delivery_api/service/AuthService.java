package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.request.LoginRequest;
import com.deliverytech.delivery_api.dto.request.RegisterRequest;
import com.deliverytech.delivery_api.dto.response.LoginResponse;
import com.deliverytech.delivery_api.dto.response.UserResponse;
import com.deliverytech.delivery_api.model.Usuario;

public interface AuthService {

    // Cláusula numero 1: Deve saber como processar um login
    LoginResponse login(LoginRequest request);

    // Clausula numero 2: Deve saber como registrar um novo usuario
    Usuario register(RegisterRequest request);

    // Clausula numero 3? Deve saber como obter informacoes do usuario logado
    UserResponse getCurrentUser();


}
