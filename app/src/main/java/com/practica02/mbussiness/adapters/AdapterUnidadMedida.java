package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.UnidadMedidas;
import com.practica02.mbussiness.clases.Marca;
import com.practica02.mbussiness.clases.UnidadMedida;
import com.practica02.mbussiness.repository.RequiredOperation;
import com.practica02.mbussiness.utils.OnClickDataListener;
import lombok.Setter;

import java.util.List;

public class AdapterUnidadMedida extends RecyclerView.Adapter<AdapterUnidadMedida.ViewHolder> {

    LayoutInflater inflater;
    List<UnidadMedida> unidadMedidaList;

    @Setter
    private OnClickDataListener<UnidadMedida> onEditClickDataListener;
    @Setter
    private OnClickDataListener<UnidadMedida> onViewClickDataListener;
    @Setter
    private OnClickDataListener<UnidadMedida> onDeleteClickDataListener;

    public AdapterUnidadMedida(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setUnidadMedida(List<UnidadMedida> unidadMedidaList) {
        this.unidadMedidaList= unidadMedidaList;
    }

    @NonNull
    @Override
    public AdapterUnidadMedida.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_unidad_medida, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterUnidadMedida.ViewHolder holder, int position) {
        String codigo = unidadMedidaList.get(position).getCodigo();
        String nombre = unidadMedidaList.get(position).getNombre();
        String status = "";
        String registryState = unidadMedidaList.get(position).getStatus();
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
        holder.bVer.setOnClickListener(v -> onViewClickDataListener.onClickDataOperation(unidadMedidaList.get(position)));
        holder.bModificar.setOnClickListener(v -> onEditClickDataListener.onClickDataOperation(unidadMedidaList.get(position)));
        holder.bEliminar.setOnClickListener(v -> onDeleteClickDataListener.onClickDataOperation(unidadMedidaList.get(position)));
    }

    @Override
    public int getItemCount() {
        return unidadMedidaList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "ViewHolder";
        TextView codigo, nombre, status;
        Button bVer, bModificar, bEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codUnidadMedida);
            nombre = itemView.findViewById(R.id.nombreUnidadMedida);
            status = itemView.findViewById(R.id.statusUnidadMedida);
            bVer = itemView.findViewById(R.id.bVerUnidadMedida);
            bModificar = itemView.findViewById(R.id.bModificarUnidadMedida);
            bEliminar = itemView.findViewById(R.id.bEliminarUnidadMedida);
        }
    }

}
