package com.yudiriconapitupulu.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.yudiriconapitupulu.ajr.adapters.BrosurAdapter;
import com.yudiriconapitupulu.ajr.api.BrosurApi;
import com.yudiriconapitupulu.ajr.models.BrosurResponse;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BrosurActivity extends AppCompatActivity {

    public static final int LAUNCH_ADD_ACTIVITY = 123;

    private SwipeRefreshLayout srBrosur;
    private BrosurAdapter adapter;
    private SearchView svBrosur;
    private LinearLayout layoutLoading;
    private RequestQueue queue;
    private BottomNavigationView bottomNavigationView;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brosur);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempID", -1);

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        layoutLoading = findViewById(R.id.layout_loading);
        srBrosur = findViewById(R.id.sr_brosur);
        svBrosur = findViewById(R.id.sv_brosur);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.b_mobil);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        i = new Intent(BrosurActivity.this, HomeActivityCustomer.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(BrosurActivity.this, HomeActivityCustomer.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.b_mobil:
                        return true;
                    case R.id.promo:
                        i = new Intent(BrosurActivity.this, PromoActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(BrosurActivity.this, PromoActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        i = new Intent(BrosurActivity.this, ProfileCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(BrosurActivity.this, ProfileCustomerActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.logout:
                        showDialog();
                        return false;
                }
                return false;
            }
        });

        srBrosur.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllBrosur();
            }
        });

        svBrosur.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        RecyclerView rvBrosur = findViewById(R.id.rv_brosur);
        adapter = new BrosurAdapter(new ArrayList<>(), this);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //Grid Layout Portrait
            rvBrosur.setLayoutManager(new GridLayoutManager(BrosurActivity.this, 1));
        }else{
            //Grid Layout Landscape
            rvBrosur.setLayoutManager(new GridLayoutManager(BrosurActivity.this, 2));
        }
        rvBrosur.setAdapter(adapter);

        getAllBrosur();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == Activity.RESULT_OK)
            getAllBrosur();
    }

    private void getAllBrosur() {
        // TODO: Tambahkan fungsi untuk menampilkan seluruh data buku.
        srBrosur.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(GET, BrosurApi.GET_ALL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object MahasiswaResponse menggunakan Gson */
                BrosurResponse brosurResponse = gson.fromJson(response, BrosurResponse.class);

                adapter.setBrosurList(brosurResponse.getBrosurList());
                adapter.getFilter().filter(svBrosur.getQuery());

                Toast.makeText(BrosurActivity.this, brosurResponse.getMessage(), Toast.LENGTH_SHORT).show();
                srBrosur.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                srBrosur.setRefreshing(false);

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(BrosurActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(BrosurActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            layoutLoading.setVisibility(View.GONE);
        }
    }

    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        // set title dialog
        alertDialogBuilder.setTitle("Atma Jaya Rental");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Apakah Anda Yakin Ingin Logout?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        startActivity(new Intent(BrosurActivity.this, MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
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