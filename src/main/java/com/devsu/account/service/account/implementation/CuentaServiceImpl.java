package com.devsu.account.service.account.implementation;

import com.devsu.account.domain.dto.Cuenta;
import com.devsu.account.domain.entities.Clientes;
import com.devsu.account.domain.repository.CuentaRepository;
import com.devsu.account.service.account.CuentaService;
import com.devsu.account.util.exception.MyApplicationException;
import com.devsu.account.util.generic.dao.Response;
import com.devsu.account.util.utilities.DtoUtils;
import com.devsu.account.util.utilities.MapperUtil;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("cuentaService")
public class CuentaServiceImpl implements CuentaService {
    private static final Logger LOG = LogManager.getLogger(CuentaServiceImpl.class);

    private final WebClient webClient;
    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaServiceImpl(CuentaRepository cuentaRepository, WebClient.Builder webClientBuilder) {
        this.cuentaRepository = cuentaRepository;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8090/devsu/api/v1").build();
    }

    public Clientes obtenerClientePorId(Long id) {
        Clientes account = Clientes.builder().build();
        Mono<Response> responseMono = this.webClient.get()
                .uri("/clientes/get?id={id}", id)
                .retrieve()
                .bodyToMono(Response.class);

        Response response = responseMono.block();
        Map<String, Object> data = response.getData();
        if (data != null && data.containsKey("customer")) {
            Map<String, Object> customer = (Map<String, Object>) data.get("customer");
            account = MapperUtil.mapToEntity(customer, Clientes.class);
        }
        return account;
    }


    @Transactional
    public Cuenta getAccountById(Long id) {
        Optional<Cuenta> accountOptional = cuentaRepository.findById(id);
        Cuenta account = new Cuenta();
        if(accountOptional.isPresent()) {
            account = accountOptional.get();

            Clientes customer = obtenerClientePorId(account.getClienteId());
            if(DtoUtils.isPersistence(customer)) {
                account.setNombreCliente(customer.getNombre());
                account.setIdentificacionCliente(customer.getIdentificacion());
            }
        }
        return account;
    }

    @Transactional
    public List<Cuenta> getAccountAll() {
        List<Cuenta> accounts = cuentaRepository.findAll();

        accounts.stream().forEach(account -> {
            Clientes customer = obtenerClientePorId(account.getClienteId());
            if(DtoUtils.isPersistence(customer)) {
                account.setNombreCliente(customer.getNombre());
                account.setIdentificacionCliente(customer.getIdentificacion());
            }
        });

        return accounts;
    }

    @Override
    public Cuenta getAccountByAccountNumber(String numeroCuenta) {
        Cuenta account = cuentaRepository.findAccountByAccountNumber(numeroCuenta);
        if(DtoUtils.isPersistence(account)) {
            Clientes customer = obtenerClientePorId(account.getClienteId());
            if (DtoUtils.isPersistence(customer)) {
                account.setNombreCliente(customer.getNombre());
                account.setIdentificacionCliente(customer.getIdentificacion());
            }
        }
        return account;
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Cuenta saveAccount(Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial)  throws MyApplicationException {
        Clientes customer = obtenerClientePorId(clienteId);
        if(DtoUtils.isNotPersistence(customer)) {
            throw new MyApplicationException("E001", "El cliente " + clienteId + " no existe.");
        }
        Cuenta cuenta = Cuenta.builder()
                .clienteId(clienteId)
                .numeroCuenta(numeroCuenta)
                .tipoCuenta(tipoCuenta)
                .saldoInicial(saldoInicial)
                .estado("A")
                .build();
        cuenta = cuentaRepository.save(cuenta);
        return getAccountById(cuenta.getId());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public Cuenta updateAccount(Long id, Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial,
                                String estado) throws Exception {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if(cuenta.isEmpty()) {
            throw new MyApplicationException("E001", "La cuenta " + numeroCuenta + " no existe");
        }
        Cuenta saveCuenta = cuenta.get();
        saveCuenta.setClienteId(clienteId);
        saveCuenta.setNumeroCuenta(numeroCuenta);
        saveCuenta.setTipoCuenta(tipoCuenta);
        saveCuenta.setSaldoInicial(saldoInicial);
        saveCuenta.setEstado(estado);
        saveCuenta = cuentaRepository.save(saveCuenta);

        return getAccountById(saveCuenta.getId());
    }

    @Override
    @Transactional(rollbackOn = {Exception.class, MyApplicationException.class})
    public void deleteAccount(Long id) throws MyApplicationException {
        Optional<Cuenta> account = cuentaRepository.findById(id);
        if(account.isEmpty()) {
            throw new MyApplicationException("E001", "Cuenta " + id + " no existe.");
        }
        try {
            cuentaRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyApplicationException("E002", "Error al eliminar cuenta.", e);
        }
    }

}
