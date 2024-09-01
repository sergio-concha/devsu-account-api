package com.devsu.account.application.controller.accounts;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaMassiveSelectController {

    private static final Logger LOG = LogManager.getLogger(CuentaMassiveSelectController.class);

    private final CuentaService cuentaService;

    @GetMapping(value = "/massiveSelect", produces = "application/json")
    public ResponseEntity<Response> massiveSelect() {
        LOG.info("API [ GET: /massiveSelect ] INI => Process account service massiveSelect");
        Response response = new Response();
        try {
            List<Cuenta> cuentas = cuentaService.getAccountAll();
            List<Map<String, Object>> cuentasResponse = cuentas.stream().map(Cuenta::toHashMap).toList();

            response.putItem("massiveSelect", cuentasResponse);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ GET: /massiveSelect ] END => Process account service massiveSelect");
        }
    }
}
