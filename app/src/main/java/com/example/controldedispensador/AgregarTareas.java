package com.example.controldedispensador;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AgregarTareas extends AppCompatActivity implements View.OnClickListener {
    EditText new_cant, new_med, hora, fecha;
    Button button_new_task, button_hora, button_fecha;
    RadioButton tanque_1, tanque_2, tanque_3,tanque_4;
    private int mYear, mMonth, mDay, mHour, mMinute, mSec;
    TextView titulo_new_tarea;
    int tanque=1;
    String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarea);


        new_cant = findViewById(R.id.new_cant);
        new_med = findViewById(R.id.new_med);
        hora = findViewById(R.id.hora);
        fecha = findViewById(R.id.fecha);
        tanque_1 = findViewById(R.id.tanque_1);
        tanque_2 = findViewById(R.id.tanque_2);
        tanque_3 = findViewById(R.id.tanque_3);
        tanque_4 = findViewById(R.id.tanque_4);
        button_new_task = findViewById(R.id.button_new_task);
        button_hora = findViewById(R.id.button_hora);
        button_fecha = findViewById(R.id.button_fecha);
        titulo_new_tarea = findViewById(R.id.titulo_new_tarea);

        usuario = getIntent().getStringExtra("user1");



        button_fecha.setOnClickListener(this);
        button_hora.setOnClickListener(this);

        button_new_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });


    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.tanque_1:
                if (checked)
                     tanque =1;
                    break;
            case R.id.tanque_2:
                if (checked)
                    tanque =2;
                    break;
            case R.id.tanque_3:
                if (checked)
                    tanque =3;
                break;
            case R.id.tanque_4:
                if (checked)
                    tanque =4;
                    break;
        }
    }
    @Override
    public void onClick(View v) {

        if (v == button_fecha) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            String fecha_totext = year+ "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                            fecha.setText(fecha_totext);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == button_hora) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String hora_toText = hourOfDay + ":" + minute + ":00";
                            hora.setText(hora_toText);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    }
    private void insertData() {


        final String cantidad = new_cant.getText().toString().trim();
        final String medicina = new_med.getText().toString().trim();
        final String horas = hora.getText().toString();
        final String fechas = fecha.getText().toString();
        final String horayfecha = fechas + " " + horas;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        if (cantidad.isEmpty()) {

            new_cant.setError("complete los campos");
        } else if (medicina.isEmpty()) {

            new_med.setError("complete los campos");
        } else if (horas.isEmpty()) {

            hora.setError("complete los campos");
        } else if (fechas.isEmpty()) {

            fecha.setError("complete los campos");
        } else {
            progressDialog.show();


                StringRequest request = new StringRequest(Request.Method.POST, "https://www.odirenepernalete.com/agregar_tarea.php",
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("Tarea guardada")) {

                                    Toast.makeText(AgregarTareas.this, "Tarea guardada", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                    Intent intent = new Intent(AgregarTareas.this, ControlTareas.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(AgregarTareas.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    Toast.makeText(AgregarTareas.this, "Error al guardar tarea", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AgregarTareas.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("cant", cantidad);
                        params.put("med", medicina);
                        params.put("tanque", String.valueOf(tanque));
                        params.put("hora", horayfecha);

                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(AgregarTareas.this);
                requestQueue.add(request);


            }



        }



    }

