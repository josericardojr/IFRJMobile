package com.josericardojunior.persistenciadadosfim;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.josericardojunior.persistenciadadosfim.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileActivity extends AppCompatActivity {

    EditText edtText;
    Button btnSave;
    Button btnRetrieve;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.file_persistence);

        edtText = (EditText) findViewById(R.id.edtLongText);
        btnSave = (Button) findViewById(R.id.btnFileSave);
        btnRetrieve = (Button) findViewById(R.id.btnLoadFile);


    }


    public void FileButtonAction(View view){

        if (view == btnSave){
            FileOutputStream fos = null;

            String text = edtText.getText().toString();

            try {
                fos = openFileOutput("Teste.xpto", MODE_APPEND);
                fos.write(text.getBytes());
                fos.close();
            } catch (FileNotFoundException fnf){
                fnf.printStackTrace();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        } else if (view == btnRetrieve){
            FileInputStream fis = null;


            try {
                fis = openFileInput("Teste.xpto");

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(fis));

                String line = "";
                String content = "";

                line = br.readLine();
                while ( line != null){
                    content += line + "\n";
                    line = br.readLine();
                }

                br.close();
                fis.close();

                edtText.setText(content);
            } catch (FileNotFoundException fnf){
                fnf.printStackTrace();
            } catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
}
