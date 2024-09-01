package com.devsu.account.application.controller.accounts;

import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Request;
import com.devsu.account.util.generic.dao.Response;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devsu/api/v1/cuentas")
@RequiredArgsConstructor
public class CuentaDeleteController {

    private static final Logger LOG = LogManager.getLogger(CuentaDeleteController.class);

    private final CuentaService cuentaService;

    @DeleteMapping(value = "/delete", produces = "application/json")
    public ResponseEntity<Response> delete(@RequestBody Request request) {
        LOG.info("API [ DELETE: /delete ] INI => Process service delete account");
        Response response = new Response();
        try {
            Long id = request.getParam("id", Long.class);

            cuentaService.deleteAccount(id);

            response.putItem("message", "Cuenta " + id + " eliminado con exito!");

            return ResponseEntity.ok(response);

        } catch (MyApplicationException ex) {
            response.putError("code", ex.getCodigo());
            response.putError("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (Exception ex) {
            return MyApplicationException.validarResultadoResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);

        } finally {
            LOG.info("API [ DELETE: /delete ] END => Process service delete account");
        }
    }
}
