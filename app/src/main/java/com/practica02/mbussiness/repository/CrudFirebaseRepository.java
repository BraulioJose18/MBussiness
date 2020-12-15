package com.practica02.mbussiness.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.practica02.mbussiness.clases.Entity;
import com.practica02.mbussiness.repository.livedata.DocumentReferenceFirebaseLiveData;
import com.practica02.mbussiness.repository.livedata.MultipleDocumentReferenceLiveData;

import java.util.List;

public interface CrudFirebaseRepository<E extends Entity, I> {
    /**
     * Son todos los datos de la entidad.
     *
     * @return LiveData de los documentos referenciados.
     */
    MultipleDocumentReferenceLiveData<E, ? extends Query> findAll();

    /**
     * Obtener la entidad de firebase.
     *
     * @param identifier Es el identificador.
     * @return LiveData del documento referenciado.
     */
    DocumentReferenceFirebaseLiveData<E> findById(I identifier);

    /**
     * Guardar datos en la Firebase.
     *
     * @param entity Es la entidad a ser persistida en Firebase.
     */
    void save(E entity);

    /**
     * Guardar datos en la Firebase.
     *
     * @param entities Es la entidad a ser persistida en Firebase.
     */
    void saveAll(List<E> entities);

    /**
     * Actualiza los datos de Firebase.
     *
     * @param entity Es la actualización de la entidad.
     */
    void update(E entity);

    /**
     * Actualizar datos en la Firebase.
     *
     * @param entities Es la entidad a ser actualizada en Firebase.
     */
    void updateAll(List<E> entities) throws IllegalAccessException;

    /**
     * Borrar los datos de Firebase.
     *
     * @param entity Es la entidad a borrar.
     */
    Task<Void> delete(E entity);

    /**
     * Borrar datos en la Firebase.
     *
     * @param entities Es la entidad a ser borrada en Firebase.
     */
    void deleteAll(List<E> entities);
}
