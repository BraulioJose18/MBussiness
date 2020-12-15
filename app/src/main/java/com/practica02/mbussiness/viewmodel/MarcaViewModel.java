package com.practica02.mbussiness.viewmodel;

import androidx.lifecycle.ViewModel;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.MarcaRepository;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

public class MarcaViewModel extends ViewModel {
    private final MarcaRepository repository;
    private MultipleDocumentReferenceLiveData<Marca, ? extends Query> allLiveData;
    private MultipleDocumentReferenceLiveData<Marca, ? extends Query> activeLiveData;

    public MarcaViewModel() {
        this.repository = MarcaRepository.getInstance();
    }

    public MultipleDocumentReferenceLiveData<Marca, ? extends Query> getAllListLiveData() {
        if (allLiveData == null) {
            this.allLiveData = this.repository.findAll();
        }
        return this.allLiveData;
    }

    public DocumentReferenceFirebaseLiveData<Marca> getLiveData(String id) {
        return this.repository.findById(id);
    }

    public MultipleDocumentReferenceLiveData<Marca, ? extends Query> getActiveListLiveData() {
        if (activeLiveData == null) {
            this.activeLiveData = this.repository.getEntitiesWithActiveRegistry();
        }
        return activeLiveData;
    }

    public void saveOrUpdate(Marca marca) {
        if (marca.getDocumentId() != null && !marca.getDocumentId().isEmpty()) {
            this.repository.update(marca);
        } else {
            this.repository.save(marca);
        }
    }

    public void delete(Marca marca) {
        this.repository.delete(marca);
    }
}
