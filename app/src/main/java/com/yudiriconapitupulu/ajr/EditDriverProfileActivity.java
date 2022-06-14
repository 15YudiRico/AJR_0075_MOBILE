package com.yudiriconapitupulu.ajr;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yudiriconapitupulu.ajr.models.Driver;
import com.yudiriconapitupulu.ajr.models.DriverResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EditDriverProfileActivity extends AppCompatActivity {

    private EditText etNama, etAlamat, etNoTelpn, etEmail, etPassword;
    private RadioButton rTersedia, rTidakTersedia;
    private Button btnSimpan, btnBack;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_driver_profile);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempDriverID", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        etNama = findViewById(R.id.et_nama);
        etAlamat = findViewById(R.id.et_Alamat);
        etNoTelpn = findViewById(R.id.et_NomorTelepon);
        etEmail = findViewById(R.id.et_Email);
        etPassword = findViewById(R.id.et_Password);
        layoutLoading = findViewById(R.id.layout_loading);

        rTersedia = findViewById(R.id.radio_tersedia);
        rTidakTersedia = findViewById(R.id.radio_tidakTersedia);

        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = new Intent(EditDriverProfileActivity.this, ProfileDriverActivity.class);
                i.putExtra("tempDriverID", id);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();
            }
        });

        btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        getDriverById(id);
    }

    private void getDriverById(long id) {
        // TODO: Tambahkan fungsi untuk menampilkan data buku berdasarkan id.
        //  (hint: gunakan Glide untuk load gambar)
        setLoading(true);

        // Membuat request baru untuk mengambil data mahasiswa berdasarkan id
        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.site/api/driver/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object ProdukResponse menggunakan Gson */
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);

                Driver driver = driverResponse.getDriverList().get(0);
                etNama.setText(driver.getNama_driver());
                etAlamat.setText(driver.getAlamat_driver());
                etNoTelpn.setText(driver.getNo_telp_driver());
                etEmail.setText(driver.getEmail_driver());

                if(driver.getStatus_ketersediaan_driver()==1){
                    rTersedia.setChecked(true);
                }else{
                    rTidakTersedia.setChecked(true);
                }

                Toast.makeText(EditDriverProfileActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(EditDriverProfileActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(EditDriverProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    public int getNewStatusKetersediaan(){
        if (rTersedia.isChecked()){
            return 1;
        }else {
            return 0;
        }
    }

    private void updateDriver(long id) {
        // TODO: Tambahkan fungsi untuk mengubah data buku.
        setLoading(true);

        Driver driver = new Driver(
                etNama.getText().toString(),
                etAlamat.getText().toString(),
                etEmail.getText().toString(),
                etNoTelpn.getText().toString(),
                getNewStatusKetersediaan(),
                etPassword.getText().toString());

        // Membuat request baru untuk mengedit data mahasiswa
        StringRequest stringRequest = new StringRequest(PUT, "https://atmajayarental.site/api/driver/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                /* Deserialiasai data dari response JSON dari API
                menjadi java object MahasiswaResponse menggunakan Gson */
                DriverResponse driverResponse = gson.fromJson(response, DriverResponse.class);

                i = new Intent(EditDriverProfileActivity.this, ProfileDriverActivity.class);
                i.putExtra("tempDriverID", id);
                startActivity(i);
                overridePendingTransition(0,0);
                finish();

                Toast.makeText(EditDriverProfileActivity.this, driverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(EditDriverProfileActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(EditDriverProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            // Menambahkan header pada request
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
            // Menambahkan request body berupa object mahasiswa
            @Override
            public byte[] getBody() throws AuthFailureError {
                Gson gson = new Gson();
                 /* Serialisasi data dari java object ProdukResponse
                 menjadi JSON string menggunakan Gson */
                String requestBody = gson.toJson(driver);
                return requestBody.getBytes(StandardCharsets.UTF_8);
            }
            // Mendeklarasikan content type dari request body yang ditambahkan
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        // Menambahkan request ke request queue
        queue.add(stringRequest);
    }

    // Fungsi ini digunakan menampilkan layout loading
    private void setLoading(boolean isLoading) {
        if (isLoading) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.VISIBLE);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            layoutLoading.setVisibility(View.INVISIBLE);
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        // set title dialog
        alertDialogBuilder.setTitle("Atma Jaya Rental");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah Anda Yakin Update Profile?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        long idDriver = getIntent().getLongExtra("tempDriverID", -1);
                        updateDriver(idDriver);
                    }
                })
                .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }
}