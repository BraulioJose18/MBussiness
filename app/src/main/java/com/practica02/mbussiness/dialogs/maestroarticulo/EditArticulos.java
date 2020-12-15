package com.practica02.mbussiness.dialogs.maestroarticulo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
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

public class EditArticulos extends AppCompatDialogFragment {

    private static final String TAG = AddArticulos.class.getSimpleName();
    private EditText code, name, precioUnitario;
    Spinner spinnerEstado, spinnerUnidadMedida, spinnerMarca;
    private ArticuloViewModel viewModel;
    private MarcaViewModel viewModelMarca;
    private UnidadMedidaViewModel viewModelUnidadMedida;
    Articulo data;

    public EditArticulos(Articulo data){
        this.data = data;
    }

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
        viewModelMarca.getAllListLiveData().observe(this, marcas -> {
            adapterMarca.setMarcas(marcas);
            adapterMarca.notifyDataSetChanged();
        });


        this.viewModelUnidadMedida = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);
        UnidadMedidaArrayAdapter adapterUnidadMedida = new UnidadMedidaArrayAdapter(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerUnidadMedida.setAdapter(adapterUnidadMedida);
        viewModelUnidadMedida.getAllListLiveData().observe(this, unidadMedidas -> {
            adapterUnidadMedida.setUnidadMedidas(unidadMedidas);
            adapterUnidadMedida.notifyDataSetChanged();
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
        this.viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);

        String estado = data.getStatus();
        int position;
        if(estado.equalsIgnoreCase("A")){
            position = 0;
        }else if(estado.equalsIgnoreCase("I")){
            position = 1;
        }else{
            position = 2;
        }
        spinnerEstado.setSelection(position);

        builder
                .setView(view)
                .setTitle("Editar Artículo")
                .setPositiveButton("Actualizar", (dialog, which) -> {
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
                    //viewModel.saveOrUpdate(new Articulo(code.getText().toString(), name.getText().toString(), 12.0, registryState, marca.getDocumentId(), unidadMedida.getDocumentId()));
                    data.setCodigo(code.getText().toString());
                    data.setNombre(name.getText().toString());
                    data.setStatus(registryState);
                    data.setPrecioUnitario(Double.parseDouble(precioUnitario.getText().toString()));
                    data.setMarcaId(marca.getDocumentId());
                    data.setUnidadMedidaId(unidadMedida.getDocumentId());
                    viewModel.saveOrUpdate(data);
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });

        code.setText(data.getCodigo());
        name.setText(data.getNombre());
        String precio = data.getPrecioUnitario()+"";
        precioUnitario.setText(precio);
        spinnerMarca.setSelection(spinnerMarca.getSelectedItemPosition());
        spinnerUnidadMedida.setSelection(spinnerUnidadMedida.getSelectedItemPosition());
        return builder.create();
    }
}
