package com.practica02.mbussiness.dialogs.maestroarticulo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.MaestroArticulos;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;

public class DeleteArticulos extends AppCompatDialogFragment {

    private static final String TAG = DeleteArticulos.class.getSimpleName();

    ImageView img;
    private ArticuloViewModel viewModel;
    Articulo data;

    public DeleteArticulos(Articulo data){
        this.data = data;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        img = view.findViewById(R.id.imageViewDelete);
        img.setImageResource(R.drawable.ic_danger);

        //String [] opciones = {"activo","inactivo","eliminado"};

        this.viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);

        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    viewModel.delete(data);
                    MaestroArticulos.adapterArticulos.notifyDataSetChanged();
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();

    }
}
