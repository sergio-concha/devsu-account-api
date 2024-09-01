package com.devsu.account.application.controller.accounts;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Request;
import com.devsu.account.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaUpdateController {

    private static final Logger LOG = LogManager.getLogger(CuentaUpdateController.class);

    private final CuentaService cuentaService;

    @PutMapping(value = "/update", produces = "application/json")
    public ResponseEntity<Response> update(@RequestBody Request request) {
        LOG.info("API [ PUT: /update ] INI => Process service register account");
        Response response = new Response();
        try {
            Long id = request.getParam("id", Long.class);
            Long clienteId = request.getParam("clienteId", Long.class);
            String numeroCuenta = request.getParam("numeroCuenta", String.class);
            String tipoCuenta = request.getParam("tipoCuenta", String.class);
            BigDecimal saldoInicial = request.getParam("saldoInicial", BigDecimal.class);
            String estado = request.getParam("estado", String.class);

            Cuenta account = cuentaService.updateAccount(id, clienteId, numeroCuenta, tipoCuenta, saldoInicial, estado);

            Map<String, Object> accountResponse = account.toHashMap();

            response.putItem("account", accountResponse);

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ PUT: /update ] END => Process service register account");
        }
    }
}
