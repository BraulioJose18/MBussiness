package com.practica02.mbussiness.clases;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.Exclude;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import lombok.*;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Articulo extends Entity {
    private String codigo;
    private String nombre;
    private Double precioUnitario;
    private String status;
    // Codigo de la marca (NO DOCUMENT ID)
    private String marcaId;
    // Codigo de la Unidad de Medida (NO DOCUMENT ID)
    private String unidadMedidaId;
    @Exclude
    private DocumentReferenceFirebaseLiveData<Marca> marcaLiveData;
    @Exclude
    private DocumentReferenceFirebaseLiveData<UnidadMedida> unidadMedidaLiveData;
    @Exclude
    private Marca marca;
    @Exclude
    private UnidadMedida unidadMedida;

    public Articulo(String codigo, String nombre, Double precioUnitario, String status, String marcaId, String unidadMedidaId) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.status = status;
        this.precioUnitario = precioUnitario;
        this.marcaId = marcaId;
        this.unidadMedidaId = unidadMedidaId;
    }

    @Exclude
    public DocumentReferenceFirebaseLiveData<Marca> getMarcaLiveData() {
        return marcaLiveData;
    }

    @Exclude
    public DocumentReferenceFirebaseLiveData<UnidadMedida> getUnidadMedidaLiveData() {
        return unidadMedidaLiveData;
    }

    @Exclude
    public Marca getMarca() {
        return marca;
    }

    @Exclude
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }
}
