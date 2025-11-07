package com.deliverytech.delivery_api.repository;

import com.deliverytech.delivery_api.model.Pedido;
import com.deliverytech.delivery_api.model.StatusPedido;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByRestauranteId(Long restauranteId);

    // CORREÇÃO 1: Renomeado para coincidir com a propriedade 'statusPedido'
    List<Pedido> findByStatusPedido(StatusPedido status);

    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens i LEFT JOIN FETCH i.produto WHERE p.id = :id")
    Optional<Pedido> findByIdWithItens(@Param("id") Long id);

    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.itens i LEFT JOIN FETCH i.produto WHERE p.cliente.id = :clienteId")
    List<Pedido> findByClienteIdWithItens(@Param("clienteId") Long clienteId);

    @Query("SELECT p.restaurante.nome, SUM(p.valorTotal) FROM Pedido p GROUP BY p.restaurante.nome ORDER BY SUM(p.valorTotal) DESC")
    List<Object[]> calcularTotalVendasPorRestaurante();

    @Query("SELECT p FROM Pedido p WHERE p.valorTotal > :valor ORDER BY p.valorTotal DESC")
    List<Pedido> buscarPedidosComValorAcimaDe(@Param("valor") BigDecimal valor);

    // CORREÇÃO 2: Alterado 'p.status' para 'p.statusPedido' no HQL/JPQL
    @Query("SELECT p FROM Pedido p WHERE p.dataPedido BETWEEN :inicio AND :fim AND p.statusPedido = :status ORDER BY p.dataPedido DESC")
    List<Pedido> relatorioPedidosPorPeriodoEStatus(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim, @Param("status") StatusPedido status);

    @Query("SELECT p.restaurante.nome as nomeRestaurante, SUM(p.valorTotal) as totalVendas, COUNT(p.id) as quantidadePedidos FROM Pedido p GROUP BY p.restaurante.nome")
    List<RelatorioVendas> obterRelatorioVendasPorRestaurante();

    // CORREÇÃO 3: Renomeado para coincidir com a propriedade 'statusPedido'
    List<Pedido> findByStatusPedidoAndDataPedidoBetween(StatusPedido status, LocalDateTime inicio, LocalDateTime fim);

    List<Pedido> findByDataPedidoGreaterThanEqual(LocalDateTime data);

    List<Pedido> findByDataPedidoLessThanEqual(LocalDateTime data);
}
