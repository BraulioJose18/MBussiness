package com.practica02.mbussiness.dialogs.marca;

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
import com.practica02.mbussiness.viewmodel.MarcaViewModel;

public class ViewMarcas extends AppCompatDialogFragment {

    private static final String TAG = ViewMarcas.class.getSimpleName();
    private EditText code, name;
    Spinner spinnerEstado;
    private MarcaViewModel viewModel;
    Marca data;

    public ViewMarcas(Marca data){
        this.data = data;
    }
    //Ya estoy mareado :v

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_marca, null);
        code = view.findViewById(R.id.code);
        name = view.findViewById(R.id.name);
        spinnerEstado = view.findViewById(R.id.spinnerEstado);
        //String [] opciones = {"activo","inactivo","eliminado"};

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
        spinnerEstado.setEnabled(false);
        this.viewModel = new ViewModelProvider(this).get(MarcaViewModel.class);

        builder
                .setView(view)
                .setTitle("Detalles")
                .setNegativeButton("Salir", (dialog, which) -> {
                });

        code.setText(data.getCodigo());
        name.setText(data.getNombre());

        code.setEnabled(false);
        name.setEnabled(false);


        return builder.create();

    }
}
