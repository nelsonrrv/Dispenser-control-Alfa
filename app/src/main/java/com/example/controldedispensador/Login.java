package com.example.controldedispensador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.controldedispensador.R.layout.activity_main;

public class Login extends AppCompatActivity {

    EditText usuario, password;
    TextView button_registrar;
    Button button_entrar;

    String str_user,str_password,str_al;
    String url = "https://www.odirenepernalete.com/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        usuario = findViewById(R.id.usuario);
        password = findViewById(R.id.contraseña);
        button_registrar = findViewById(R.id.button_new_task);
        button_entrar = findViewById(R.id.button_entrar);




        button_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                entrar();
            }
        });

        button_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Login.this, Registro.class);
                startActivity(intent);
            }

        });


    }

    public void entrar () {

        if(usuario.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca Usuario", Toast.LENGTH_SHORT).show();
        }
        else if(password.getText().toString().equals("")){
            Toast.makeText(this, "Introduzca Contraseña", Toast.LENGTH_SHORT).show();
        }
        else{


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Por favor espere...");

            progressDialog.show();

            str_user = usuario.getText().toString().trim();
            str_password = password.getText().toString().trim();
            GlobalVar user_act = new GlobalVar();
            user_act.setActualUser(usuario.getText().toString().trim());

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();

                    System.out.println(response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");



                        if (success.equals("3")) {
                            usuario.setText("");
                            password.setText("");
                            startActivity(new Intent(Login.this, ControlAdmin.class));
                            Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        } else if(success.equals("")) {
                            usuario.setText("");
                            password.setText("");
                            Toast.makeText(Login.this, "Login fallido. Usuario o Contraseña invalida", Toast.LENGTH_SHORT).show();
                        }else {

                            Intent intent = new Intent(Login.this, Control.class);
                            intent.putExtra("user", str_user);
                            startActivity(intent);
                            Toast.makeText(Login.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                            usuario.setText("");
                            password.setText("");

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Error Reading Detail : " + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }
                }, new Response.ErrorListener(){

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            ) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("user",str_user);
                    params.put("pass",str_password);
                    return params;

                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);



        }
    }


}

