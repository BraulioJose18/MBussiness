package com.practica02.mbussiness.repository;

import android.util.Log;

import com.google.firebase.firestore.*;
import com.practica02.mbussiness.clases.Entity;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;
import com.practica02.mbussiness.utils.MapperObject;

import java.util.List;

public abstract class FirebaseRepository<E extends Entity> implements CrudFirebaseRepository<E, String> {
    private static final String TAG = FirebaseRepository.class.getSimpleName();
    protected final CollectionReference collectionReference;
    protected final Class<E> entityClass;

    public FirebaseRepository(Class<E> entityClass) {
        Log.e(TAG, "FIREBASE_REPOSITORY");
        this.entityClass = entityClass;
        this.collectionReference = FirebaseFirestore.getInstance().collection(this.entityClass.getSimpleName());
    }

    @Override
    public MultipleDocumentReferenceLiveData<E, CollectionReference> findAll() {
        return new MultipleDocumentReferenceLiveData<>(this.collectionReference, this.entityClass);
    }

    @Override
    public DocumentReferenceFirebaseLiveData<E> findById(String identifier) {
        Log.e(TAG, "findById()");
        return new DocumentReferenceFirebaseLiveData<>(this.collectionReference.document(identifier), this.entityClass);
    }

    @Override
    public void save(E entity) {
        Log.e(TAG, "save()");
        this.collectionReference.document().set(entity);
    }

    @Override
    public void saveAll(List<E> entities) {
        Log.e(TAG, "saveAll()");
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        for (E entity : entities) {
            DocumentReference documentReference = this.collectionReference.document();
            batch.set(documentReference, entity);
        }
        batch.commit();
    }

    @Override
    public void update(E entity) {
        Log.e(TAG, "update()");
        this.collectionReference.document(entity.getDocumentId()).set(entity);
    }

    @Override
    public void updateAll(List<E> entities) throws IllegalAccessException {
        Log.e(TAG, "updateAll()");
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        for (E entity : entities) {
            DocumentReference documentReference = this.collectionReference.document(entity.getDocumentId());
            batch.update(documentReference, MapperObject.convertMapToObject(entity));
        }
        batch.commit();
    }

    @Override
    public void delete(E entity) {
        Log.e(TAG, "delete()");
        this.collectionReference.document(entity.getDocumentId()).delete();
    }

    @Override
    public void deleteAll(List<E> entities) {
        Log.e(TAG, "deleteAll()");
        WriteBatch batch = FirebaseFirestore.getInstance().batch();
        for (E entity : entities) {
            batch.delete(this.collectionReference.document(entity.getDocumentId()));
        }
        batch.commit();
    }
}
