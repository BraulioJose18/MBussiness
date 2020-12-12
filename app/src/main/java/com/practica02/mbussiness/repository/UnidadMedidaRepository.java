package com.practica02.mbussiness.repository;

import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

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
    public MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> getEntitiesWithActiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> getEntitiesWithInactiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", INACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> getEntitiesWithEliminatedRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ELIMINATED), this.entityClass);
    }
}
