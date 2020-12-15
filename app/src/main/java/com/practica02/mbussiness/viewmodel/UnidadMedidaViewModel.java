package com.practica02.mbussiness.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.MarcaRepository;
import com.practica02.mbussiness.repository.UnidadMedidaRepository;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

public class UnidadMedidaViewModel extends ViewModel {
    private final UnidadMedidaRepository repository;
    private MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> allLiveData;
    private MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> activeLiveData;

    public UnidadMedidaViewModel() {
        this.repository = UnidadMedidaRepository.getInstance();
    }

    public MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> getAllListLiveData() {
        if (allLiveData == null) {
            this.allLiveData = this.repository.findAll();
        }
        return this.allLiveData;
    }

    public DocumentReferenceFirebaseLiveData<UnidadMedida> getLiveData(String id) {
        return this.repository.findById(id);
    }

    public MultipleDocumentReferenceLiveData<UnidadMedida, ? extends Query> getActiveListLiveData() {
        if (activeLiveData == null) {
            this.activeLiveData = this.repository.getEntitiesWithActiveRegistry();
        }
        return activeLiveData;
    }

    public void saveOrUpdate(UnidadMedida marca) {
        if (marca.getDocumentId() != null && !marca.getDocumentId().isEmpty()) {
            this.repository.update(marca);
        } else {
            this.repository.save(marca);
        }
    }

    public void delete(UnidadMedida marca) {
        this.repository.delete(marca);
    }
}
