package com.hakiki95.databarangwithbarcodezxing;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public  void ScanBarcode(View v) {
        try{
            Intent intentToApps = new Intent(ACTION_SCAN);
            intentToApps.putExtra("SCAN_MODE","PRODUCT_MODE");
            startActivityForResult(intentToApps,0);
        }catch (ActivityNotFoundException e) {
            showDialog(Home.this,"Scanner Apps Tidak ditemukan","Download Aplikasinya di GooglePlay Store?",
                    "Iya","Tidak").show();
        }

    }

    public static AlertDialog showDialog (final Activity act, CharSequence title,CharSequence messege, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(messege);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        try {
                            act.startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                        }

                    }
                }

        );
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return  downloadDialog.show();
    }

    public  void  onActivityResult (int requestcode,int resultcode, Intent intent) {

        if (requestcode==0){
            if(resultcode== RESULT_OK){
                String content =  intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Intent godetail = new Intent(getApplicationContext(),DetailBarang.class);
                godetail.putExtra("paramBarcode",content);
                startActivity(godetail);
                /*Toast toast = Toast.makeText(this, "Content: " + content + "Format: " + format, Toast.LENGTH_LONG);
                toast.show();
                btn_detail.setText(content);*/
            }

        }
    }
}
