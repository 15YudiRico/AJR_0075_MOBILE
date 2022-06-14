package com.yudiriconapitupulu.ajr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivityCustomer extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_customer);
        getSupportActionBar().hide();

        long id = getIntent().getLongExtra("tempID", -1);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.b_mobil:
                        i = new Intent(HomeActivityCustomer.this, BrosurActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(HomeActivityCustomer.this, BrosurActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.promo:
                        i = new Intent(HomeActivityCustomer.this, PromoActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(HomeActivityCustomer.this, PromoActivity.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.profile:
                        i = new Intent(HomeActivityCustomer.this, ProfileCustomerActivity.class);
                        i.putExtra("tempID", id);
                        startActivity(i);
                        //startActivity(new Intent(HomeActivityCustomer.this, ProfileCustomerActivity.class));
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
                        startActivity(new Intent(HomeActivityCustomer.this, MainActivity.class));
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