package com.practica02.mbussiness.dialogs.unidadmedida;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.Marcas;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.UnidadMedidas;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

public class DeleteUnidadMedida extends AppCompatDialogFragment {

    private static final String TAG = DeleteUnidadMedida.class.getSimpleName();

    ImageView img;

    private UnidadMedidaViewModel viewModel;
    UnidadMedida data;

    public DeleteUnidadMedida (UnidadMedida data){
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

        this.viewModel = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);

        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    viewModel.delete(data).addOnCompleteListener((task)->{
                        UnidadMedidas.adapterUnidadMedida.notifyDataSetChanged();
                    });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();

    }
}
