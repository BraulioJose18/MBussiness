package com.practica02.mbussiness.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

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
    public Task<Void> delete(Marca entity) {
        entity.setStatus(ELIMINATED);
        return Tasks.call(() -> {
            super.save(entity);
            return null;
        });
    }

    @Override
    public MultipleDocumentReferenceLiveData<Marca, ? extends Query> getEntitiesWithActiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<Marca, ? extends Query> getEntitiesWithInactiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", INACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<Marca, ? extends Query> getEntitiesWithEliminatedRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ELIMINATED), this.entityClass);
    }
}
