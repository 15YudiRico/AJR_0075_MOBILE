package com.yudiriconapitupulu.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.yudiriconapitupulu.ajr.api.BrosurApi;
import com.yudiriconapitupulu.ajr.models.Brosur;
import com.yudiriconapitupulu.ajr.models.BrosurResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ViewBrosurActivity extends AppCompatActivity {

    private EditText etNama, etTMobil, etjTransmisi, etjBahanBakar, etwMobil, etvBagasi, etfasilitas, ethargaSewa;
    private ImageView ivGambar;
    private LinearLayout layoutLoading;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_brosur);
        getSupportActionBar().hide();

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        ivGambar = findViewById(R.id.iv_gambar);
        etNama = findViewById(R.id.et_nama);
        etTMobil = findViewById(R.id.et_tMobil);
        etjTransmisi = findViewById(R.id.et_jTransmisi);
        etjBahanBakar = findViewById(R.id.et_jBahanBakar);
        etwMobil = findViewById(R.id.et_wMobil);
        etvBagasi = findViewById(R.id.et_vBagasi);
        etfasilitas = findViewById(R.id.et_fasilitas);
        ethargaSewa = findViewById(R.id.et_hargaSewa);
        layoutLoading = findViewById(R.id.layout_loading);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        long id_mobil = getIntent().getLongExtra("id_mobil", -1);

        getBrosurById(id_mobil);
    }

    private void getBrosurById(long id_mobil) {
        // TODO: Tambahkan fungsi untuk menampilkan data buku berdasarkan id.
        //  (hint: gunakan Glide untuk load gambar)
        setLoading(true);

        // Membuat request baru untuk mengambil data mahasiswa berdasarkan id
        StringRequest stringRequest = new StringRequest(GET, BrosurApi.GET_BY_ID_URL + id_mobil, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object ProdukResponse menggunakan Gson */
                BrosurResponse brosurResponse = gson.fromJson(response, BrosurResponse.class);

                Brosur brosur = brosurResponse.getBrosurList().get(0);
                etNama.setText(brosur.getNama_mobil());
                etTMobil.setText(brosur.getTipe_mobil());
                etjTransmisi.setText(brosur.getJenis_transmisi());
                etjBahanBakar.setText(brosur.getJenis_bahan_bakar());
                etwMobil.setText(brosur.getWarna_mobil());
                etvBagasi.setText(brosur.getVolume_bagasi() + "mm");
                etfasilitas.setText(brosur.getFasilitas());
                ethargaSewa.setText("Rp" + String.valueOf(brosur.getHarga_sewa_perhari())+ "0");

                Glide.with(ivGambar)
                        .load(brosur.getFoto_mobil())
                        .placeholder(R.drawable.no_image)
                        .into(ivGambar);

                Toast.makeText(ViewBrosurActivity.this, brosurResponse.getMessage(), Toast.LENGTH_SHORT).show();
                setLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(ViewBrosurActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(ViewBrosurActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

}