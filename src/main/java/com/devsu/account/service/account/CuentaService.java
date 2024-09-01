package com.devsu.account.service.account;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.util.exception.MyApplicationException;

import java.math.BigDecimal;
import java.util.List;

public interface CuentaService {

    Cuenta getAccountById(Long id);

    List<Cuenta> getAccountAll();

    Cuenta saveAccount(Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial)  throws Exception;

    Cuenta updateAccount(Long id, Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial, String estado) throws Exception;

    void deleteAccount(Long id) throws MyApplicationException;

    Cuenta getAccountByAccountNumber(String numeroCuenta);
}
