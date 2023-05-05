package com.example.controldedispensador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class DispositivosAdaptadores extends RecyclerView.Adapter<DispositivosAdaptadores.ViewHolder> {
    private final Context context;
    private final InterfaceDispositivos interfaceDispositivos;
    private List<Dispositivos> listaDispositivos;
    
    public DispositivosAdaptadores(Context context, List<Dispositivos> listaDispositivos, InterfaceDispositivos interfaceDispositivos) {
        this.context = context;
        this.listaDispositivos = listaDispositivos;
        this.interfaceDispositivos = interfaceDispositivos;
    }

    @NonNull
    @Override
    public DispositivosAdaptadores.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType ){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.lista_dispositivos,parent,false);
        return new DispositivosAdaptadores.ViewHolder(view);
        
    }

    @Override
    public void onBindViewHolder(@NonNull DispositivosAdaptadores.ViewHolder holder, int position) {
        holder.rcv_mac.setText(listaDispositivos.get(position).getMac());
        holder.rcv_nom_dis.setText(listaDispositivos.get(position).getNom_dis());
    }

    @Override
    public int getItemCount() {

        return listaDispositivos.size();
    }

    public void setItems(List<Dispositivos> dispositivos){
        listaDispositivos = dispositivos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rcv_mac, rcv_nom_dis;
        String mac, nom_dis;

            public  ViewHolder(@NonNull View itemView) {
                super(itemView);
                rcv_mac = itemView.findViewById(R.id.cv_horayfecha);
                rcv_nom_dis = itemView.findViewById(R.id.rcv_nom_dis);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (interfaceDispositivos != null){
                            int pos = getAdapterPosition();
                            if(pos != RecyclerView.NO_POSITION){
                                interfaceDispositivos.onDispositivoClick(pos);
                            }
                        }
                    }
                });

            }



    }
    }

