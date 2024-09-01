package com.devsu.account.domain.dto;

import com.devsu.account.util.generic.dao.GenericDto;
import com.devsu.account.util.utilities.DtoUtils;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name="CUENTA", schema = "public")
@NoArgsConstructor
public class Cuenta extends GenericDto<Cuenta> {

    @Serial
    private static final long serialVersionUID = 4633771914311362138L;

    @Id
    @SequenceGenerator(name = "Cuenta_sg", sequenceName = "CUENTA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Cuenta_sg")
    private Long id;

    @Column(name="CLIENTE_ID", length = 50, nullable=false)
    private Long clienteId;

    @Column(name="NUMERO_CUENTA", length = 50, nullable=false)
    private String numeroCuenta;

    /**
     * A: Ahorro <br>
     * C: Corriente
     */
    @Column(name="TIPO_CUENTA", length = 2, nullable=false)
    private String tipoCuenta;

    @Column(name="SALDO_INICIAL", length = 2, nullable=false)
    private BigDecimal saldoInicial;

    /**
     * A: Activo <br>
     * I: Inactivo
     */
    @Column(name="ESTADO", length = 2, nullable=false)
    private String estado;

    @Transient
    private String nombreCliente;

    @Transient
    private String identificacionCliente;

    @Builder
    public Cuenta(Long id, Long clienteId, String numeroCuenta, String tipoCuenta, BigDecimal saldoInicial, String estado,
                  String nombreCliente, String identificacionCliente) {
        this.id = id;
        this.clienteId = clienteId;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.identificacionCliente = identificacionCliente;
    }

    public String statusLabel() {
        String status = "Activo";
        if("I".equals(this.estado)) {
            status = "Inactivo";
        }
        return status;
    }

    public String tipoCuentaLabel() {
        String typeCtaLabel = "Ahorro";
        if("C".equals(this.tipoCuenta)) {
            typeCtaLabel = "Corriente";
        }
        return typeCtaLabel;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("clienteId", this.clienteId);
        map.put("numeroCuenta", this.numeroCuenta);
        map.put("tipoCuenta", this.tipoCuenta);
        map.put("saldoInicial", this.saldoInicial);
        map.put("estado", this.estado);
        map.put("tipoCuentaLabel", this.tipoCuentaLabel());
        map.put("estadoLabel", this.statusLabel());
        map.put("nombreCliente", this.nombreCliente);
        map.put("identificacionCliente", this.identificacionCliente);
        return map;
    }
}
