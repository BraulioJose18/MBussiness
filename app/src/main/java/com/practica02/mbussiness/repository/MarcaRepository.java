package com.practica02.mbussiness.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.practica02.mbussiness.clases.Marca;

import java.util.Optional;

public class MarcaRepository extends FireStoreRepository<Marca> {
    private static MarcaRepository INSTANCE;

    public synchronized static MarcaRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new MarcaRepository();
        return INSTANCE;
    }

    private MarcaRepository() {
        super(Marca.class);
        this.TAG = MarcaRepository.class.getSimpleName();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Task<Optional<Marca>> findById(String identifier) {
        return this.collectionReference.whereEqualTo("codigo", identifier).get().continueWithTask(task -> Tasks.forResult(Optional.of(task.getResult().toObjects(entityClass).get(0))));
    }
}
