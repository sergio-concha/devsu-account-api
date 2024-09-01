package com.devsu.account.domain.repository;

import com.devsu.account.domain.dto.Movimientos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository(value = "movimientosRepository")
public interface MovimientosRepository extends JpaRepository<Movimientos, Long> {

    @Override
    @Query(value = "Select c " +
            "from Movimientos c " +
            "join fetch c.cuenta p")
    List<Movimientos> findAll();

    @Query(value = "Select m " +
            "from Movimientos m " +
            "join fetch m.cuenta c " +
            "where m.id = :id")
    Movimientos findMovementById(@Param("id") Long id);

    @Query(value = "Select m " +
            "from Movimientos m " +
            "join fetch m.cuenta c " +
            "Where c.clienteId = :clienteId " +
            "and c.numeroCuenta = :numeroCuenta")
    List<Movimientos> findMovementsByCustomer(@Param("clienteId") Long clienteId,
                                              @Param("numeroCuenta") String numeroCuenta);

    @Query(value = "Select m " +
            "from Movimientos m " +
            "join fetch m.cuenta c " +
            "Where c.clienteId = :clienteId " +
            "and m.fecha between :dateFrom and :dateTo")
    List<Movimientos> findMovementsByDateAndCustomer(
            @Param("clienteId") Long clienteId,
            @Param("dateFrom") Date dateFrom,
            @Param("dateTo") Date dateTo);

}
