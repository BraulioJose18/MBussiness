package com.practica02.mbussiness.repository;

import com.practica02.mbussiness.clases.Entity;
import com.practica02.mbussiness.repository.livedata.QueryFirebaseLiveData;

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
    QueryFirebaseLiveData<T> getEntitiesWithActiveRegistry();

    /**
     * Obtiene entidades con un registro Inactivo.
     *
     * @return Retorna un live data de los documentos que cumplen con el estado inactivo.
     */
    QueryFirebaseLiveData<T> getEntitiesWithInactiveRegistry();

    /**
     * Obtiene entidades con un registro activo.
     *
     * @return Retorna un live data de los documentos que cumplen con el estado eliminado.
     */
    QueryFirebaseLiveData<T> getEntitiesWithEliminatedRegistry();
}
