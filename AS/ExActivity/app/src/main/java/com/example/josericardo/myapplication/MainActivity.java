package com.example.josericardo.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    public class DrawView extends View {

        public DrawView(Context context){
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint p = new Paint();
            p.setColor(Color.RED);

            canvas.drawCircle(100, 100, 100, p);

            Paint p2 = new Paint();
            p2.setColor(Color.BLACK);
            p2.setTextSize(30);

            canvas.drawText("Pontuacao: ", 200, 400, p2);
        }
    }



    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(R.layout.activity_main);
    }

    public void pressionarBotao(View view){
        //TextView idValor = findViewById(R.id.txtValor);
        //idValor.setText("Teste");

        Intent intent = new
                Intent(MainActivity.this, Activity2.class);
        startActivity(intent);

    }

    public void carregarPagina(View view){
        Uri uri = Uri.parse("http://wwww.google.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Tag", "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Tag", "onResume() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Tag", "onRestart() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Tag", "onDestroy() called");
    }
}
