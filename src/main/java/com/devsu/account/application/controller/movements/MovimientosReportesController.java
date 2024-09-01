package com.devsu.account.application.controller.movements;

import com.devsu.account.domain.dto.Movimientos;
import com.devsu.account.service.movements.MovimientosService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/movimientos")
@RequiredArgsConstructor
public class MovimientosReportesController {

    private static final Logger LOG = LogManager.getLogger(MovimientosReportesController.class);

    private final MovimientosService movimientosService;

    @GetMapping(value = "/reportes", produces = "application/json")
    public ResponseEntity<Response> reportes(@RequestParam String dateFrom,
                                                  @RequestParam String dateTo,
                                                  @RequestParam Long clienteId) {
        LOG.info("API [ GET: /massiveSelect ] INI => Process service massiveSelect");
        Response response = new Response();
        try {

            List<Movimientos> movements = movimientosService.getReportMovements(clienteId, dateFrom, dateTo);
            List<Map<String, Object>> movementsResponse = movements.stream().map(Movimientos::toHashMapReport).toList();

            response.putItem("massiveSelect", movementsResponse);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ GET: /massiveSelect ] END => Process service massiveSelect");
        }
    }
}
