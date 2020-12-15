package com.practica02.mbussiness.dialogs.maestroarticulo;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class UnidadMedidaArrayAdapter extends ArrayAdapter<String> {
    @Getter
    public List<UnidadMedida> unidadMedidas;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UnidadMedidaArrayAdapter(@NonNull Context context, int resource, @NonNull List<UnidadMedida> unidadMedidas) {
        super(context, resource, unidadMedidas.parallelStream().map(UnidadMedida::getNombre).collect(Collectors.toList()));
        this.unidadMedidas = unidadMedidas;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setUnidadMedidas(List<UnidadMedida> unidadMedidas) {
        this.clear();
        this.addAll(unidadMedidas.parallelStream().map(UnidadMedida::getNombre).collect(Collectors.toList()));
        this.unidadMedidas = unidadMedidas;
    }
}
