package com.devsu.account.domain.dto;

import com.devsu.account.util.generic.dao.GenericDto;
import com.devsu.account.util.utilities.DtoUtils;
import com.devsu.account.util.utilities.FechasUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Entity
@Table(name="MOVIMIENTOS", schema = "public")
@NoArgsConstructor
public class Movimientos extends GenericDto<Movimientos> {

    @Serial
    private static final long serialVersionUID = 4633771914311361138L;

    @Id
    @SequenceGenerator(name = "Movimientos_sg", sequenceName = "MOVIMIENTOS_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Movimientos_sg")
    private Long id;

    @JoinColumn(name="CUENTA_ID", nullable = false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Cuenta cuenta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA", nullable = false)
    private Date fecha;

    /**
     * D: Deposito <br>
     * R: Retiro
     */
    @Column(name="TIPO_MOVIMIENTO", length = 2, nullable=false)
    private String tipoMovimiento;

    @Column(name="VALOR", length = 2, nullable=false)
    private BigDecimal valor;

    @Column(name="SALDO", length = 2, nullable=false)
    private BigDecimal saldo;


    @Builder
    public Movimientos(Long id, Cuenta cuenta, Date fecha, String tipoMovimiento, BigDecimal valor, BigDecimal saldo) {
        this.id = id;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
    }


    public String tipoMovimientoLabel() {
        String typeCtaLabel = "Deposito";
        if("R".equals(this.tipoMovimiento)) {
            typeCtaLabel = "Retiro";
        }
        return typeCtaLabel;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("tipoMovimiento", this.tipoMovimiento);
        map.put("fecha", FechasUtil.getFechaFormato(this.fecha));
        map.put("saldo", this.saldo);
        map.put("valor", this.valor);
        map.put("tipoMovimientoLabel", this.tipoMovimientoLabel());

        if(DtoUtils.isPersistence(this.cuenta)) {
            map.put("cuentaId", this.cuenta.getId());
            map.put("clienteId", this.cuenta.getClienteId());
            map.put("numeroCuenta", this.cuenta.getNumeroCuenta());
            map.put("tipoCuenta", this.cuenta.getTipoCuenta());
            map.put("saldoInicial", this.cuenta.getSaldoInicial());
            map.put("estado", this.cuenta.getEstado());
            map.put("tipoCuentaLabel", this.cuenta.tipoCuentaLabel());
            map.put("estadoLabel", this.cuenta.statusLabel());
            map.put("nombreCliente", this.cuenta.getNombreCliente());
            map.put("identificacionCliente", this.cuenta.getIdentificacionCliente());
        }

        return map;
    }

    public Map<String, Object> toHashMapReport() {
        Map<String, Object> map = new HashMap<>();
        map.put("fecha", FechasUtil.getFechaFormato(this.fecha));
        map.put("tipoMovimiento", this.tipoMovimientoLabel());
        map.put("saldoDisponible", this.saldo);
        map.put("movimiento", this.valor);

        if(DtoUtils.isPersistence(this.cuenta)) {
            map.put("cliente", this.cuenta.getNombreCliente());
            map.put("tipoCuenta", this.cuenta.getTipoCuenta());
            map.put("numeroCuenta", this.cuenta.getNumeroCuenta());
            map.put("saldoInicial", this.cuenta.getSaldoInicial());
            map.put("estado", this.cuenta.statusLabel());
        }

        return map;
    }

}
