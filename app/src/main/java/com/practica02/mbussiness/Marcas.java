package com.practica02.mbussiness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.practica02.mbussiness.adapters.AdapterMarca;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.MarcaRepository;

import java.util.ArrayList;

public class Marcas extends Fragment {


    private static AdapterMarca adapterMarca;
    private static ArrayList<Marca> listaMarca;
    //AdapterMarca adapterMarca;
    RecyclerView rvMarcas;
    //ArrayList<Marca> listaMarca;

    FloatingActionButton addMarca;

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
        addMarca = vista.findViewById(R.id.addMarca);


        listaMarca = new ArrayList<>();
        //cargar lista
        cargarLista();
        //mostrar datos
        mostrarDatos();

        addMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        rvMarcas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMarca = new AdapterMarca(getContext());
        adapterMarca.setMarca(new ArrayList<>());
        rvMarcas.setAdapter(adapterMarca);

        return vista;
    }

    public void openDialog() {
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getFragmentManager(), "example dialog");
    }

    public static void cargarLista() {
        MarcaRepository marcaRepository = MarcaRepository.getInstance();
        marcaRepository.findAll().addOnCompleteListener(task -> {
            listaMarca.clear();
            listaMarca.addAll(task.getResult());
            adapterMarca.setMarca(listaMarca);
            adapterMarca.notifyDataSetChanged();
        });
    }

    public void mostrarDatos() {
        //rvMarcas.setLayoutManager(new LinearLayoutManager(getContext()));
        //adapterMarca = new AdapterMarca(getContext(), listaMarca);
        //rvMarcas.setAdapter(adapterMarca);
    }
}