package com.practica02.mbussiness;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.adapters.AdapterUnidadMedida;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.dialogs.marca.AddMarcas;
import com.practica02.mbussiness.dialogs.marca.DeleteMarcas;
import com.practica02.mbussiness.dialogs.marca.EditMarcas;
import com.practica02.mbussiness.dialogs.marca.ViewMarcas;
import com.practica02.mbussiness.dialogs.unidadmedida.AddUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.DeleteUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.EditUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.ViewUnidadMedida;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

import java.util.ArrayList;
import java.util.List;

public class UnidadMedidas extends Fragment {


    private static final String TAG = "UNIDAD MEDIDA";
    private static AdapterUnidadMedida adapterUnidadMedida;
    private static List<UnidadMedida> listaUnidadMedida;
    private UnidadMedidaViewModel viewModel;
    //AdapterMarca adapterMarca;
    RecyclerView rvUnidadMedida;
    //ArrayList<Marca> listaMarca;

    //FloatingActionButton addMarca;
    Button addUnidadMedida;
    SearchView searchUnidadMedida;

    TextView codigo, nombre, estado;


    public UnidadMedidas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_unidad_medida, container, false);
        rvUnidadMedida = vista.findViewById(R.id.rvUnidadMedida);
        //addMarca = vista.findViewById(R.id.addMarca);
        addUnidadMedida = vista.findViewById(R.id.adddUnidadMedida);
        searchUnidadMedida = vista.findViewById(R.id.searchUnidad);


        listaUnidadMedida = new ArrayList<>();

        addUnidadMedida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });


       /* addMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });*/
        viewModel = new ViewModelProvider(this).get(UnidadMedidaViewModel.class);
        rvUnidadMedida.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterUnidadMedida = new AdapterUnidadMedida(getContext());
        adapterUnidadMedida.setUnidadMedida(listaUnidadMedida);
        rvUnidadMedida.setAdapter(adapterUnidadMedida);
        adapterUnidadMedida.setOnViewClickDataListener(data -> {
            Log.e(TAG, data.toString());
            openDialogView(data);
        });
        adapterUnidadMedida.setOnEditClickDataListener(data -> {
            Log.e(TAG, data.toString());
            editDialogView(data);
        });
        adapterUnidadMedida.setOnDeleteClickDataListener(data -> {
            Log.e(TAG, data.toString());
            deleteDialogView();
        });
        viewModel.getAllListLiveData().observe(this.getViewLifecycleOwner(), unidadMedida -> {
            adapterUnidadMedida.setUnidadMedida(unidadMedida);
            adapterUnidadMedida.notifyDataSetChanged();
        });
        return vista;
    }

    public void openDialogAdd() {
        AddUnidadMedida addUnidadMedidaDialog = new AddUnidadMedida();
        addUnidadMedidaDialog.show(this.getParentFragmentManager(), "example dialog");
    }
    public void openDialogView(UnidadMedida unidadMedida) {
        ViewUnidadMedida viewUnidadMedidaDialog = new ViewUnidadMedida(unidadMedida);
        viewUnidadMedidaDialog.show(this.getParentFragmentManager(), "example dialog");
    }
    public void editDialogView(UnidadMedida unidadMedida) {
        EditUnidadMedida editUnidadMedidaDialog = new EditUnidadMedida(unidadMedida);
        editUnidadMedidaDialog.show(this.getParentFragmentManager(), "example dialog");
    }
    public void deleteDialogView() {
        DeleteUnidadMedida deleteUnidadMedidaDialog = new DeleteUnidadMedida();
        deleteUnidadMedidaDialog.show(this.getParentFragmentManager(), "example dialog");
    }
}