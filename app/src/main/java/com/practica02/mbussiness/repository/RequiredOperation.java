package com.practica02.mbussiness.repository;

import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Entity;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

public interface RequiredOperation<T extends Entity> {
    /**
     * Consulta para los registros activos.
     */
    public static final String ACTIVE = "A";
    /**
     * Consulta para los registros inactivos.
     */
    public static final String INACTIVE = "I";
    /**
     * Consulta para los reqistros eliminados.
     */
    public static final String ELIMINATED = "*";

    /**
     * Obtiene entidades con un registro activo.
     *
     * @return Retorna un live data de los documentos que cumplen con el estado activo.
     */
    MultipleDocumentReferenceLiveData<T, ? extends Query> getEntitiesWithActiveRegistry();

    /**
     * Obtiene entidades con un registro Inactivo.
     *
     * @return Retorna un live data de los documentos que cumplen con el estado inactivo.
     */
    MultipleDocumentReferenceLiveData<T, ? extends Query> getEntitiesWithInactiveRegistry();

    /**
     * Obtiene entidades con un registro activo.
     *
     * @return Retorna un live data de los documentos que cumplen con el estado eliminado.
     */
    MultipleDocumentReferenceLiveData<T, ? extends Query> getEntitiesWithEliminatedRegistry();
}
