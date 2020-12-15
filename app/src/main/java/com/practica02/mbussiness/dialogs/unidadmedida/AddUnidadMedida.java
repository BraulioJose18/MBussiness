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

public class AddUnidadMedida extends AppCompatDialogFragment {

    private static final String TAG = AddUnidadMedida.class.getSimpleName();
    private EditText code, name;
    Spinner spinnerEstado;
    private UnidadMedidaViewModel viewModel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_unidad, null);
        code = view.findViewById(R.id.codeUnidad);
        name = view.findViewById(R.id.nameUnidad);
        spinnerEstado = view.findViewById(R.id.spinnerEstadoUnidad);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.combo_status, android.R.layout.simple_spinner_item);
        spinnerEstado.setAdapter(adapter);
        this.viewModel = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);

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
                    viewModel.saveOrUpdate(new UnidadMedida(code.getText().toString(), name.getText().toString(), registryState));
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();

    }
}
