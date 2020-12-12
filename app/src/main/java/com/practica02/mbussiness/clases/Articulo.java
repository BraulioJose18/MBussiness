package com.practica02.mbussiness.clases;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.Exclude;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Articulo extends Entity {
    private String codigo;
    private String nombre;
    private String status;
    // Codigo de la marca (NO DOCUMENT ID)
    private String marcaId;
    // Codigo de la Unidad de Medida (NO DOCUMENT ID)
    private String unidadMedidaId;
    @Exclude
    private LiveData<Marca> marca;
    @Exclude
    private LiveData<UnidadMedida> unidadMedida;

    public Articulo(String codigo, String nombre, String status, String marcaId, String unidadMedidaId) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
        this.marcaId = marcaId;
        this.unidadMedidaId = unidadMedidaId;
    }
}
