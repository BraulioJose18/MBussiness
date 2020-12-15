package com.practica02.mbussiness.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
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
    private MultipleDocumentReferenceLiveData<Articulo, ? extends Query> allLiveDataWithRelationships;
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


    public MultipleDocumentReferenceLiveData<Articulo, ? extends Query> getAllListLiveDataWithMarcaAndUnidadMedida() {
        if (allLiveDataWithRelationships == null) {
            this.allLiveDataWithRelationships = this.repository.findAllWithMarcaAndUnidadMedida();
        }
        return this.allLiveDataWithRelationships;
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

    public Task<Void> delete(Articulo marca) {
        return this.repository.delete(marca);
    }
}
