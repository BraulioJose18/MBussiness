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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.adapters.AdapterArticulos;
import com.practica02.mbussiness.adapters.AdapterMarca;
import com.practica02.mbussiness.adapters.AdapterUnidadMedida;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.dialogs.maestroarticulo.AddArticulos;
import com.practica02.mbussiness.dialogs.maestroarticulo.DeleteArticulos;
import com.practica02.mbussiness.dialogs.maestroarticulo.EditArticulos;
import com.practica02.mbussiness.dialogs.maestroarticulo.ViewArticulos;
import com.practica02.mbussiness.dialogs.marca.AddMarcas;
import com.practica02.mbussiness.dialogs.marca.DeleteMarcas;
import com.practica02.mbussiness.dialogs.marca.EditMarcas;
import com.practica02.mbussiness.dialogs.marca.ViewMarcas;
import com.practica02.mbussiness.dialogs.unidadmedida.AddUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.DeleteUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.EditUnidadMedida;
import com.practica02.mbussiness.dialogs.unidadmedida.ViewUnidadMedida;
import com.practica02.mbussiness.viewmodel.ArticuloViewModel;
import com.practica02.mbussiness.viewmodel.MarcaViewModel;
import com.practica02.mbussiness.viewmodel.UnidadMedidaViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MaestroArticulos extends Fragment {


    private static final String TAG = "MAESTRO ARTICULO";
    private static AdapterArticulos adapterArticulos;
    private static List<Articulo> listaArticulos;
    private ArticuloViewModel viewModel;
    //AdapterMarca adapterMarca;
    RecyclerView rvArticulo;
    //ArrayList<Marca> listaMarca;

    //FloatingActionButton addMarca;
    Button addArticulo;
    SearchView searchArticulo;

    TextView codigo, nombre, estado;


    public MaestroArticulos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_maestro_articulos, container, false);
        rvArticulo = vista.findViewById(R.id.rvMaestroArticulo);
        addArticulo = vista.findViewById(R.id.addMaestroArticulo);
        searchArticulo = vista.findViewById(R.id.searchMaestro);

        listaArticulos = new ArrayList<>();

        addArticulo.setOnClickListener(new View.OnClickListener() {
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
        viewModel = new ViewModelProvider(this).get(ArticuloViewModel.class);
        rvArticulo.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterArticulos = new AdapterArticulos(getContext());
        adapterArticulos.setOwner(this.getViewLifecycleOwner());
        adapterArticulos.setArticulosList(new ArrayList<>());
        rvArticulo.setAdapter(adapterArticulos);
        adapterArticulos.setOnViewClickDataListener(data -> {
            Log.e(TAG, data.toString());
            openDialogView(data);
        });
        adapterArticulos.setOnEditClickDataListener(data -> {
            Log.e(TAG, data.toString());
            editDialogView(data);
        });
        adapterArticulos.setOnDeleteClickDataListener(data -> {
            Log.e(TAG, data.toString());
            deleteDialogView();
        });
        LifecycleOwner owner = this.getViewLifecycleOwner();
        viewModel.getAllActiveListLiveDataWithMarcaAndUnidadMedida().observe(owner, article -> {
            Log.e(TAG, "UPDATE");
            adapterArticulos.setArticulosList(article);
            article.forEach(articulo -> {
                Log.e(TAG, "Short UPDATE");
                articulo.getUnidadMedidaLiveData().getDocumentReference().get().addOnCompleteListener(task -> {
                    articulo.setUnidadMedida(Objects.requireNonNull(task.getResult()).toObject(UnidadMedida.class));
                    adapterArticulos.notifyDataSetChanged();
                });
                articulo.getMarcaLiveData().getDocumentReference().get().addOnCompleteListener(task -> {
                    articulo.setMarca(Objects.requireNonNull(task.getResult()).toObject(Marca.class));
                    adapterArticulos.notifyDataSetChanged();
                });
            });
            adapterArticulos.notifyDataSetChanged();
        });
        return vista;
    }

    public void openDialogAdd() {
        AddArticulos addArticuloDialog = new AddArticulos();
        addArticuloDialog.show(this.getParentFragmentManager(), "example dialog");
    }

    public void openDialogView(Articulo articulo) {
        ViewArticulos viewArticulosDialog = new ViewArticulos(articulo);
        viewArticulosDialog.show(this.getParentFragmentManager(), "example dialog");
    }

    public void editDialogView(Articulo articulo) {
        EditArticulos editUnidadMedidaDialog = new EditArticulos(articulo);
        editUnidadMedidaDialog.show(this.getParentFragmentManager(), "example dialog");
    }

    public void deleteDialogView() {
        DeleteArticulos deleteArticulosDialog = new DeleteArticulos();
        deleteArticulosDialog.show(this.getParentFragmentManager(), "example dialog");
    }
}