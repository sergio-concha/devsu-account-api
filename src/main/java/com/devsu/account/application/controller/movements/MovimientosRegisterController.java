package com.devsu.account.application.controller.movements;

import com.devsu.account.domain.dto.Movimientos;
import com.devsu.account.service.movements.MovimientosService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Request;
import com.devsu.account.util.generic.dao.Response;
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
@RequestMapping("/devsu/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientosRegisterController {

    private static final Logger LOG = LogManager.getLogger(MovimientosRegisterController.class);

    private final MovimientosService movimientosService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Response> register(@RequestBody Request request) {
        LOG.info("API [ POST: /register ] INI => Process service register customer");
        Response response = new Response();
        try {
            String numeroCuenta = request.getParam("numeroCuenta", String.class);
            String tipoMovimiento = request.getParam("tipoMovimiento", String.class);
            String fecha = request.getParam("fecha", String.class);
            BigDecimal valor = request.getParam("valor", BigDecimal.class);

            Movimientos movimiento = movimientosService.saveMovimiento(numeroCuenta, tipoMovimiento, fecha, valor);

            Map<String, Object> movimientoResponse = movimiento.toHashMap();

            response.putItem("massiveSelect", movimientoResponse);

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ POST: /register ] END => Process service register customer");
        }
    }
}
