package com.practica02.mbussiness.dialogs.marca;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.practica02.mbussiness.MaestroArticulos;
import com.practica02.mbussiness.Marcas;
import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;

public class DeleteMarcas extends AppCompatDialogFragment {

    private static final String TAG = DeleteMarcas.class.getSimpleName();

    ImageView img;

    private MarcaViewModel viewModel;
    Marca data;

    public DeleteMarcas(Marca data){
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

        this.viewModel = new ViewModelProvider(this).get(MarcaViewModel.class);

        builder
                .setView(view)
                .setTitle("¿Seguro que desea eliminar?")
                .setPositiveButton("Confirmar", (dialog, which) -> {
                    viewModel.delete(data).addOnCompleteListener((task)->{
                        Marcas.adapterMarca.notifyDataSetChanged();
                    });
                })
                .setNegativeButton("Cancelar", (dialog, which) -> {
                });


        return builder.create();

    }
}
