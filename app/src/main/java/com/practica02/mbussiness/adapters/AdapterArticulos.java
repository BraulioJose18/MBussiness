package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Articulo;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.utils.OnClickDataListener;

import java.util.List;
import java.util.Objects;

import lombok.Setter;

public class AdapterArticulos extends RecyclerView.Adapter<AdapterArticulos.ViewHolder> {

    LayoutInflater inflater;
    List<Articulo> articulosList;
    LifecycleOwner owner;

    @Setter
    private OnClickDataListener<Articulo> onEditClickDataListener;
    @Setter
    private OnClickDataListener<Articulo> onViewClickDataListener;
    @Setter
    private OnClickDataListener<Articulo> onDeleteClickDataListener;

    public AdapterArticulos(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setArticulosList(List<Articulo> articulosList) {
        this.articulosList = articulosList;
    }

    @NonNull
    @Override
    public AdapterArticulos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_maestro_articulo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterArticulos.ViewHolder holder, int position) {
        String codigo = articulosList.get(position).getCodigo();
        String nombre = articulosList.get(position).getNombre();
        if (articulosList.get(position).getUnidadMedida() != null) {
            holder.unidadMedida.setText(articulosList.get(position).getUnidadMedida().getNombre());
        } else {
            Log.e("TAG", "Es nulo");
        }
        if (articulosList.get(position).getMarca() != null) {
            holder.marca.setText(articulosList.get(position).getMarca().getNombre());
        } else {
            Log.e("TAG", "Es nulo");
        }
        String precio = articulosList.get(position).getPrecioUnitario() + "";
        String status = "";
        String registryState = articulosList.get(position).getStatus();
        if (registryState.equalsIgnoreCase(RequiredOperation.ACTIVE)) {
            status = "Activo";
        } else if (registryState.equalsIgnoreCase(RequiredOperation.INACTIVE)) {
            status = "Inactivo";
        } else if (registryState.equalsIgnoreCase(RequiredOperation.ELIMINATED)) {
            status = "Eliminado";
        }
        holder.codigo.setText(codigo);
        holder.nombre.setText(nombre);
        holder.precio.setText(precio);
        holder.status.setText(status);
        holder.bVer.setOnClickListener(v -> onViewClickDataListener.onClickDataOperation(articulosList.get(position)));
        holder.bModificar.setOnClickListener(v -> onEditClickDataListener.onClickDataOperation(articulosList.get(position)));
        holder.bEliminar.setOnClickListener(v -> onDeleteClickDataListener.onClickDataOperation(articulosList.get(position)));
    }

    @Override
    public int getItemCount() {
        return articulosList.size();
    }

    public void setOwner(LifecycleOwner viewLifecycleOwner) {
        this.owner = viewLifecycleOwner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "ViewHolder";
        TextView codigo, nombre, unidadMedida, precio, marca, status;
        Button bVer, bModificar, bEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codMaestroArticulo);
            nombre = itemView.findViewById(R.id.nombreMaestroArticulo);
            unidadMedida = itemView.findViewById(R.id.unidadMedidaMaestroArticulo);
            precio = itemView.findViewById(R.id.precioUnitarioMaestroArticulo);
            marca = itemView.findViewById(R.id.marcaMaestroArticulo);
            status = itemView.findViewById(R.id.statusMaestroArticulo);
            bVer = itemView.findViewById(R.id.bVerArticulo);
            bModificar = itemView.findViewById(R.id.bModificarArticulo);
            bEliminar = itemView.findViewById(R.id.bEliminarArticulo);
        }
    }
}
