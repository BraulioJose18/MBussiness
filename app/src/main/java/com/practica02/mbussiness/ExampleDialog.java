package com.practica02.mbussiness;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.MarcaRepository;

public class ExampleDialog extends AppCompatDialogFragment {

    private static final String TAG = ExampleDialog.class.getSimpleName();
    private EditText code, name, status;
    Spinner spinnerEstado;

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

        builder.setView(view).setTitle("Añadir Item").setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MarcaRepository marcaRepository = MarcaRepository.getInstance();
                marcaRepository.save(new Marca(code.getText().toString(), name.getText().toString(), spinnerEstado.getSelectedItem().toString()));
                Marcas.cargarLista();
            }
        });


        return builder.create();

    }

    public void show(FragmentManager fragmentManager) {
    }
}
