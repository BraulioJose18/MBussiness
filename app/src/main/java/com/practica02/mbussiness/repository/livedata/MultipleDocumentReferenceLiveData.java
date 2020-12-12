package com.practica02.mbussiness.repository.livedata;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import com.google.firebase.firestore.*;
import com.practica02.mbussiness.clases.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MultipleDocumentReferenceLiveData<T extends Entity, L extends Query> extends LiveData<List<T>> implements EventListener<QuerySnapshot> {

    protected static String TAG = MultipleDocumentReferenceLiveData.class.getSimpleName();
    // Firebase Utils.
    private final L multipleDocuments;
    protected ListenerRegistration listenerRegistration = () -> {
    };
    // Entity Utils.
    protected final List<T> entityList = new ArrayList<>();
    private final Class<T> entityClass;

    public MultipleDocumentReferenceLiveData(L multipleDocuments, Class<T> entityClass) {
        this.multipleDocuments = multipleDocuments;
        this.entityClass = entityClass;
    }

    @Override
    protected void onActive() {
        this.listenerRegistration = this.multipleDocuments.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        this.listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
        if (querySnapshot != null && !querySnapshot.isEmpty()) {
            Log.e(TAG, "Updating");
            this.entityList.clear();
            this.entityList.addAll(querySnapshot.toObjects(this.entityClass));
            for (int i = 0; i < querySnapshot.size(); i++) {
                this.entityList.get(i).setDocumentId(querySnapshot.getDocuments().get(i).getId());
            }
            this.setValue(entityList);
        } else if (error != null) {
            Log.e(TAG, error.getMessage(), error.getCause());
        }
    }
}
