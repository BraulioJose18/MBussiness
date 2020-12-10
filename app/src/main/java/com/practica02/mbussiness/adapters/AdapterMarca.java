package com.practica02.mbussiness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.practica02.mbussiness.R;
import com.practica02.mbussiness.clases.Marca;

import java.util.ArrayList;

public class AdapterMarca extends RecyclerView.Adapter<AdapterMarca.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    ArrayList<Marca> marca;

    //listener
    private View.OnClickListener listener;

    public AdapterMarca(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setMarca(ArrayList<Marca> marca) {
        this.marca = marca;
    }

    @NonNull
    @Override
    public AdapterMarca.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_marca, parent, false);
        view.setOnClickListener(this);

        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMarca.ViewHolder holder, int position) {

        String codigo = marca.get(position).getCodigo();
        String nombre = marca.get(position).getNombre();
        String status = marca.get(position).getStatus();
        holder.codigo.setText(codigo);
        holder.nombre.setText(nombre);
        holder.status.setText(status);
    }

    @Override
    public int getItemCount() {
        return marca.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView codigo, nombre, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.codMarca);
            nombre = itemView.findViewById(R.id.nombreMarca);
            status = itemView.findViewById(R.id.statusMarca);
        }
    }

}
