package com.deliverytech.delivery_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deliverytech.delivery_api.model.Cliente;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
    // SELECT * FROM cliente WHERE email = ?

    boolean existsByEmail(String email);
    // SELECT COUNT(*) FROM cliente WHERE email = ?

    List<Cliente> findByAtivoTrue();
    // SELECT * FROM cliente WHERE ativo = true

    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    // SELECT * FROM cliente WHERE LOWER(nome) LIKE LOWER('%'...'%')

    @Query(value = "SELECT c.nome, COUNT(p.id) AS total_pedidos " +
                   "FROM cliente c " +
                   "LEFT JOIN pedido p ON c.id = p.cliente_id " +
                   "GROUP BY c.id,c.nome " +
                   "ORDER BY total_pedidos DESC " +
                   "LIMIT 10", nativeQuery = true )
                    List<Object[]> rankingClientesPorPedidos();
}

