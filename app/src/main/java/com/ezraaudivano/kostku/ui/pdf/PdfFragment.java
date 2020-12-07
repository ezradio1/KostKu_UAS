package com.ezraaudivano.kostku.ui.pdf;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ezraaudivano.kostku.API.KostAPI;
import com.ezraaudivano.kostku.API.PengumumanAPI;
import com.ezraaudivano.kostku.R;
import com.ezraaudivano.kostku.model.Kost;
import com.ezraaudivano.kostku.model.Pengumuman;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class PdfFragment extends Fragment {
    private PdfViewModel pdfViewModel;

    private static final String TAG = "PdfCreatorActivity";
    final private int REQUEST_CODE_ASK_PERMISSIONS = 101;
    private File pdfFile;
    private PdfWriter writer;
    private AlertDialog.Builder builder;
    private Button btnCetak, btnCetak2;
    private List<Pengumuman> listPengumuman;
    private List<Kost> listKost;
    private View view;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String emailFB = user.getEmail();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pdfViewModel =
                new ViewModelProvider(this).get(PdfViewModel.class);
        view = inflater.inflate(R.layout.fragment_pdf, container, false);
        listPengumuman = new ArrayList<Pengumuman>();
        listKost = new ArrayList<Kost>();
        getPengumuman();
        getKost();

        btnCetak=view.findViewById(R.id.btnCetak);
        btnCetak2=view.findViewById(R.id.btnCetak2);
        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());

                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin ingin mencetak pengumuman ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            createPdfWrapper();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
            }
        });

        btnCetak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(getContext());

                builder.setCancelable(false);
                builder.setMessage("Apakah anda yakin ingin mencetak kost ?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            createPdfWrapper2();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (DocumentException e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setBackgroundColor(Color.WHITE);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.WHITE);
            }
        });
        return view;
    }

    private void createPdf() throws FileNotFoundException, DocumentException {
        //isikan code createPdf()
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Download/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Direktori baru untuk file pdf berhasil dibuat");
        }
        String pdfname = "Dokumen_Pengumuman"+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath()+ '/', pdfname);

        OutputStream output = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, output);
        document.open();

        Paragraph judul = new Paragraph(" Dokumen Pengumuman \n\n", new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
                com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph Kepada= new Paragraph(
                "Daftar Pengumuman KostKu",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK)
        );
        cellSupplier.addElement(Kepada);
        tables.addCell(cellSupplier);

        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan daftar pengumuman KostKu \n\n",f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{5,5,5});
        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);
        //TODO 2.5 - Bagian ini tidak perlu diubah

        PdfPCell h1 = new PdfPCell(new Phrase("Judul Pengumuman"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        PdfPCell h2 = new PdfPCell(new Phrase("Subjudul Pengumuman"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Konten"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h4);
        PdfPCell[] cells = tableHeader.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{5,5,5});
        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        int arrLength = listPengumuman.size();
        for(int x=0;x<arrLength;x++){
            for(int i=0;i<cells.length;i++){
                if(i==0){
                    tableData.addCell(listPengumuman.get(x).getJudul());
                }else if(i==1){
                    tableData.addCell(listPengumuman.get(x).getSubjudul());
                }else{
                    tableData.addCell(listPengumuman.get(x).getKonten());
                }
            }
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String tglDicetak = sdf.format(currentTime);
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf();

    }

    private void createPdf2() throws FileNotFoundException, DocumentException {
        //isikan code createPdf()
        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Download/");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i(TAG, "Direktori baru untuk file pdf berhasil dibuat");
        }
        String pdfname = "Dokumen_Kost"+".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath()+ '/', pdfname);

        OutputStream output = new FileOutputStream(pdfFile);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(PageSize.A4);
        writer = PdfWriter.getInstance(document, output);
        document.open();

        Paragraph judul = new Paragraph(" Dokumen Pengumuman \n\n", new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
                com.itextpdf.text.Font.BOLD, BaseColor.BLACK));
        judul.setAlignment(Element.ALIGN_CENTER);
        document.add(judul);
        PdfPTable tables = new PdfPTable(new float[]{16, 8});
        tables.getDefaultCell().setFixedHeight(50);
        tables.setTotalWidth(PageSize.A4.getWidth());
        tables.setWidthPercentage(100);
        tables.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        PdfPCell cellSupplier = new PdfPCell();
        cellSupplier.setPaddingLeft(20);
        cellSupplier.setPaddingBottom(10);
        cellSupplier.setBorder(Rectangle.NO_BORDER);

        Paragraph Kepada= new Paragraph(
                "Daftar Kost KostKu",
                new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                        com.itextpdf.text.Font.NORMAL, BaseColor.BLACK)
        );
        cellSupplier.addElement(Kepada);
        tables.addCell(cellSupplier);

        document.add(tables);
        com.itextpdf.text.Font f = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
        Paragraph Pembuka = new Paragraph("\nBerikut merupakan daftar kost KostKu \n\n",f);
        Pembuka.setIndentationLeft(20);
        document.add(Pembuka);
        PdfPTable tableHeader = new PdfPTable(new float[]{5,5,5,5,5});
        tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        tableHeader.getDefaultCell().setFixedHeight(30);
        tableHeader.setTotalWidth(PageSize.A4.getWidth());
        tableHeader.setWidthPercentage(100);
        //TODO 2.5 - Bagian ini tidak perlu diubah

        PdfPCell h1 = new PdfPCell(new Phrase("Nama Kost"));
        h1.setHorizontalAlignment(Element.ALIGN_CENTER);
        h1.setPaddingBottom(5);
        PdfPCell h2 = new PdfPCell(new Phrase("Alamat Kost"));
        h2.setHorizontalAlignment(Element.ALIGN_CENTER);
        h2.setPaddingBottom(5);
        PdfPCell h3 = new PdfPCell(new Phrase("Tipe Kost"));
        h3.setHorizontalAlignment(Element.ALIGN_CENTER);
        h3.setPaddingBottom(5);
        PdfPCell h4 = new PdfPCell(new Phrase("Luas Bangunan"));
        h4.setHorizontalAlignment(Element.ALIGN_CENTER);
        h4.setPaddingBottom(5);
        PdfPCell h5 = new PdfPCell(new Phrase("Sisa Kamar"));
        h5.setHorizontalAlignment(Element.ALIGN_CENTER);
        h5.setPaddingBottom(5);
        tableHeader.addCell(h1);
        tableHeader.addCell(h2);
        tableHeader.addCell(h3);
        tableHeader.addCell(h4);
        tableHeader.addCell(h5);
        PdfPCell[] cells = tableHeader.getRow(0).getCells();
        for (int j = 0; j < cells.length; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        document.add(tableHeader);
        PdfPTable tableData = new PdfPTable(new float[]{5,5,5,5,5});
        tableData.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableData.getDefaultCell().setFixedHeight(30);
        tableData.setTotalWidth(PageSize.A4.getWidth());
        tableData.setWidthPercentage(100);
        tableData.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        int arrLength = listPengumuman.size();
        for(int x=0;x<arrLength;x++){
            for(int i=0;i<cells.length;i++){
                if(i==0){
                    tableData.addCell(listKost.get(x).getNama_kost());
                }else if(i==1){
                    tableData.addCell(listKost.get(x).getAlamat_kost());
                }else if(i==2){
                    tableData.addCell(listKost.get(x).getTipe_kost());
                }else if(i==3){
                    tableData.addCell(String.valueOf(listKost.get(x).getLuas_kost()));
                }else{
                    tableData.addCell(String.valueOf(listKost.get(x).getSisa_kamar()));
                }
            }
        }
        document.add(tableData);
        com.itextpdf.text.Font h = new
                com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
                com.itextpdf.text.Font.NORMAL);
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String tglDicetak = sdf.format(currentTime);
        Paragraph P = new Paragraph("\nDicetak tanggal " + tglDicetak, h);
        P.setAlignment(Element.ALIGN_RIGHT);
        document.add(P);
        document.close();
        previewPdf();

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        //isikan code createPdfWrapper()
        int hasWriteStoragePermission = 0;
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("Izinkan aplikasi untuk akses penyimpanan?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new
                                                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }

    }

    private void createPdfWrapper2() throws FileNotFoundException, DocumentException {

        //isikan code createPdfWrapper()
        int hasWriteStoragePermission = 0;
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("Izinkan aplikasi untuk akses penyimpanan?",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new
                                                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf2();
        }

    }

    private void previewPdf() {
        //isikan code previewPdf()
        PackageManager packageManager = getContext().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent,
                PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                uri = FileProvider.getUriForFile(getActivity(), getContext().getPackageName()+".provider",
                        pdfFile);
            } else {
                uri = Uri.fromFile(pdfFile);
            }
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(uri, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            pdfIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //TODO 2.6 - Sesuaikan package dengan package yang anda buat
            getContext().grantUriPermission("com.ezraaudivano.kostku.ui.pdf", uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(pdfIntent);
        } else {
            FancyToast.makeText(getContext(),"Unduh pembuka PDF untuk menampilkan file ini",
                    FancyToast.LENGTH_LONG,FancyToast.WARNING,true).show();
        }
    }


    public void getPengumuman() {
        //Tambahkan tampil buku disini
        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan data pengumuman");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, PengumumanAPI.URL_SELECT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try{
                    JSONArray jsonArray = response.getJSONArray("data");
                    if(!listPengumuman.isEmpty())
                        listPengumuman.clear();

                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        Integer id = jsonObject.optInt("id");
                        String judul = jsonObject.optString("judul");
                        String subjudul = jsonObject.optString("subjudul");
                        String konten = jsonObject.optString("konten");

                        Pengumuman pengumuman = new Pengumuman(id, judul, subjudul, konten);
                        listPengumuman.add(pengumuman);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    private void getKost() {

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menampilkan Data Kost");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, KostAPI.URL_SELECT
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengambil data response json object yang berupa data mahasiswa
                    JSONArray jsonArray = response.getJSONArray("data");

                    if(!listKost.isEmpty())
                        listKost.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        //Mengubah data jsonArray tertentu menjadi json Object
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String id           = jsonObject.optString("id");
                        String nama_kost           = jsonObject.optString("nama_kost");
                        String tipe_kost        = jsonObject.optString("tipe_kost");
                        String alamat_kost        = jsonObject.optString("alamat_kost");
                        String emailPemilik        = jsonObject.optString("emailPemilik");
                        String luas_kost            = jsonObject.optString("luas_kost");
                        String sisa_kamar            = jsonObject.optString("sisa_kamar");
                        String imageUrl            = jsonObject.optString("image");

                        if(emailPemilik.equalsIgnoreCase(emailFB)) {
                            //Membuat objek user
                            Kost kost = new Kost(id, emailFB, nama_kost, tipe_kost, alamat_kost, Integer.parseInt(luas_kost), Integer.parseInt(sisa_kamar), imageUrl);
                            //Menambahkan objek user tadi ke list user
                            listKost.add(kost);
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Toast.makeText(view.getContext(), response.optString("message"),
                        Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), error.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}
