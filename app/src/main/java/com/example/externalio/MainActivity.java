package com.example.externalio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    static final int READ_BLOCK_SIZE = 100;

    private String filename="dellatan.txt";
    private String filepath="dellatan.externalio";
    File myFile;
    String myTyped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText txtbox = findViewById(R.id.txtbox);
        final Button btnCle = findViewById(R.id.btnCle);
        final Button btnRea = findViewById(R.id.btnRea);
        final Button btnWri = findViewById(R.id.btnWri);

        btnCle.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtbox.setText("");
                    }
                }
        );
        btnRea.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            FileInputStream fis = new FileInputStream(myFile);
                            DataInputStream in = new DataInputStream(fis);
                            BufferedReader br =
                                    new BufferedReader(new InputStreamReader(in));
                            String strLine;
                            while ((strLine = br.readLine()) != null) {
                                myTyped = myTyped + strLine;
                            }
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), "File saved externally!",
                                Toast.LENGTH_SHORT).show();

                    }
                }
        );
        btnWri.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            FileOutputStream fos = new FileOutputStream(myFile);
                            fos.write(txtbox.getText().toString().getBytes());
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getBaseContext(), "File saved externally!",
                                Toast.LENGTH_SHORT).show();


                    }
                }
        );

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            btnWri.setEnabled(false);
        }
    }

    public static boolean isExternalStorageReadOnly() {

        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;

    }

}
