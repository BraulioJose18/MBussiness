package com.practica02.mbussiness.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.ArticuloRepository;
import com.practica02.mbussiness.repository.UnidadMedidaRepository;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

public class ArticuloViewModel extends ViewModel {
    private final ArticuloRepository repository;
    private MultipleDocumentReferenceLiveData<Articulo, ? extends Query> allLiveData;
    private MultipleDocumentReferenceLiveData<Articulo, ? extends Query> activeLiveData;
    private MultipleDocumentReferenceLiveData<Articulo, ? extends Query> activeLiveDataWithRelationships;

    public ArticuloViewModel() {
        this.repository = ArticuloRepository.getInstance();
    }

    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getAllListLiveData() {
        if (allLiveData == null) {
            this.allLiveData = this.repository.findAll();
        }
        return this.allLiveData;
    }

    public DocumentReferenceFirebaseLiveData<Articulo> getLiveData(String id) {
        return this.repository.findById(id);
    }

    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getAllActiveListLiveData() {
        if (activeLiveData == null) {
            this.activeLiveData = this.repository.getEntitiesWithActiveRegistry();
        }
        return activeLiveData;
    }

    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getAllActiveListLiveDataWithMarcaAndUnidadMedida() {
        if (activeLiveDataWithRelationships == null) {
            this.activeLiveDataWithRelationships = this.repository.findAllActiveRegistryWithMarcaAndUnidadMedida();
        }
        return activeLiveDataWithRelationships;
    }

    public void saveOrUpdate(Articulo marca) {
        if (marca.getDocumentId() != null && !marca.getDocumentId().isEmpty()) {
            this.repository.update(marca);
        } else {
            this.repository.save(marca);
        }
    }

    public void delete(Articulo marca) {
        this.repository.delete(marca);
    }
}
