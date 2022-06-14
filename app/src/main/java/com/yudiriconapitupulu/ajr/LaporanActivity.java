package com.yudiriconapitupulu.ajr;

import static com.android.volley.Request.Method.GET;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.whiteelephant.monthpicker.MonthPickerDialog;
import com.yudiriconapitupulu.ajr.api.PromoApi;
import com.yudiriconapitupulu.ajr.models.Driver;
import com.yudiriconapitupulu.ajr.models.DriverResponse;
import com.yudiriconapitupulu.ajr.models.Laporan;
import com.yudiriconapitupulu.ajr.models.LaporanResponse;
import com.yudiriconapitupulu.ajr.models.PromoResponse;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LaporanActivity extends AppCompatActivity {
    private RequestQueue queue;
    private BottomNavigationView bottomNavigationView;
    private Button btnLap1, btnLap2, btnLap3, btnLap4, btnLap5, btnDate;
    private TextView dateMonthYear;
    int pointer, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        getSupportActionBar().hide();

        // Pendeklarasian request queue
        queue = Volley.newRequestQueue(this);

        btnLap1 = findViewById(R.id.btnLap1);
        btnLap2 = findViewById(R.id.btnLap2);
        btnLap3 = findViewById(R.id.btnLap3);
        btnLap4 = findViewById(R.id.btnLap4);
        btnLap5 = findViewById(R.id.btnLap5);

        dateMonthYear = findViewById(R.id.dateMonthYear);
        btnDate = findViewById(R.id.btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker(view);
            }
        });

        btnLap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateMonthYear.getText().toString().equals("Month/Year")){
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=1;
                    getLaporanByPointer(pointer, month, year);
                }

            }
        });

        btnLap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateMonthYear.getText().toString().equals("Month/Year")){
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=2;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btnLap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateMonthYear.getText().toString().equals("Month/Year")){
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=3;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btnLap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateMonthYear.getText().toString().equals("Month/Year")){
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=4;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        btnLap5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dateMonthYear.getText().toString().equals("Month/Year")){
                    Toast.makeText(LaporanActivity.this, "Pilih Bulan & Tahun", Toast.LENGTH_SHORT).show();
                }else {
                    pointer=5;
                    getLaporanByPointer(pointer, month, year);
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.report);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(LaporanActivity.this, HomeActivityManager.class));
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                    case R.id.report:
                        return true;
                    case R.id.logout:
                        showDialog();
                        return false;
                }
                return false;
            }
        });
    }

    private void getLaporanByPointer(int pointer, int month, int year) {
        // TODO: Tambahkan fungsi untuk menampilkan seluruh data buku.

        StringRequest stringRequest = new StringRequest(GET, "https://atmajayarental.site/api/laporan/"+pointer+"/"+month+"/"+year, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                 /* Deserialiasai data dari response JSON dari API
                 menjadi java object MahasiswaResponse menggunakan Gson */
                LaporanResponse laporanResponse = gson.fromJson(response, LaporanResponse.class);

                try {
                    cetakPDF(laporanResponse.getLaporanList(), pointer, month, year);
                } catch (FileNotFoundException | DocumentException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(LaporanActivity.this, laporanResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                    JSONObject errors = new JSONObject(responseBody);

                    Toast.makeText(LaporanActivity.this, errors.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(LaporanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void datePicker(View view){
        final Calendar today = Calendar.getInstance();
        MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(LaporanActivity.this, new MonthPickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(int selectedMonth, int selectedYear) {
                dateMonthYear.setText((selectedMonth + 1) + "/" + selectedYear);
                month = selectedMonth+1;
                year = selectedYear;
            }
        }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

        builder.setActivatedMonth(today.get(Calendar.MONTH))
                .setMinYear(1990)
                .setActivatedYear(today.get(Calendar.YEAR))
                .setMaxYear(2050)
                .setTitle("Select Month / Year")
                .build().show();
    }

    private void cetakPDF(List<Laporan> laporanList, int pointer, int month, int year) throws FileNotFoundException, DocumentException {
        /** Untuk Android 11 nanti file pdf tidak bisa diakses lewat filemanager HP langsung
         * jadi harus konek lewat PC gara gara implementasi Scoped Storage.
         * Kalau mau biar bisa di android 11 bisa pelajari sendiri tentangpenggunaan MediaStorage* */

        File folder = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
        if (!folder.exists()) {
            folder.mkdir();
        }

        Date currentTime = Calendar.getInstance().getTime();
        String pdfName = currentTime.getTime() + ".pdf";

        File pdfFile = new File(folder.getAbsolutePath(), pdfName);
        OutputStream outputStream = new FileOutputStream(pdfFile);

        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // bagian header
        Paragraph judul = new Paragraph("ATMA JOGJA RENTAL \n\n",
                new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK));

        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);

        if(pointer == 1){
            Paragraph subJudul = new Paragraph("Laporan Penyewaan Mobil pada Bulan & Tahun Tertentu \n\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));

            subJudul.setAlignment(Element.ALIGN_CENTER);
            document.add(subJudul);

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Rico Napitupulu" + "\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "No : " + "LAP01" + "\n\n" +
                            "Bulan/Tahun: " + month + "/"+ year +"\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nBerikut merupakan isi dari laporan: \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Nama Mobil"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Tipe Mobil"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Harga Sewa Perhari"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Peminjaman"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);
            PdfPCell h5 = new PdfPCell(new Phrase("Total Durasi"));
            h5.setHorizontalAlignment(Element.ALIGN_CENTER);
            h5.setPaddingBottom(5);
            PdfPCell h6 = new PdfPCell(new Phrase("Pendapatan"));
            h6.setHorizontalAlignment(Element.ALIGN_CENTER);
            h6.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);
            tableHeader.addCell(h5);
            tableHeader.addCell(h6);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }
            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getNama_mobil());
                tableData.addCell(L.getTipe_mobil());
                tableData.addCell(String.valueOf("Rp "+L.getHarga_sewa_perhari()+"0"));
                tableData.addCell(String.valueOf(L.getJUMLAH_PEMINJAMAN()+"x"));
                tableData.addCell(String.valueOf(L.getTOTAL_DURASI()+" Hari"));
                tableData.addCell(String.valueOf("Rp "+L.getPENDAPATAN()+"0"));
            }

            document.add(tableData);

            com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();

        } else if(pointer==2){
            Paragraph subJudul = new Paragraph("Laporan Detail Pendapatan pada Bulan & Tahun Tertentu \n\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));

            subJudul.setAlignment(Element.ALIGN_CENTER);
            document.add(subJudul);

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Rico Napitupulu" + "\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "No : " + "LAP02" + "\n\n" +
                            "Bulan/Tahun: " + month + "/"+ year +"\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nBerikut merupakan isi data dari laporan: \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Mobil"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Jenis Transaksi"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);
            PdfPCell h5 = new PdfPCell(new Phrase("Total Pendapatan"));
            h5.setHorizontalAlignment(Element.ALIGN_CENTER);
            h5.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);
            tableHeader.addCell(h5);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }
            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getNama_customer());
                tableData.addCell(L.getNama_mobil());

                if(L.getJenis_transaksi()==0){
                    tableData.addCell("Peminjaman Mobil");
                } else {
                    tableData.addCell("Peminjaman Mobil + Driver");
                }

                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI()+"x"));
                tableData.addCell(String.valueOf("Rp "+L.getTOTAL_PENDAPATAN_DARI_CUSTOMER()+"0"));
            }

            document.add(tableData);

            com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();

        } else if(pointer==3){
            Paragraph subJudul = new Paragraph("Laporan Top 5 Driver dengan transaksi terbanyak pada Bulan & Tahun Tertentu \n\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));

            subJudul.setAlignment(Element.ALIGN_CENTER);
            document.add(subJudul);

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Rico Napitupulu" + "\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "No : " + "LAP03" + "\n\n" +
                            "Bulan/Tahun: " + month + "/"+ year +"\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nBerikut merupakan isi data dari laporan: \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }
            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getFormat_id_driver()+String.valueOf(L.getId_driver()));
                tableData.addCell(L.getNama_driver());
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI()+"x"));
            }

            document.add(tableData);

            com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();

        }else if(pointer == 4){
            Paragraph subJudul = new Paragraph("Laporan Performa Top 5 Driver pada Bulan & Tahun Tertentu \n\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));

            subJudul.setAlignment(Element.ALIGN_CENTER);
            document.add(subJudul);

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Rico Napitupulu" + "\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "No : " + "LAP04" + "\n\n" +
                            "Bulan/Tahun: " + month + "/"+ year +"\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nBerikut merupakan isi data dari laporan: \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5, 5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("ID Driver"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Nama Driver"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);
            PdfPCell h3 = new PdfPCell(new Phrase("Rerata Rating Driver"));
            h3.setHorizontalAlignment(Element.ALIGN_CENTER);
            h3.setPaddingBottom(5);
            PdfPCell h4 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h4.setHorizontalAlignment(Element.ALIGN_CENTER);
            h4.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);
            tableHeader.addCell(h3);
            tableHeader.addCell(h4);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }
            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5, 5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getFormat_id_driver()+String.valueOf(L.getId_driver()));
                tableData.addCell(L.getNama_driver());
                tableData.addCell(String.valueOf(L.getRERATA_RATING_DRIVER()));
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI()+"x"));
            }

            document.add(tableData);

            com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();

        } else if (pointer==5) {
            Paragraph subJudul = new Paragraph("Laporan Top 5 Customer dengan transaksi terbanyak pada Bulan & Tahun Tertentu \n\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK));

            subJudul.setAlignment(Element.ALIGN_CENTER);
            document.add(subJudul);

            // Buat tabel
            PdfPTable tables = new PdfPTable(new float[]{16, 8});

            // Settingan ukuran tabel
            tables.getDefaultCell().setFixedHeight(50);
            tables.setTotalWidth(PageSize.A4.getWidth());
            tables.setWidthPercentage(100);
            tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            PdfPCell cellSupplier = new PdfPCell();
            cellSupplier.setPaddingLeft(20);
            cellSupplier.setPaddingBottom(10);
            cellSupplier.setBorder(Rectangle.NO_BORDER);

            Paragraph kepada = new Paragraph("Kepada Yth: \n" + "Rico Napitupulu" + "\n",
                    new com.itextpdf.text.Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL, BaseColor.BLACK));
            cellSupplier.addElement(kepada);
            tables.addCell(cellSupplier);

            Paragraph NomorTanggal = new Paragraph(
                    "No : " + "LAP05" + "\n\n" +
                            "Bulan/Tahun: " + month + "/"+ year +"\n",
                    new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                            com.itextpdf.text.Font.NORMAL, BaseColor.BLACK));

            NomorTanggal.setPaddingTop(5);
            tables.addCell(NomorTanggal);
            document.add(tables);
            com.itextpdf.text.Font f = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);

            Paragraph Pembuka = new Paragraph("\nBerikut merupakan isi data dari laporan: \n\n", f);
            Pembuka.setIndentationLeft(20);
            document.add(Pembuka);
            PdfPTable tableHeader = new PdfPTable(new float[]{5, 5});

            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
            tableHeader.getDefaultCell().setFixedHeight(30);
            tableHeader.setTotalWidth(PageSize.A4.getWidth());
            tableHeader.setWidthPercentage(100);

            // Setup Column
            PdfPCell h1 = new PdfPCell(new Phrase("Nama Customer"));
            h1.setHorizontalAlignment(Element.ALIGN_CENTER);
            h1.setPaddingBottom(5);
            PdfPCell h2 = new PdfPCell(new Phrase("Jumlah Transaksi"));
            h2.setHorizontalAlignment(Element.ALIGN_CENTER);
            h2.setPaddingBottom(5);

            tableHeader.addCell(h1);
            tableHeader.addCell(h2);

            // Beri warna untuk kolumn
            for (PdfPCell cells : tableHeader.getRow(0).getCells()) {
                cells.setBackgroundColor(BaseColor.PINK);
            }
            document.add(tableHeader);
            PdfPTable tableData = new PdfPTable(new float[]{5, 5});

            tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableData.getDefaultCell().setFixedHeight(30);
            tableData.setTotalWidth(PageSize.A4.getWidth());
            tableData.setWidthPercentage(100);
            tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);

            // masukan data pegawai jadi baris
            for (Laporan L : laporanList) {
                tableData.addCell(L.getNama_customer());
                tableData.addCell(String.valueOf(L.getJUMLAH_TRANSAKSI()+"x"));
            }

            document.add(tableData);

            com.itextpdf.text.Font h = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                    com.itextpdf.text.Font.NORMAL);
            String tglDicetak = currentTime.toLocaleString();
            Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
            P.setAlignment(Element.ALIGN_RIGHT);
            document.add(P);
            document.close();
            previewPdf(pdfFile);
            Toast.makeText(this, "PDF berhasil dibuat", Toast.LENGTH_SHORT).show();
        }

    }

    private void previewPdf(File pdfFile) {
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List<ResolveInfo> list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);

        if (list.size() > 0) {
            Uri uri;
            uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);

            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            this.grantUriPermission(getPackageName(), uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION |
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
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
                        startActivity(new Intent(LaporanActivity.this, MainActivity.class));
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