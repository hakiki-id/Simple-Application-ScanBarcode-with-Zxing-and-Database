package com.hakiki95.databarangwithbarcodezxing;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hakiki on 11/01/2016.
 */
public class DetailBarang extends AppCompatActivity {
    TextView namabarang, stockbarang, hargabarang;
    Map<String,String> getCode = new HashMap<String,String>();
    RequestQueue requesDataqueue ;
    Button back ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailbarang);

        Intent getdata = getIntent();
        String barcode = getdata.getExtras().getString("paramBarcode");

        namabarang = (TextView) findViewById(R.id.tvNamaBarang);
        stockbarang = (TextView) findViewById(R.id.tvStock);
        hargabarang = (TextView) findViewById(R.id.tvHarga);
        back =(Button) findViewById(R.id.btnback);

        getCode.put("parambarcodedariandroid",barcode);

        requesDataqueue = Volley.newRequestQueue(getApplicationContext());

        String urls = "http://192.168.43.19/coba/dataBarang/get_tbAngkaLikeID.php" ;

        JsonObjectRequest requestData = new JsonObjectRequest(Request.Method.POST, urls, new JSONObject(getCode),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            //Toast.makeText(DetailBarang.this, response.toString(),Toast.LENGTH_LONG).show();
                        try {
                            JSONArray array = response.getJSONArray("databarang");

                            for (int i =0; i<array.length(); i++) {
                                JSONObject databarang = array.getJSONObject(i);
                                String nama = databarang.getString("nama_barang");
                                String stok = databarang.getString("stok");
                                String harga = databarang.getString("harga");

                                namabarang.setText(nama);
                                stockbarang.setText(stok + " Pack");
                                hargabarang.setText("Rp. " + harga + "/pack");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR VOLLE",error.getMessage());
                    }
                });

        requesDataqueue.add(requestData);
        }

    public  void btnBack (View v) {
        Intent goback = new Intent(DetailBarang.this,Home.class);
        startActivity(goback);
        finish();
    }
}
