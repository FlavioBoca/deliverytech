package com.deliverytech.delivery_api.model;

public enum StatusPedido {
    // O momento em que foi realizado o pediodo pelo cliente
    CRIADO("Criado"), 
    // O momento em que o restaurante confirmou o pedido
    CONFIRMADO("Confirmado"), // O momento em que o restaurante confirmou o pedido
    // O momento em que o restaurante está preparando o pedido
    PREPARANDO("Preparando"), 
    // O momento em que o pedido saiu para entrega - Motoboy
    SAIU_PARA_ENTREGA("Saiu para Entrega"),
    // O momento em que o pedido foi entregue ao cliente
    ENTREGUE("Entregue"), 
    // O momento em que o pedido foi cancelado
    CANCELADO("Cancelado");
    
    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
