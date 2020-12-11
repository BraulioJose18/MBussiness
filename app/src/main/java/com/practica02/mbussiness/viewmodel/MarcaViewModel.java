package com.practica02.mbussiness.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.MarcaRepository;
import com.practica02.mbussiness.repository.livedata.CollectionReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.QueryFirebaseLiveData;

public class MarcaViewModel extends ViewModel {
    private final MarcaRepository repository;
    private CollectionReferenceFirebaseLiveData<Marca> allLiveData;
    private QueryFirebaseLiveData<Marca> activeLiveData;

    public MarcaViewModel() {
        this.repository = MarcaRepository.getInstance();
    }

    public CollectionReferenceFirebaseLiveData<Marca> getAllListLiveData() {
        if (allLiveData == null) {
            this.allLiveData = this.repository.findAll();
        }
        return this.allLiveData;
    }

    public DocumentReferenceFirebaseLiveData<Marca> getLiveData(String id) {
        return this.repository.findById(id);
    }

    public QueryFirebaseLiveData<Marca> getActiveListLiveData() {
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
}
