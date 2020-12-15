package com.practica02.mbussiness.dialogs.maestroarticulo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Entity;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ViewArticulos extends AppCompatDialogFragment {

    private static final String TAG = ViewArticulos.class.getSimpleName();
    private EditText code, name, precioUnitario;
    Spinner spinnerEstado, spinnerUnidadMedida, spinnerMarca;
    private ArticuloViewModel viewModel;
    private MarcaViewModel viewModelMarca;
    private UnidadMedidaViewModel viewModelUnidadMedida;
    Articulo data;

    public ViewArticulos(Articulo data) {
        this.data = data;
    }
    //Ya estoy mareado :v

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_articulo, null);
        code = view.findViewById(R.id.codeArticulo);
        name = view.findViewById(R.id.nameArticulo);
        spinnerEstado = view.findViewById(R.id.spinnerEstadoArticulo);
        precioUnitario = view.findViewById(R.id.precioArticulo);

        spinnerUnidadMedida = view.findViewById(R.id.spinnerUnidadMedida);
        spinnerMarca = view.findViewById(R.id.spinnerMarca);

        this.viewModelMarca = new ViewModelProvider(this).get(MarcaViewModel.class);
        MarcaArrayAdapter adapterMarca = new MarcaArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerMarca.setAdapter(adapterMarca);
        viewModelMarca.getActiveListLiveData().observe(this, marcas -> {
            adapterMarca.setMarcas(marcas);
            spinnerMarca.setSelection(marcas.stream().map(Entity::getDocumentId).collect(Collectors.toList()).indexOf(data.getMarcaId()));
            adapterMarca.notifyDataSetChanged();
        });


        this.viewModelUnidadMedida = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);
        UnidadMedidaArrayAdapter adapterUnidadMedida = new UnidadMedidaArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerUnidadMedida.setAdapter(adapterUnidadMedida);
        viewModelUnidadMedida.getActiveListLiveData().observe(this, unidadMedidas -> {
            adapterUnidadMedida.setUnidadMedidas(unidadMedidas);
            spinnerUnidadMedida.setSelection(unidadMedidas.stream().map(Entity::getDocumentId).collect(Collectors.toList()).indexOf(data.getUnidadMedidaId()));
            adapterUnidadMedida.notifyDataSetChanged();
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
        String estado = data.getStatus();
        int position;
        if (estado.equalsIgnoreCase("A")) {
            position = 0;
        } else if (estado.equalsIgnoreCase("I")) {
            position = 1;
        } else {
            position = 2;
        }
        spinnerEstado.setSelection(position);
        spinnerEstado.setEnabled(false);
        this.viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);

        builder
                .setView(view)
                .setTitle("Detalles")
                .setNegativeButton("Salir", (dialog, which) -> {
                });

        code.setText(data.getCodigo());
        name.setText(data.getNombre());
        String precio = data.getPrecioUnitario() + "";
        precioUnitario.setText(precio);

        code.setEnabled(false);
        name.setEnabled(false);
        precioUnitario.setEnabled(false);
        spinnerMarca.setEnabled(false);
        spinnerUnidadMedida.setEnabled(false);

        //spinnerMarca.setSelection(spinnerMarca.getSelectedItemPosition());

        //spinnerUnidadMedida.setSelection(spinnerUnidadMedida.getSelectedItemPosition());
        return builder.create();

    }
}
