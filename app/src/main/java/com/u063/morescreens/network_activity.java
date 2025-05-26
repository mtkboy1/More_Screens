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

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class network_activity extends AppCompatActivity {
    private Bitmap bitmaps;
    int sy = 400, sx = 300;
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
    Context c = this;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            Bundle msg = message.getData();
            ArrayList<String> a = msg.getStringArrayList("data");

            return false;
        }
    });
    public void host(View view){
        Server s = new Server(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    s.getClient();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            Bitmap b = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
                            Random r = new Random();
                            b = BitmapUtil.setBackground(b, Color.rgb(r.nextInt(),255,255));
                            b = BitmapUtil.drawRect(b,50,10,50,50,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,40,40,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,10,20,Color.rgb(50,0,0));
                            //String[] bytes = BitmapOperations.getStringArray(0, b);
                            //byte[][] bytes = BitmapOperations.getByteArray(0,b);
                            //String[] bytes = BitmapOperations.getByteArray(0,b);
                            s.sendBitmap(0,b);
                        }
                    };
                    Timer timer = new Timer("hi");
                    timer.schedule(timerTask,100,100);
                }
            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }
    public void connect(View view){
        Client client = new Client("192.168.0.109",5050);
        client.setSx(sx);
        client.setSy(sy);
        ImageView img = findViewById(R.id.src);
        client.init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //ArrayList<String> a = new ArrayList<>();
                client.connect("192.168.0.109",5050);
                while(true) {
                    //client.readStr();
                    Bitmap bit = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
                    bit = client.readBitmap(sx,sy);
                    Bitmap finalBit=bit;//BitmapOperations.readData(bit,c);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(finalBit);
                        }
                    });

                    Bundle b = new Bundle();
                    //a = client.readStr();
                    /*b.putStringArrayList("data",a);
                    Message msg = new Message();
                    msg.setData(b);
                    handler.sendMessage(msg);*/
                }

            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }

}
