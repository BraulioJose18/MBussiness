package com.practica02.mbussiness.repository.livedata;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.*;
import com.practica02.mbussiness.clases.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentReferenceFirebaseLiveData<T extends Entity> extends MutableLiveData<T> implements EventListener<DocumentSnapshot> {

    protected static String TAG = DocumentReferenceFirebaseLiveData.class.getSimpleName();
    // Firebase Utils.
    private final DocumentReference documentReference;
    protected ListenerRegistration listenerRegistration = () -> {
    };
    // Entity Utils
    protected T entity;
    protected final Class<T> entityClass;

    public DocumentReferenceFirebaseLiveData(DocumentReference documentReference, Class<T> entityClass) {
        this.documentReference = documentReference;
        this.entityClass = entityClass;
    }

    @Override
    protected void onActive() {
        this.listenerRegistration = this.documentReference.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        this.listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
        if (documentSnapshot != null && !documentSnapshot.exists()) {
            Log.e(TAG, "Updating");
            this.entity = null;
            this.entity = documentSnapshot.toObject(this.entityClass);
            this.entity.setDocumentId(documentSnapshot.getId());
            this.setValue(entity);
        } else if (error != null) {
            Log.e(TAG, error.getMessage(), error.getCause());
        }
    }
}
