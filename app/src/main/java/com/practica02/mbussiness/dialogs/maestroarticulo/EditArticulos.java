package com.practica02.mbussiness.dialogs.maestroarticulo;

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
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;

public class EditArticulos extends AppCompatDialogFragment {

    private static final String TAG = ViewArticulos.class.getSimpleName();
    private EditText code, name;
    Spinner spinnerEstado;
    private ArticuloViewModel viewModel;
    Articulo data;

    public EditArticulos(Articulo data){
        this.data = data;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_articulo, null);
        code = view.findViewById(R.id.codeArticulo);
        name = view.findViewById(R.id.nameArticulo);
        spinnerEstado = view.findViewById(R.id.spinnerEstadoArticulo);

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

        this.viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);

        builder
                .setView(view)
                .setTitle("Editar Información")
                .setPositiveButton("Actualizar", (dialog, which) -> {

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });

        code.setText(data.getCodigo());
        name.setText(data.getNombre());

        return builder.create();

    }
}
