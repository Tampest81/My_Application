package com.example.my_application;

import androidx.annotation.NonNull;
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
import android.location.LocationManager;
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
        buttonGEO.setOnClickListener(new View.OnClickListener()
        {
            //
            @Override
            public void onClick(View view)
            {
                GPStracker g = new GPStracker(getApplication());
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
                public void onClick(View view)
                {
                    TirarFoto();
                }
        });

    }
    // Método para tirar a foto
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
        // criação da variável e atribuindo valores à ela
        public GPStracker(Context c)
        {
            context = c;
        }
        // criação do método GetLocation para retornar a localização
        public Location getLocation(){
        // verificação se a permissão está ativa, caso esteja desativada, irá printar através do comando Toast, "Não foi permitir"
            if(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED){
        // Mostra uma modal na tela "Não foi permitir" caso o usuário não tenha permitido o uso do fine location
                Toast.makeText(context, "Não foi permitir", Toast.LENGTH_SHORT).show();
                return null;
            }
            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGPSEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

        //Caso o gps esteja ativado, ele irá retornar a posição recebida atráves do lastknown location, que receberá a ultima posição providenciada
        //pelo Location manager gps provider, tudo isso apenas irá funcionar, após o requestLocation fizer a requisição das atualizações da posição
        //do usuário

            if(isGPSEnabled){
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 6000, 10, this);
                Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return l;
            } else {
        // Caso não esteja habilitado irá mostrar na tela, pedindo que o usuário habilite o GPS
                Toast.makeText(context, "Por favor, habitar o GPS!", Toast.LENGTH_LONG).show();
            }
        // recebendo o null para satisfazer todas as opções
            return null;
        }
        // Métodos para o GPStracker funcionar corretamente, ele pede a existência deles, mas não há a necessidade de ter um valor.
        @Override
        public void onProviderDisabled(@NonNull String provider) {    }
        //
        @Override
        public void onLocationChanged(@NonNull Location location) {    }
        //
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {    }
    }
}