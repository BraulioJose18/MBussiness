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
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddArticulos extends AppCompatDialogFragment {

    private static final String TAG = AddArticulos.class.getSimpleName();
    private EditText code, name, precioUnitario;
    Spinner spinnerEstado, spinnerUnidadMedida, spinnerMarca;
    private ArticuloViewModel viewModel;
    private MarcaViewModel viewModelMarca;
    private UnidadMedidaViewModel viewModelUnidadMedida;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_articulo, null);
        code = view.findViewById(R.id.codeArticulo);
        name = view.findViewById(R.id.nameArticulo);
        precioUnitario = view.findViewById(R.id.precioArticulo);

        spinnerEstado = view.findViewById(R.id.spinnerEstadoArticulo);

        spinnerUnidadMedida = view.findViewById(R.id.spinnerUnidadMedida);
        spinnerMarca = view.findViewById(R.id.spinnerMarca);

        this.viewModelMarca = new ViewModelProvider(this).get(MarcaViewModel.class);
        MarcaArrayAdapter adapterMarca = new MarcaArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerMarca.setAdapter(adapterMarca);
        viewModelMarca.getActiveListLiveData().observe(this, marcas -> {
            adapterMarca.setMarcas(marcas);
            adapterMarca.notifyDataSetChanged();
        });


        this.viewModelUnidadMedida = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);
        UnidadMedidaArrayAdapter adapterUnidadMedida = new UnidadMedidaArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerUnidadMedida.setAdapter(adapterUnidadMedida);
        viewModelUnidadMedida.getActiveListLiveData().observe(this, unidadMedidas -> {
            adapterUnidadMedida.setUnidadMedidas(unidadMedidas);
            adapterUnidadMedida.notifyDataSetChanged();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
        this.viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);

        builder
                .setView(view)
                .setTitle("Añadir Item")
                .setPositiveButton("Añadir", (dialog, which) -> {
                    String actualState = spinnerEstado.getSelectedItem().toString();
                    String registryState = "";
                    if (actualState.equalsIgnoreCase("Activo")) {
                        registryState = RequiredOperation.ACTIVE;
                    } else if (actualState.equalsIgnoreCase("Inactivo")) {
                        registryState = registryState = RequiredOperation.INACTIVE;
                    } else if (actualState.equalsIgnoreCase("Eliminado")) {
                        registryState = RequiredOperation.ELIMINATED;
                    } else {
                        registryState = RequiredOperation.ACTIVE;
                    }
                    Marca marca = adapterMarca.getMarcas().get(spinnerMarca.getSelectedItemPosition());
                    UnidadMedida unidadMedida = adapterUnidadMedida.getUnidadMedidas().get(spinnerUnidadMedida.getSelectedItemPosition());
                    double precio = Double.parseDouble(precioUnitario.getText().toString());
                    Log.e("Precio", precio + "gaaaaaaaaa");
                    viewModel.saveOrUpdate(new Articulo(code.getText().toString(), name.getText().toString(), precio, registryState, marca.getDocumentId(), unidadMedida.getDocumentId()));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();
    }
}
