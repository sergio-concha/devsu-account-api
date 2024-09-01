package com.devsu.account.application.controller.accounts;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.generic.dao.Request;
import com.devsu.account.util.generic.dao.Response;
import com.devsu.account.util.exception.MyApplicationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaRegisterController {

    private static final Logger LOG = LogManager.getLogger(CuentaRegisterController.class);

    private final CuentaService cuentaService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Response> register(@RequestBody Request request) {
        LOG.info("API [ POST: /register ] INI => Process service register account");
        Response response = new Response();
        try {
            Long clienteId = request.getParam("clienteId", Long.class);
            String numeroCuenta = request.getParam("numeroCuenta", String.class);
            String tipoCuenta = request.getParam("tipoCuenta", String.class);
            BigDecimal saldoInicial = request.getParam("saldoInicial", BigDecimal.class);

            Cuenta accounts = cuentaService.saveAccount(clienteId, numeroCuenta, tipoCuenta, saldoInicial);

            Map<String, Object> accountResponse = accounts.toHashMap();

            response.putItem("massiveSelect", accountResponse);

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ POST: /register ] END => Process service register account");
        }
    }
}
