package com.practica02.mbussiness.dialogs.unidadmedida;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.practica02.mbussiness.R;

public class DeleteUnidadMedida extends AppCompatDialogFragment {

    private static final String TAG = DeleteUnidadMedida.class.getSimpleName();

    ImageView img;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        img = view.findViewById(R.id.imageViewDelete);
        img.setImageResource(R.drawable.ic_danger);

        //String [] opciones = {"activo","inactivo","eliminado"};

        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {

                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();

    }
}
