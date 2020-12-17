package com.practica02.mbussiness.repository;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.repository.relationlivedata.ArticuloCollectionLiveData;

public class ArticuloRepository extends FirebaseRepository<Articulo> implements RequiredOperation<Articulo> {
    private static ArticuloRepository instance;
    private final MarcaRepository marcaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    private ArticuloRepository() {
        super(Articulo.class);
        this.marcaRepository = MarcaRepository.getInstance();
        this.unidadMedidaRepository = UnidadMedidaRepository.getInstance();
    }

    public static ArticuloRepository getInstance() {
        if (instance == null) {
            instance = new ArticuloRepository();
        }
        return instance;
    }

    @Override
    public Task<Void> delete(Articulo entity) {
        entity.setStatus(ELIMINATED);
        return Tasks.call(() -> {
            super.update(entity);
            return null;
        });
    }

    public ArticuloCollectionLiveData<? extends Query> findAllWithMarcaAndUnidadMedida() {
        return new ArticuloCollectionLiveData<>(this.collectionReference, this.marcaRepository, this.unidadMedidaRepository);
    }

    public ArticuloCollectionLiveData<? extends Query> findAllActiveRegistryWithMarcaAndUnidadMedida() {
        return new ArticuloCollectionLiveData<>(this.collectionReference.whereEqualTo("status", ACTIVE), this.marcaRepository, this.unidadMedidaRepository);
    }

    public ArticuloCollectionLiveData<? extends Query> findAllInactiveRegistryWithMarcaAndUnidadMedida() {
        return new ArticuloCollectionLiveData<>(this.collectionReference.whereEqualTo("status", INACTIVE), this.marcaRepository, this.unidadMedidaRepository);
    }

    public ArticuloCollectionLiveData<? extends Query> findAllEliminatedRegistryWithMarcaAndUnidadMedida() {
        return new ArticuloCollectionLiveData<>(this.collectionReference.whereEqualTo("status", ELIMINATED), this.marcaRepository, this.unidadMedidaRepository);
    }

    @Override
    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getEntitiesWithActiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getEntitiesWithInactiveRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", INACTIVE), this.entityClass);
    }

    @Override
    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getEntitiesWithEliminatedRegistry() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference.whereEqualTo("status", ELIMINATED), this.entityClass);
    }
}
