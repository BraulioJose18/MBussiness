package com.practica02.mbussiness.repository.livedata;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.practica02.mbussiness.clases.Entity;

import java.util.ArrayList;
import java.util.List;

public class CollectionReferenceFirebaseLiveData<T extends Entity> extends LiveData<List<T>> implements EventListener<QuerySnapshot> {
    protected static String TAG = CollectionReferenceFirebaseLiveData.class.getSimpleName();
    // Firebase Utils.
    private final CollectionReference collectionReference;
    protected ListenerRegistration listenerRegistration = () -> {
    };
    // Entity Utils.
    protected final List<T> entityList = new ArrayList<>();
    private final Class<T> entityClass;

    public CollectionReferenceFirebaseLiveData(CollectionReference collectionReference, Class<T> entityClass) {
        this.collectionReference = collectionReference;
        this.entityClass = entityClass;
    }

    @Override
    protected void onActive() {
        this.listenerRegistration = this.collectionReference.addSnapshotListener(this);
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
