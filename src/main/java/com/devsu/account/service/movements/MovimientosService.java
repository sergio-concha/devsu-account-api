package com.devsu.account.service.movements;

import com.devsu.account.domain.dto.Movimientos;
import com.devsu.account.util.exception.MyApplicationException;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientosService {
    Movimientos saveMovimiento(String numeroCuenta, String tipoMovimiento, String fecha, BigDecimal valor) throws MyApplicationException;

    List<Movimientos> getReportMovements(Long clienteId, String dateFrom, String dateTo);

    void deleteMovements(Long id) throws MyApplicationException;

    Movimientos updateMovements(Long id, String numeroCuenta, String tipoMovimiento, String fecha, BigDecimal valor) throws MyApplicationException;
}
