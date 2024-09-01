package com.devsu.account.domain.entities;

import com.devsu.account.util.generic.dao.GenericDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Clientes extends GenericDto<Clientes> {

    @Serial
    private static final long serialVersionUID = 4633771914211362138L;

    private Long id;
    private String clienteId;
    private String identificacion;
    private String nombre;
    private String genero;
    private String telefono;
    private String direccion;
    private Integer edad;
    private String contrasena;
    private String estado;
    private String generoLabel;
    private String estadoLabel;

    @Builder
    public Clientes(Long id, String clienteId, String identificacion, String nombre, String genero, String telefono,
                    String direccion, Integer edad, String contrasena, String estado, String generoLabel, String estadoLabel) {
        this.id = id;
        this.clienteId = clienteId;
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.genero = genero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.edad = edad;
        this.contrasena = contrasena;
        this.estado = estado;
        this.generoLabel = generoLabel;
        this.estadoLabel = estadoLabel;
    }

    public Map<String, Object> toHashMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("clienteId", this.clienteId);
        map.put("contrasena", this.contrasena);
        map.put("estado", this.estado);
        map.put("identificacion", this.identificacion);
        map.put("direccion", this.direccion);
        map.put("nombre", this.nombre);
        map.put("edad", this.edad);
        map.put("genero", this.genero);
        map.put("telefono", this.telefono);

        return map;
    }
}
