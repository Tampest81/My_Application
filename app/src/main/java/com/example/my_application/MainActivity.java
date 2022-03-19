package com.example.my_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    // Criaçao das variáveis e atribuição do nome
    private ImageView imageViewFoto;
    private Button buttonGEO;
    //Método override para sobrescrição do AppCompatActivity
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Atribuição da variável buttonGEO, atribuindo o botão na cena na variável
        // com o uso do id
        // após isso, puxa da super classe a permissão.
        buttonGEO = (Button) findViewById(R.id.button3);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        //
        buttonGEO.setOnClickListener(new View.OnClickListener(){
            //
            @Override
            public void onClick(View view)
            {
                GPStracker = new GPStracker(getApplication());
                Location l= g.getLocation();

                // se a localização não for nula, ela ira mostrar na tela através do comando Toast as variáveis lat e lon.
                if (l != null)
                {
                    double lat = l.getAltitude();
                    double lon = l.getAltitude();
                    Toast.makeText(getApplicationContext(),"Latitude: " + lat +
                                                            "\n Longitude: " + lon,
                                   Toast.LENGTH_LONG).show();
                }
            }
        });
        // Pede a permissão da Camera
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},0);
        }
        // Atribuição da variável imageViewFoto, atribuindo a imagem variável à cena.
        imageViewFoto = (ImageView) findViewById(R.id.imageView3);
        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener()
        {
            @Override
                public void OnClick(View view)
                {
                    TirarFoto();
                }
        });

    }

    private void TirarFoto()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }
    //
    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            // Verificação se a foto foi tirada...
            // Coleta da informação -> foto e atribuição dela na variável imageViewFoto
            if(requestCode == 1 && resultCode == RESULT_OK)
            {
                Bundle extras = data.getExtras();
                Bitmap imagem = (Bitmap) extras.get("data");
                imageViewFoto.setImageBitmap(imagem);
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    public class GPStracker implements LocationListener
    {
        Context context;

        public GPStracker(Context c)
        {
            context = c;
        }
        public  Location getLocation()
        {
            if(ContextCompat.checkSelfPermission(context, Manifest))
        }
    }
}