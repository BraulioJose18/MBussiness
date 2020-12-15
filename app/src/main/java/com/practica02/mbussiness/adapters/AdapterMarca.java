package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.utils.OnClickDataListener;
import lombok.Setter;

import java.util.List;

public class AdapterMarca extends RecyclerView.Adapter<AdapterMarca.ViewHolder> {

    LayoutInflater inflater;
    List<Marca> marca;

    @Setter
    private OnClickDataListener<Marca> onEditClickDataListener;
    @Setter
    private OnClickDataListener<Marca> onViewClickDataListener;
    @Setter
    private OnClickDataListener<Marca> onDeleteClickDataListener;

    public AdapterMarca(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarca(List<Marca> marca) {
        this.marca = marca;
    }

    @NonNull
    @Override
    public AdapterMarca.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_marca, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMarca.ViewHolder holder, int position) {
        String codigo = marca.get(position).getCodigo();
        String nombre = marca.get(position).getNombre();
        String status = "";
        String registryState = marca.get(position).getStatus();
        if (registryState.equalsIgnoreCase(RequiredOperation.ACTIVE)) {
            status = "Activo";
        } else if (registryState.equalsIgnoreCase(RequiredOperation.INACTIVE)) {
            status = "Inactivo";
        } else if (registryState.equalsIgnoreCase(RequiredOperation.ELIMINATED)) {
            status = "Eliminado";
        }
        holder.codigo.setText(codigo);
        holder.nombre.setText(nombre);
        holder.status.setText(status);
        holder.bVer.setOnClickListener(v -> onViewClickDataListener.onClickDataOperation(marca.get(position)));
        holder.bModificar.setOnClickListener(v -> onEditClickDataListener.onClickDataOperation(marca.get(position)));
        holder.bEliminar.setOnClickListener(v -> onDeleteClickDataListener.onClickDataOperation(marca.get(position)));
    }

    @Override
    public int getItemCount() {
        return marca.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "ViewHolder";
        TextView codigo, nombre, status;
        Button bVer, bModificar, bEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codMarca);
            nombre = itemView.findViewById(R.id.nombreMarca);
            status = itemView.findViewById(R.id.statusMarca);
            bVer = itemView.findViewById(R.id.bVerMarca);
            bModificar = itemView.findViewById(R.id.bModificarMarca);
            bEliminar = itemView.findViewById(R.id.bEliminarMarca);
        }
    }

}
