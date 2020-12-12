package com.practica02.mbussiness.repository;

import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.livedata.QueryFirebaseLiveData;

public class UnidadMedidaRepository extends FirebaseRepository<UnidadMedida> implements RequiredOperation<UnidadMedida> {

    private static UnidadMedidaRepository instance;

    public static UnidadMedidaRepository getInstance() {
        if (instance == null) {
            instance = new UnidadMedidaRepository();
        }
        return instance;
    }

    private UnidadMedidaRepository() {
        super(UnidadMedida.class);
    }

    @Override
    public QueryFirebaseLiveData<UnidadMedida> getEntitiesWithActiveRegistry() {
        return new QueryFirebaseLiveData<UnidadMedida>(this.collectionReference.whereEqualTo("status", ACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<UnidadMedida> getEntitiesWithInactiveRegistry() {
        return new QueryFirebaseLiveData<UnidadMedida>(this.collectionReference.whereEqualTo("status", INACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<UnidadMedida> getEntitiesWithEliminatedRegistry() {
        return new QueryFirebaseLiveData<UnidadMedida>(this.collectionReference.whereEqualTo("status", ELIMINATED), entityClass);
    }
}
