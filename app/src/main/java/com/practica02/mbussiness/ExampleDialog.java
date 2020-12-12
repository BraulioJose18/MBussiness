package com.practica02.mbussiness;

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

import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;

public class ExampleDialog extends AppCompatDialogFragment {

    private static final String TAG = ExampleDialog.class.getSimpleName();
    private EditText code, name, status;
    Spinner spinnerEstado;
    private MarcaViewModel viewModel;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_marca, null);
        code = view.findViewById(R.id.code);
        name = view.findViewById(R.id.name);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);

        //String [] opciones = {"activo","inactivo","eliminado"};

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
        this.viewModel = new ViewModelProvider(this).get(MarcaViewModel.class);

        builder.setView(view).setTitle("Añadir Item").setPositiveButton("Añadir", (dialog, which) -> {
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
            viewModel.saveOrUpdate(new Marca(code.getText().toString(), name.getText().toString(), registryState));
        }).setNegativeButton("Cancelar", (dialog, which) -> {
        });


        return builder.create();

    }
}
