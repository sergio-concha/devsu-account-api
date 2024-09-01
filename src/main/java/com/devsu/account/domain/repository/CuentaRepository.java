package com.devsu.account.domain.repository;

import com.devsu.account.domain.dto.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "cuentaRepository")
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query(value = "Select c " +
            "from Cuenta c " +
            "Where c.numeroCuenta = :numeroCuenta")
    Cuenta findAccountByAccountNumber(@Param("numeroCuenta") String numeroCuenta);

    @Query(value = "Select c " +
            "from Cuenta c " +
            "Where c.clienteId = :clienteId")
    Cuenta findAccountByClienteId(@Param("clienteId") Long clienteId);
}
