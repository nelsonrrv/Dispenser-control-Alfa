package com.example.controldedispensador;


import android.app.ProgressDialog;
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

public class ControlTareas extends AppCompatActivity implements InterfaceTareas {
    RecyclerView recyclerView;
    TareasAdapter tareasAdapter;
    List<Tareas> listaTareas;
    String url = "https://www.odirenepernalete.com/mostrar_tareas.php";
    String url2 = "https://odirenepernalete.com/borrar_tareas.php";
    Button new_tarea, salir;
    String usuario;


    @Override
    protected void onCreate (Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_control_tareas);
        usuario = getIntent().getStringExtra("user");
        recyclerView = (RecyclerView) findViewById(R.id.lista_tareas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new_tarea = findViewById(R.id.new_tarea);
        salir = findViewById(R.id.salir);

        listaTareas = new ArrayList<>();

        tareasAdapter = new TareasAdapter(ControlTareas.this, listaTareas, this);
        recyclerView.setAdapter(tareasAdapter);

        LoadAllTareas();

        new_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ControlTareas.this, AgregarTareas.class);
                intent.putExtra("user1", usuario);
                startActivity(intent);
            }

        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ControlTareas.this, Login.class);
                startActivity(intent);
            }

        });


    }



    private void LoadAllTareas() {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray array) {

                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject object = array.getJSONObject(i);
                        int task = object.getInt("task");
                        int id = object.getInt("id");
                        int cant = object.getInt("cant");
                        int tanque = object.getInt("tanque");
                        String med = object.getString("med");
                        String hora = object.getString("hora");

                        Tareas tareas = new Tareas();
                        tareas.setTask(task);
                        tareas.setId(id);
                        tareas.setMedicamento(med);
                        tareas.setCantidad(cant);
                        tareas.setFechayhora(hora);
                        tareas.setTanque(tanque);
                        listaTareas.add(tareas);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                tareasAdapter = new TareasAdapter(ControlTareas.this, listaTareas, ControlTareas.this);
                recyclerView.setAdapter(tareasAdapter);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ControlTareas.this, error.toString(), Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(ControlTareas.this);
        requestQueue.add(request);

    }


    @Override
    public void onTareasClick(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ControlTareas.this);
        CharSequence[] items = new CharSequence[3];
        items[0] = "Modificar tarea";
        items[1] = "Borrar tarea";
        items[2] = "Cancelar";


        builder.setTitle("Seleccione una opción")
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int l) {

                        if (l == 0) {
                            /// Modificar tarea
                            Intent intent = new Intent(ControlTareas.this, ModificarTareas.class);

                          intent.putExtra("task",listaTareas.get(position).getTask());
                          intent.putExtra("id",listaTareas.get(position).getId());
                          intent.putExtra("cant",listaTareas.get(position).getCantidad());
                          intent.putExtra("tanque",listaTareas.get(position).getTanque());
                          intent.putExtra("med",listaTareas.get(position).getMedicamento());
                          intent.putExtra("hora",listaTareas.get(position).getFechayhora());

                            startActivity(intent);


                        } else if (l == 1) {
                            /// Borrar tarea
                            borrarTarea();
                            Intent intent = new Intent(ControlTareas.this, ControlTareas.class);
                            startActivity(intent);
                        }
                        else {
                            //// Cancelar
                            return;
                        }
                    }

                    private void borrarTarea() {

                        int task_borrar = listaTareas.get(position).getTask();
                        final ProgressDialog progressDialog = new ProgressDialog(ControlTareas.this);
                        progressDialog.setMessage("Por favor espere...");

                        progressDialog.show();


                        StringRequest request = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("Se borro tarea con exito")) {

                                    progressDialog.dismiss();
                                    Toast.makeText(ControlTareas.this, response, Toast.LENGTH_SHORT).show();

                                }
                            }
                        }, new Response.ErrorListener(){

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(ControlTareas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        ) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put("task",String.valueOf(task_borrar));
                                return params;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(ControlTareas.this);
                        requestQueue.add(request);


                    }

                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}

