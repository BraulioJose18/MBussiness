package com.practica02.mbussiness;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practica02.mbussiness.adapters.AdapterMarca;
import com.practica02.mbussiness.clases.Marca;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Marcas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Marcas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    AdapterMarca adapterMarca;
    RecyclerView rvMarcas;
    ArrayList <Marca> listaMarca;

    public Marcas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Marcas.
     */
    // TODO: Rename and change types and number of parameters
    public static Marcas newInstance(String param1, String param2) {
        Marcas fragment = new Marcas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_marcas, container, false);
        rvMarcas=vista.findViewById(R.id.rvMarcas);
        listaMarca = new ArrayList<>();
        //cargar lista
        cargarLista();
        //mostrar datos
        mostrarDatos();
        return vista;
    }
    public void cargarLista(){
        listaMarca.add(new Marca("M001","Weber","activo"));
        listaMarca.add(new Marca("M002","Mr Grill","inactivo"));
        listaMarca.add(new Marca("M003","Parrillero","reactivo"));
        listaMarca.add(new Marca("M004","Barbacoa","pasivo"));
    }
    public void mostrarDatos(){
        rvMarcas.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMarca = new AdapterMarca(getContext(),listaMarca);
        rvMarcas.setAdapter(adapterMarca);
    }
}