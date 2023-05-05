package com.example.controldedispensador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NombreDispositivos extends AppCompatActivity {
    TextView macview;
    EditText nom_dis_nuevo;
    Button button_siguiente;
    String mactext, nombre_nuevo_dispositivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_nom_dis);



        macview = findViewById(R.id.mac_id_dis);
        nom_dis_nuevo = findViewById(R.id.fecha);
        button_siguiente = findViewById(R.id.button_new_task);
        String mac = getIntent().getStringExtra("mac");
        String nom_dis = getIntent().getStringExtra("nom_dis");

        macview.setText(mac);

         if (!nom_dis.equals("")) {
             nom_dis_nuevo.setText(nom_dis);
         }


        button_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambioNombre();
            }
        });

    }
            private void cambioNombre() {

                String url = "https://www.odirenepernalete.com/cambio_nombre_dispositivo.php";


                    if(nom_dis_nuevo.getText().toString().equals("")){
                        Toast.makeText(this, "Introduzca un nombre", Toast.LENGTH_SHORT).show();
                    }
                    else {


                        final ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("Por favor espere...");

                        progressDialog.show();

                        nombre_nuevo_dispositivo = nom_dis_nuevo.getText().toString().trim();
                        mactext = macview.getText().toString().trim();

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equalsIgnoreCase("Cambio de nombre correcto")) {

                                    Toast.makeText(NombreDispositivos.this, "Cambio de nombre correctoo", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                    Intent intent = new Intent(NombreDispositivos.this, Control.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(NombreDispositivos.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Toast.makeText(NombreDispositivos.this, "Error al cambiar de nombre", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progressDialog.dismiss();
                                Toast.makeText(NombreDispositivos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        ) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("mac", mactext);
                                params.put("nom_dis", nombre_nuevo_dispositivo);
                                return params;

                            }

                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(NombreDispositivos.this);
                        requestQueue.add(request);



                    }
                }

        }




