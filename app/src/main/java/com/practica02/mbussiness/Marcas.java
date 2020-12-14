package com.practica02.mbussiness;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practica02.mbussiness.adapters.AdapterMarca;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;

import java.util.ArrayList;
import java.util.List;

public class Marcas extends Fragment {


    private static final String TAG = "MARCA";
    private static AdapterMarca adapterMarca;
    private static List<Marca> listaMarca;
    private MarcaViewModel viewModel;
    //AdapterMarca adapterMarca;
    RecyclerView rvMarcas;
    //ArrayList<Marca> listaMarca;

    //FloatingActionButton addMarca;
    Button adddMarca;

    TextView codigo, nombre, estado;


    public Marcas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_marcas, container, false);
        rvMarcas = vista.findViewById(R.id.rvMarcas);
        //addMarca = vista.findViewById(R.id.addMarca);
        adddMarca = vista.findViewById(R.id.adddMarca);

        listaMarca = new ArrayList<>();

        adddMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });


       /* addMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });*/
        viewModel = new ViewModelProvider(this).get(MarcaViewModel.class);
        rvMarcas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMarca = new AdapterMarca(getContext());
        adapterMarca.setMarca(listaMarca);
        rvMarcas.setAdapter(adapterMarca);
        adapterMarca.setOnViewClickDataListener(data -> {
            Log.e(TAG, data.toString());
            openDialog();
        });
        viewModel.getAllListLiveData().observe(this.getViewLifecycleOwner(), marcas -> {
            adapterMarca.setMarca(marcas);
            adapterMarca.notifyDataSetChanged();
        });
        return vista;
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(this.getParentFragmentManager(), "example dialog");
    }
}