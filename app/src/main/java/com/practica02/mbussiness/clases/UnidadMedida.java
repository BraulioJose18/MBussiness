package com.practica02.mbussiness.clases;

import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UnidadMedida extends Entity {
    private String codigo;
    private String nombre;
    private String status;
}
