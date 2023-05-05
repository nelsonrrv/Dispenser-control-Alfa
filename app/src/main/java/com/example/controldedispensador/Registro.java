package com.example.controldedispensador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText nombreUser, usuario_crear, contrasena_crear, idUser;
    Button button_registrar;
    int al = 1;
    Switch radio_enf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        nombreUser = findViewById(R.id.hora);
        usuario_crear = findViewById(R.id.new_med);
        contrasena_crear = findViewById(R.id.new_cant);
        idUser = findViewById(R.id.fecha);
        button_registrar = findViewById(R.id.button_new_task);
        radio_enf = (Switch) findViewById(R.id.radio_enf);


        radio_enf.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if (radio_enf.isChecked()) //checking if  switch is checked
                {
                    idUser.setVisibility(View.VISIBLE);
                    al=2;
                } else {
                    idUser.setVisibility(View.GONE);
                    al=1;
                }
            }
        });

        button_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });


    }

    private void insertData() {


        boolean switchState = radio_enf.isChecked();
        final String usuario = usuario_crear.getText().toString().trim();
        final String nombre = nombreUser.getText().toString().trim();
        final String password = contrasena_crear.getText().toString().trim();
        final String id = idUser.getText().toString().trim();
        final String access = String.valueOf(al);

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        if (nombre.isEmpty()) {

            nombreUser.setError("complete los campos");
        } else if (usuario.isEmpty()) {

            usuario_crear.setError("complete los campos");
        } else if (password.isEmpty()) {

            contrasena_crear.setError("complete los campos");
        } else if (id.isEmpty() && switchState) {

            idUser.setError("complete los campos");
        } else {
            progressDialog.show();

            if (radio_enf.isChecked()) {
                StringRequest request = new StringRequest(Request.Method.POST, "https://www.odirenepernalete.com/comprobar_id_enf.php",
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("Id Valido")) {

                                    StringRequest request = new StringRequest(Request.Method.POST, "https://www.odirenepernalete.com/insertar.php",
                                            new Response.Listener<String>() {



                                                @Override
                                                public void onResponse(String response) {

                                                    if (response.equalsIgnoreCase("Registro correcto")) {

                                                        Toast.makeText(Registro.this, "Registro correcto", Toast.LENGTH_SHORT).show();

                                                        progressDialog.dismiss();

                                                        Intent intent = new Intent(Registro.this, Login.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Registro.this, "Error en registro", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(Registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<String, String>();

                                            params.put("name", nombre);
                                            params.put("user", usuario);
                                            params.put("pass", password);
                                            params.put("al", access);


                                            return params;
                                        }
                                    };


                                    RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
                                    requestQueue.add(request);


                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(Registro.this, "Id Invalido", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id_enf", id);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
                requestQueue.add(request);

            } else {

                                    StringRequest request = new StringRequest(Request.Method.POST, "https://www.odirenepernalete.com/insertar.php",
                                            new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String response) {

                                                    if (response.equalsIgnoreCase("Registro correcto")) {

                                                        Toast.makeText(Registro.this, "Registro correcto", Toast.LENGTH_SHORT).show();

                                                        progressDialog.dismiss();

                                                        Intent intent = new Intent(Registro.this, Login.class);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        Toast.makeText(Registro.this, "Error en registro", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(Registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                    }

                                    ) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> params = new HashMap<String, String>();

                                            params.put("name", nombre);
                                            params.put("user", usuario);
                                            params.put("pass", password);
                                            params.put("al", access);


                                            return params;
                                        }
                                    };


                                    RequestQueue requestQueue = Volley.newRequestQueue(Registro.this);
                                    requestQueue.add(request);


                                }



            }



        }


    }