package com.example.controldedispensador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TareasAdapter extends RecyclerView.Adapter<TareasAdapter.ViewHolder> {
    private final Context context;
    private final InterfaceTareas interfaceTareas;
    private List<Tareas> listaTareas;

    public TareasAdapter(Context context, List<Tareas> listaTareas, InterfaceTareas interfaceTareas) {
        this.context = context;
        this.listaTareas = listaTareas;
        this.interfaceTareas = interfaceTareas;
    }

    @NonNull
    @Override
    public TareasAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType ){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lista_tareas,parent,false);
        return new TareasAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull TareasAdapter.ViewHolder holder, int position) {
        holder.cv_id_tarea.setText(String.valueOf(listaTareas.get(position).getTask()));
        holder.cv_medicamento.setText(listaTareas.get(position).getMedicamento());
        holder.cv_cantidad.setText(String.valueOf(listaTareas.get(position).getCantidad()));
        holder.cv_horayfecha.setText(listaTareas.get(position).getFechayhora());
        holder.tanque.setText(String.valueOf(listaTareas.get(position).getTanque()));
        holder.cv_task_id.setText(String.valueOf(listaTareas.get(position).getTask()));
    }

    @Override
    public int getItemCount() {

        return listaTareas.size();
    }

    public void setItems(List<Tareas> tareas){
        listaTareas = tareas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cv_id_tarea, cv_medicamento, cv_cantidad, cv_horayfecha, tanque, cv_task_id, cv_task_id4, cv_id_tarea4, cv_cantidad4, cv_medicamento4, tanque4, cv_horayfecha4;

        public  ViewHolder(@NonNull View itemView) {
            super(itemView);
            cv_id_tarea = itemView.findViewById(R.id.cv_id_tarea);
            cv_task_id = itemView.findViewById(R.id.cv_task_id);
            cv_medicamento = itemView.findViewById(R.id.cv_medicamento);
            cv_cantidad = itemView.findViewById(R.id.cv_cantidad);
            cv_horayfecha = itemView.findViewById(R.id.cv_horayfecha);
            tanque = itemView.findViewById(R.id.tanque);
            cv_id_tarea4 = itemView.findViewById(R.id.cv_id_tarea4);
            cv_task_id4 = itemView.findViewById(R.id.cv_task_id4);
            cv_medicamento4 = itemView.findViewById(R.id.cv_medicamento4);
            cv_cantidad4 = itemView.findViewById(R.id.cv_cantidad4);
            cv_horayfecha4 = itemView.findViewById(R.id.cv_horayfecha4);
            tanque4 = itemView.findViewById(R.id.tanque4);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (interfaceTareas != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            interfaceTareas.onTareasClick(pos);
                        }
                    }
                }
            });

        }



    }
}

