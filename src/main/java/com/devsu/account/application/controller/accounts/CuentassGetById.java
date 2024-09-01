package com.devsu.account.application.controller.accounts;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.generic.dao.Response;
import com.devsu.account.util.utilities.DtoUtils;
import com.devsu.account.util.exception.MyApplicationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/devsu/api/v1/clientes")
@RequiredArgsConstructor
public class CuentassGetById {

    private static final Logger LOG = LogManager.getLogger(CuentassGetById.class);

    private final CuentaService cuentaService;

    @GetMapping(value = "/get", produces = "application/json")
    public ResponseEntity<Response> getById(@RequestParam Long id) {
        LOG.info("API [ GET: /get ] INI => Process service delete account");
        Response response = new Response();
        try {
            Cuenta account = cuentaService.getAccountById(id);
            Map<String, Object> accountResponse = new HashMap<>();
            if(DtoUtils.isPersistence(account)) {
                accountResponse = account.toHashMap();
            }

            response.putItem("account", accountResponse);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ GET: /get ] END => Process service delete account");
        }
    }
}
