package com.u063.morescreens;

import static android.view.View.GONE;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
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
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
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
                            b = BitmapUtil.setBackground(b, Color.rgb(255,255,255));
                            b = BitmapUtil.drawRect(b,50,10,50,50,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,40,40,Color.rgb(50,0,0));
                            b = BitmapUtil.drawRect(b,10,10,10,20,Color.rgb(50,0,0));
                            String[] bytes = BitmapOperations.getStringArray(0, b);
                            s.send(bytes);
                        }
                    };
                    Timer timer = new Timer("hi");
                    timer.schedule(timerTask,5000,300);
                }
            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }
    public void connect(View view){
        ImageView img = findViewById(R.id.src);
        Client client = new Client("192.168.0.109",5050);
        Context c = this;
        client.setSx(sx);
        client.setSy(sy);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> a = new ArrayList<>();
                client.connect("192.168.0.109",5050);
                Bitmap bit = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
                while(true) {
                    int y=0;
                    int x=0;
                    for (int z = 0; z < a.size(); z++) {
                        if(a.get(z).equals("new")) {
                            y+=1;
                            x=0;
                        } else {
                            x+=1;
                            if(x<bit.getWidth()&&y<bit.getHeight()) {
                                if(mathUtil.isNumeric(a.get(z))) {
                                    bit.setPixel(x, y, Color.rgb(Integer.parseInt(a.get(z)), 0, 0));
                                } else {
                                    bit.setPixel(x, y, Color.rgb(0, 0, 0));
                                }
                            }
                        }
                    }
                    bit=BitmapOperations.readData(bit,c);
                    Bitmap finalBit = bit;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            img.setImageBitmap(finalBit);
                        }
                    });
                    a = client.readStr();
                }

            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }

}
