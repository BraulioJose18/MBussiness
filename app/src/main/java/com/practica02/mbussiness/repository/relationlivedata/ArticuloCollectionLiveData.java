package com.practica02.mbussiness.repository.relationlivedata;

import android.os.Build;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.repository.MarcaRepository;
import com.practica02.mbussiness.repository.UnidadMedidaRepository;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

public class ArticuloCollectionLiveData<L extends Query> extends MultipleDocumentReferenceLiveData<Articulo, L> {
    private final MarcaRepository marcaRepository;
    private final UnidadMedidaRepository unidadMedidaRepository;

    public ArticuloCollectionLiveData(L multipleDocuments, MarcaRepository marcaRepository, UnidadMedidaRepository unidadMedidaRepository) {
        super(multipleDocuments, Articulo.class);
        this.marcaRepository = marcaRepository;
        this.unidadMedidaRepository = unidadMedidaRepository;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
        if (querySnapshot != null && !querySnapshot.isEmpty()) {
            Log.e(TAG, "Updating");
            this.entityList.clear();
            this.entityList.addAll(querySnapshot.toObjects(Articulo.class));
            for (int i = 0; i < querySnapshot.size(); i++) {
                Log.e(TAG, "Changing Data on Article");
                this.entityList.get(i).setDocumentId(querySnapshot.getDocuments().get(i).getId());
                this.entityList.get(i).setMarcaLiveData(this.marcaRepository.findById(this.entityList.get(i).getMarcaId()));
                this.entityList.get(i).setUnidadMedidaLiveData(this.unidadMedidaRepository.findById(this.entityList.get(i).getUnidadMedidaId()));
            }
            // Updating livedata.
            this.setValue(entityList);
        } else if (error != null) {
            Log.e(TAG, error.getMessage(), error.getCause());
        }
    }
}
