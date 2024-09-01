package com.devsu.account.service.movements.implementation;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.domain.dto.Movimientos;
import com.devsu.account.domain.entities.Clientes;
import com.devsu.account.domain.repository.MovimientosRepository;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.service.movements.MovimientosService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.utilities.DtoUtils;
import com.devsu.account.util.utilities.FechasUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service("movimientosService")
public class MovimientosServiceImpl implements MovimientosService {

    private final MovimientosRepository movimientosRepository;
    private final CuentaService cuentaService;

    @Autowired
    public MovimientosServiceImpl(MovimientosRepository movimientosRepository, CuentaService cuentaService) {
        this.movimientosRepository = movimientosRepository;
        this.cuentaService = cuentaService;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Movimientos saveMovimiento(String numeroCuenta, String tipoMovimiento, String fecha, BigDecimal valor) throws MyApplicationException {
        Cuenta account =  cuentaService.getAccountByAccountNumber(numeroCuenta);
        if(DtoUtils.isNotPersistence(account)) {
            throw new MyApplicationException("M001", "La cuenta " + numeroCuenta + " no existe.");
        }

        BigDecimal saldoDisponible = account.getSaldoInicial();

        List<Movimientos> movements = movimientosRepository.findMovementsByCustomer(account.getClienteId(), account.getNumeroCuenta());
        if (!movements.isEmpty()) {
            saldoDisponible = movements.stream()
                    .map(Movimientos::getValor)
                    .reduce(saldoDisponible, BigDecimal::add);
        }
        saldoDisponible = saldoDisponible.add(valor);

        if (saldoDisponible.compareTo(BigDecimal.ZERO) < 0) {
            throw new MyApplicationException("M002", "Saldo no disponible.");
        }

        Movimientos saveMovement = Movimientos.builder()
                .cuenta(account)
                .tipoMovimiento(tipoMovimiento)
                .fecha(fecha != null ? FechasUtil.getFechaFormato(fecha) : FechasUtil.getCurrentDate())
                .valor(valor)
                .saldo(saldoDisponible)
                .build();

        return movimientosRepository.save(saveMovement);
    }

    @Transactional
    public List<Movimientos> getReportMovements(Long clienteId, String dateFrom, String dateTo) {
        List<Movimientos> movements = movimientosRepository.findMovementsByDateAndCustomer(clienteId,
                FechasUtil.getFechaFormato(dateFrom), FechasUtil.getFechaFormato(dateTo));

        movements.forEach(movement -> {
            Cuenta account = cuentaService.getAccountById(movement.getCuenta().getId());
            movement.setCuenta(account);
        });

        return movements;
    }

    @Override
    public void deleteMovements(Long id) throws MyApplicationException{
        Optional<Movimientos> movement = movimientosRepository.findById(id);
        if(movement.isEmpty()) {
            throw new MyApplicationException("M001", "Movimiento " + id + " no existe.");
        }
        try {
            movimientosRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyApplicationException("M002", "Error al eliminar movimiento.", e);
        }
    }

    @Override
    public Movimientos updateMovements(Long id, String numeroCuenta, String tipoMovimiento, String fecha, BigDecimal valor) throws MyApplicationException {
        Movimientos movement = movimientosRepository.findMovementById(id);
        if(DtoUtils.isNotPersistence(movement)) {
            throw new MyApplicationException("E001", "El movimiento " + id + " no existe");
        }

        Cuenta account =  cuentaService.getAccountByAccountNumber(numeroCuenta);
        if(DtoUtils.isNotPersistence(account)) {
            throw new MyApplicationException("M001", "La cuenta " + numeroCuenta + " no existe.");
        }

        movement.setCuenta(account);
        movement.setFecha(fecha != null ? FechasUtil.getFechaFormato(fecha) : FechasUtil.getCurrentDate());
        movement.setTipoMovimiento(tipoMovimiento);
        movement.setValor(valor);

        return movimientosRepository.save(movement);
    }
}
