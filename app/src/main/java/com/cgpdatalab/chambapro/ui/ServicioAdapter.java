package com.cgpdatalab.chambapro.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.model.Servicio;
import com.cgpdatalab.chambapro.util.Formato;

import java.util.List;

/** Adaptador de la lista de servicios. */
public class ServicioAdapter extends RecyclerView.Adapter<ServicioAdapter.ViewHolder> {

    public interface OnServicioClick {
        void onClick(Servicio servicio);
    }

    private List<Servicio> datos;
    private final OnServicioClick listener;

    public ServicioAdapter(List<Servicio> datos, OnServicioClick listener) {
        this.datos = datos;
        this.listener = listener;
    }

    public void actualizar(List<Servicio> nuevos) {
        this.datos = nuevos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Servicio s = datos.get(position);
        holder.tvNombre.setText(s.getNombre());
        holder.tvCategoria.setText(s.getCategoria());
        holder.tvProveedor.setText(holder.itemView.getContext()
                .getString(R.string.detalle_proveedor, s.getProveedor(), s.getCiudad()));
        holder.tvPrecio.setText(Formato.precio(s.getPrecio()));
        holder.itemView.setOnClickListener(v -> listener.onClick(s));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvNombre;
        final TextView tvCategoria;
        final TextView tvProveedor;
        final TextView tvPrecio;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvCategoria = itemView.findViewById(R.id.tvCategoria);
            tvProveedor = itemView.findViewById(R.id.tvProveedor);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
