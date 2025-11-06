package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

//import org.apache.catalina.connector.ClientAbortException;
  

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    
    private BigDecimal valorTotal;
    private String numeroPedido;
    private BigDecimal subtotal;
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;
    
    private LocalDateTime dataPedido = LocalDateTime.now();
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;

    @Embedded 
    private Endereco enderecoEntrega;
    


}
