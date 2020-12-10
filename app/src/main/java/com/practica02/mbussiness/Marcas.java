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

import java.util.ArrayList;

public class Marcas extends Fragment {


    AdapterMarca adapterMarca;
    RecyclerView rvMarcas;
    ArrayList <Marca> listaMarca;

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
        rvMarcas=vista.findViewById(R.id.rvMarcas);
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


        return vista;
    }
    public void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getFragmentManager(),"example dialog");

    }
    public void cargarLista(){
        listaMarca.add(new Marca("M001","Weber","activo"));
        listaMarca.add(new Marca("M002","Mr Grill","inactivo"));
        listaMarca.add(new Marca("M003","Parrillero","eliminado"));
        listaMarca.add(new Marca("M004","Barbacoa","activo"));
        listaMarca.add(new Marca("M005","Disco","activo"));
        listaMarca.add(new Marca("M004","Cassete","inactivo"));
    }
    public void mostrarDatos(){
        rvMarcas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMarca = new AdapterMarca(getContext(),listaMarca);
        rvMarcas.setAdapter(adapterMarca);
    }
}