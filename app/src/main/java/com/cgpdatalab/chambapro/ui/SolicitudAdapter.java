package com.cgpdatalab.chambapro.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cgpdatalab.chambapro.R;
import com.cgpdatalab.chambapro.model.Solicitud;
import com.cgpdatalab.chambapro.util.Formato;

import java.util.List;

/** Adaptador de la lista de solicitudes. */
public class SolicitudAdapter extends RecyclerView.Adapter<SolicitudAdapter.ViewHolder> {

    private final List<Solicitud> datos;

    public SolicitudAdapter(List<Solicitud> datos) {
        this.datos = datos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_solicitud, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Solicitud s = datos.get(position);
        holder.tvServicio.setText(s.getServicio());
        holder.tvProveedor.setText(s.getProveedor());
        holder.tvPrecioFecha.setText(holder.itemView.getContext()
                .getString(R.string.solicitud_precio_fecha, Formato.precio(s.getPrecio()), s.getFecha()));
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView tvServicio;
        final TextView tvProveedor;
        final TextView tvPrecioFecha;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            tvProveedor = itemView.findViewById(R.id.tvProveedor);
            tvPrecioFecha = itemView.findViewById(R.id.tvPrecioFecha);
        }
    }
}
