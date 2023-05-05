package com.example.controldedispensador;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class Enlace extends AppCompatActivity implements InterfaceDispositivos {
    RecyclerView recyclerView;
    DispositivosAdaptadores dispositivosAdaptadores;
    List<Dispositivos> listaDispositivos;
    String url = "https://www.odirenepernalete.com/mostrar_dispositivos.php";
    String url_2 = "https://www.odirenepernalete.com/conexion_dis_user.php";
    Button salir;
    String usuario;


    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_enlace);

        usuario = getIntent().getStringExtra("user1");
        recyclerView = (RecyclerView) findViewById(R.id.lista_tareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        salir = findViewById(R.id.salir);

        listaDispositivos = new ArrayList<>();

        dispositivosAdaptadores = new DispositivosAdaptadores(Enlace.this, listaDispositivos, this);
        recyclerView.setAdapter(dispositivosAdaptadores);

        LoadAllDispositivos();


        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Enlace.this, Login.class);
                startActivity(intent);
            }

        });
    }



    private void LoadAllDispositivos() {

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray array) {

                for (int i = 0; i < array.length(); i++){
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

                dispositivosAdaptadores = new DispositivosAdaptadores(Enlace.this, listaDispositivos, Enlace.this);
                recyclerView.setAdapter(dispositivosAdaptadores);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Enlace.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Enlace.this);
        requestQueue.add(request);


    }


    @Override
    public void onDispositivoClick(int position) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        String macput = listaDispositivos.get(position).getMac();


        
        StringRequest request = new StringRequest(Request.Method.POST, url_2,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("Enlace correcto")) {

                            Toast.makeText(Enlace.this, "Enlace correcto", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(Enlace.this, Control.class);

                            startActivity(intent);
                        } else {
                            Toast.makeText(Enlace.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            Toast.makeText(Enlace.this, "Error en enlace", Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Enlace.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("mac", macput);
                params.put("user", usuario);

                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(Enlace.this);
        requestQueue.add(request);


    }


}
