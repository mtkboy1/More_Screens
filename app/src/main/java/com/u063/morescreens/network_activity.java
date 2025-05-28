package com.u063.morescreens;

import static android.view.View.GONE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.u063.morescreens.utils.BitmapUtil;
import com.u063.morescreens.utils.Database.Database;
import com.u063.morescreens.utils.Database.ManageDatabase;
import com.u063.morescreens.utils.image.BitmapOperations;
import com.u063.morescreens.utils.mathUtil;
import com.u063.morescreens.utils.network.Client;
import com.u063.morescreens.utils.network.Server;
import com.u063.morescreens.utils.network.network_client;
import com.u063.morescreens.utils.network.network_socket;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class network_activity extends AppCompatActivity {
    int TypeOfServer = 0; //TCP - 0,    UDP - 1
    int sy = 400, sx = 300; //Размер bitmap-а в пикселях
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.network_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.network), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void host(View view){ // Сервер
        network_socket server = new network_socket(TypeOfServer);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    server.getClient();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Bitmap b = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
                            Random r = new Random(); //рандом (используется для проверки правильности передачи на клиент
                            b = BitmapUtil.setBackground(b, Color.rgb(255,255,255));
                            b = BitmapUtil.drawRect(b,50,10,50,50,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,40,40,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,10,20,Color.rgb(50,0,0));
                            Bitmap finalB = b;
                            server.sendBitmap(finalB,TypeOfServer);
                        }
                    };
                    Timer timer = new Timer("hi");
                    timer.schedule(timerTask,10,10);


                }
            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }
    public void connect(View view){
        network_client client = new network_client(TypeOfServer,sx,sy);
        ImageView img = findViewById(R.id.src);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    Bitmap bit = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
                    bit = client.readBitmap(sx,sy);//client.readBitmap(sx,sy);
                    Bitmap finalBit=bit;//BitmapOperations.readData(bit,c);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(finalBit);
                        }
                    });
                }

            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }

}
