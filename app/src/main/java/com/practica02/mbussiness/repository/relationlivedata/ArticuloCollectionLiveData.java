package com.practica02.mbussiness.repository.relationlivedata;

import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.MarcaRepository;
import com.practica02.mbussiness.repository.UnidadMedidaRepository;
import com.practica02.mbussiness.repository.livedata.CollectionReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;

import java.util.ArrayList;
import java.util.List;

public class ArticuloCollectionLiveData extends CollectionReferenceFirebaseLiveData<Articulo> {
    private final MarcaRepository marcaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;
    private final List<ListenerRegistration> marcaListenerRegistrationList;
    private final List<ListenerRegistration> unidadMedidaListenerRegistrationList;

    public ArticuloCollectionLiveData(CollectionReference collectionReference, MarcaRepository marcaRepository, UnidadMedidaRepository unidadMedidaRepository) {
        super(collectionReference, Articulo.class);
        this.marcaRepository = marcaRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
        this.marcaListenerRegistrationList = new ArrayList<>();
        this.unidadMedidaListenerRegistrationList = new ArrayList<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
        if (querySnapshot != null && !querySnapshot.isEmpty()) {
            Log.e(TAG, "Updating");
            this.entityList.clear();
            this.entityList.addAll(querySnapshot.toObjects(Articulo.class));
            for (int i = 0; i < querySnapshot.size(); i++) {
                this.entityList.get(i).setDocumentId(querySnapshot.getDocuments().get(i).getId());
            }
            // Cleaning snapshot listener.
            this.cleanSnapshotListeners();
            // Adding new snapshot listener.
            this.entityList.parallelStream()
                    .forEach(articulo -> {
                        DocumentReferenceFirebaseLiveData<Marca> marcaLiveData = marcaRepository.findById(articulo.getMarcaId());
                        marcaListenerRegistrationList.add(marcaLiveData.getDocumentReference().addSnapshotListener((value, error1) -> {
                            if (value != null) {
                                articulo.setMarca(value.toObject(Marca.class));
                            } else if (error != null) {
                                Log.e(TAG, error.getMessage(), error.getCause());
                            }
                        }));
                        DocumentReferenceFirebaseLiveData<UnidadMedida> unidadMedidaLiveData = unidadMedidaRepository.findById(articulo.getUnidadMedidaId());
                        unidadMedidaListenerRegistrationList.add(unidadMedidaLiveData.getDocumentReference().addSnapshotListener((value, error1) -> {
                            if (value != null) {
                                articulo.setUnidadMedida(value.toObject(UnidadMedida.class));
                            } else if (error != null) {
                                Log.e(TAG, error.getMessage(), error.getCause());
                            }
                        }));
                    });
            this.setValue(entityList);
        } else if (error != null) {
            Log.e(TAG, error.getMessage(), error.getCause());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void cleanSnapshotListeners() {
        this.marcaListenerRegistrationList.parallelStream().forEach(ListenerRegistration::remove);
        this.unidadMedidaListenerRegistrationList.parallelStream().forEach(ListenerRegistration::remove);
        this.marcaListenerRegistrationList.clear();
        this.unidadMedidaListenerRegistrationList.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onInactive() {
        this.cleanSnapshotListeners();
        super.onInactive();
    }
}
