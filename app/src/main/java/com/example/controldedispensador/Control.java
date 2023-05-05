package com.example.controldedispensador;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Control extends AppCompatActivity implements InterfaceDispositivos {
    RecyclerView recyclerView;
    DispositivosAdaptadores dispositivosAdaptadores;
    List<Dispositivos> listaDispositivos;
      String url = "https://www.odirenepernalete.com/mostrar_dispositivos.php";
    //String url= "https://www.odirenepernalete.com/enlace_lista.php;";
    Button enlace, salir;
    String usuario;


    @Override
        protected void onCreate (Bundle saveInstanceState){
                super.onCreate(saveInstanceState);
                setContentView(R.layout.activity_home1);
                usuario = getIntent().getStringExtra("user");
                recyclerView = (RecyclerView) findViewById(R.id.lista_tareas);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                enlace = findViewById(R.id.new_tarea);
                salir = findViewById(R.id.salir);

                listaDispositivos = new ArrayList<>();

                dispositivosAdaptadores = new DispositivosAdaptadores(Control.this, listaDispositivos, this);
                recyclerView.setAdapter(dispositivosAdaptadores);

                LoadAllDispositivos();

        enlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Control.this, Enlace.class);
                intent.putExtra("user1", usuario);
                startActivity(intent);
            }

        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Control.this, Login.class);
                startActivity(intent);
            }

        });


        }



    private void LoadAllDispositivos() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray array) {

                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        String mac = object.getString("mac");
                        String nom_dis = object.getString("nom_dis");

                        Dispositivos dispositivos = new Dispositivos();
                        dispositivos.setMac(mac);
                        dispositivos.setNom_dis(nom_dis);
                        listaDispositivos.add(dispositivos);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                dispositivosAdaptadores = new DispositivosAdaptadores(Control.this, listaDispositivos, Control.this);
                recyclerView.setAdapter(dispositivosAdaptadores);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Control.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("user", usuario);

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(Control.this);
        requestQueue.add(request);

    }


    @Override
    public void onDispositivoClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Control.this);
        CharSequence[] items = new CharSequence[4];
        items[0] = "Ver tareas";
        items[1] = "Agregar tareas";
        items[2] = "Cambiar nombre de dispositivo";
        items[3] = "Cancelar";

        builder.setTitle("Seleccione una opción")
        .setItems(items, new DialogInterface.OnClickListener() {

    @Override
    public void onClick(DialogInterface dialog, int l) {

        if (l == 0) {
        /// Ver tareas
            Intent intent = new Intent(Control.this, ControlTareas.class);

            intent.putExtra("mac",listaDispositivos.get(position).getMac());
            intent.putExtra("nom_dis",listaDispositivos.get(position).getNom_dis());
            intent.putExtra("user1", usuario);

            startActivity(intent);


        } else if (l == 1) {
        /// Agregar tareas
            Intent intent = new Intent(Control.this, NombreDispositivos.class);

            intent.putExtra("mac",listaDispositivos.get(position).getMac());
            intent.putExtra("nom_dis",listaDispositivos.get(position).getNom_dis());
            intent.putExtra("user1", usuario);

            startActivity(intent);

        } else if (l == 2){
            /// Cambiar nombre
            Intent intent = new Intent(Control.this, NombreDispositivos.class);

            intent.putExtra("mac",listaDispositivos.get(position).getMac());
            intent.putExtra("nom_dis",listaDispositivos.get(position).getNom_dis());
            intent.putExtra("user1", usuario);

            startActivity(intent);
        }else {
        //// Cancelar
        return;
        }
        }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        }


    }

