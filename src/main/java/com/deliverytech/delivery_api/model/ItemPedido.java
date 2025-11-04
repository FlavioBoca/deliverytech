package com.deliverytech.delivery_api.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;

    @Column(name = "preco_total", nullable = false, precision = 10, scale = 2)  
    private BigDecimal subTotal;

    @PrePersist
    @PreUpdate
    private void calcularSubTotal() {
        if (precoUnitario != null && quantidade != null) {
            this.subTotal = precoUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
    
    }
    public void setSubTotal() {
        calcularSubTotal();

    }

    public BigDecimal getValorTotal() {
        return subTotal != null ? subTotal : BigDecimal.ZERO;
    }
    
}



