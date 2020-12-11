package com.practica02.mbussiness.repository;

import android.util.Log;

import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.livedata.QueryFirebaseLiveData;

public class MarcaRepository extends FirebaseRepository<Marca> implements RequiredOperation<Marca> {

    private static MarcaRepository instance;

    public synchronized static MarcaRepository getInstance() {
        if (instance == null) {
            instance = new MarcaRepository();
        }
        return instance;
    }

    private MarcaRepository() {
        super(Marca.class);
    }

    @Override
    public QueryFirebaseLiveData<Marca> getEntitiesWithActiveRegistry() {
        return new QueryFirebaseLiveData<Marca>(this.collectionReference.whereEqualTo("status", ACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<Marca> getEntitiesWithInactiveRegistry() {
        return new QueryFirebaseLiveData<Marca>(this.collectionReference.whereEqualTo("status", INACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<Marca> getEntitiesWithEliminatedRegistry() {
        return new QueryFirebaseLiveData<Marca>(this.collectionReference.whereEqualTo("status", ELIMINATED), entityClass);
    }
}
