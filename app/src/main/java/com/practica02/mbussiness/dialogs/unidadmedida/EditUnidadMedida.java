package com.practica02.mbussiness.dialogs.unidadmedida;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

public class EditUnidadMedida extends AppCompatDialogFragment {

    private static final String TAG = ViewUnidadMedida.class.getSimpleName();
    private EditText code, name;
    Spinner spinnerEstado;
    private UnidadMedidaViewModel viewModel;
    UnidadMedida data;

    public EditUnidadMedida(UnidadMedida data){
        this.data = data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_marca, null);
        code = view.findViewById(R.id.code);
        name = view.findViewById(R.id.name);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
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

        this.viewModel = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);

        builder
                .setView(view)
                .setTitle("Editar Información")
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
                    data.setCodigo(code.getText().toString());
                    data.setNombre(name.getText().toString());
                    data.setStatus(registryState);
                    viewModel.saveOrUpdate(data);

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });

        code.setText(data.getCodigo());
        name.setText(data.getNombre());

        return builder.create();

    }
}
