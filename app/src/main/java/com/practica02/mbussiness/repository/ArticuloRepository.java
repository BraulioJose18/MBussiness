package com.practica02.mbussiness.repository;

import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.repository.livedata.QueryFirebaseLiveData;
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

    public ArticuloCollectionLiveData findAllWithMarcaAndUnidadMedida() {
        return new ArticuloCollectionLiveData(collectionReference, marcaRepository, unidadMedidaRepository);
    }

    @Override
    public QueryFirebaseLiveData<Articulo> getEntitiesWithActiveRegistry() {
        return new QueryFirebaseLiveData<Articulo>(this.collectionReference.whereEqualTo("status", ACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<Articulo> getEntitiesWithInactiveRegistry() {
        return new QueryFirebaseLiveData<Articulo>(this.collectionReference.whereEqualTo("status", INACTIVE), entityClass);
    }

    @Override
    public QueryFirebaseLiveData<Articulo> getEntitiesWithEliminatedRegistry() {
        return new QueryFirebaseLiveData<Articulo>(this.collectionReference.whereEqualTo("status", ELIMINATED), entityClass);
    }
}
