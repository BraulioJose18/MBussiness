package com.practica02.mbussiness.clases;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Marca extends Entity {
    private String codigo;
    private String nombre;
    private String status;

    public Marca(String documentId, String codigo, String nombre, String status) {
        super(documentId);
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
    }
}
