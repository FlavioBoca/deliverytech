package com.deliverytech.delivery_api.dto.response;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Getter
@Setter
public class UserResponse {

    private String message;
    
    private String email;

    private String nome;

    private String role;

    public static UserResponse fromEntity(Object usuario) {
        
        UserResponse response = new UserResponse();
        
        response.setMessage("Usuário criado com sucesso");
        
        return response;
        
    }

}
