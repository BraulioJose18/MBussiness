package com.practica02.mbussiness.dialogs.maestroarticulo;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.practica02.mbussiness.clases.Marca;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MarcaArrayAdapter extends ArrayAdapter<String> {
    @Getter
    public List<Marca> marcas;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MarcaArrayAdapter(@NonNull Context context, int resource, @NonNull List<Marca> marcas) {
        super(context, resource, marcas.parallelStream().map(Marca::getNombre).collect(Collectors.toList()));
        this.marcas = marcas;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setMarcas(List<Marca> marcas) {
        this.clear();
        this.addAll(marcas.parallelStream().map(Marca::getNombre).collect(Collectors.toList()));
        this.marcas = marcas;
    }
}
