package com.u063.morescreens;

import static android.view.View.GONE;

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
import com.u063.morescreens.utils.network.Client;
import com.u063.morescreens.utils.network.Server;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class network_activity extends AppCompatActivity {
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
                            Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                            Random r = new Random();
                            b = BitmapUtil.setBackground(b, Color.rgb(255,0,0));
                            b = BitmapUtil.drawRect(b,10,10,50,50,Color.rgb(0,r.nextInt(),0));
                            byte[] bytes = BitmapOperations.getByteArray(0, b);
                            s.send(bytes);
                        }
                    };
                    Timer timer = new Timer("hi");
                    timer.schedule(timerTask,10000,10000);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Integer> a = new ArrayList<>();
                client.connect("192.168.0.109",5050);
                while(true) {
                    Bitmap bit = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
                    for (int x = 0; x < 100; x++) {
                        for (int y = 0; y < 100; y++) {
                            if(a.size()>x*y) {
                                bit.setPixel(x, y,Color.rgb(a.get(x * y),0,0));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //bit = BitmapUtil.setBackground(bit,Color.RED);
                                    img.setImageBitmap(bit);
                                }
                            });
                        }
                    }
                    a = client.read();
                }

            }
        }).start();
        Button host = findViewById(R.id.host);
        host.setVisibility(GONE);
        host = findViewById(R.id.connect);
        host.setVisibility(GONE);
    }
    private Bitmap readData(Bitmap bitmap){
        Bitmap b = bitmap;
        int x=10;
        int y=10;
        int sx=10;
        int sy=10;
        ArrayList<Database> database = ManageDatabase.read(this);
        for(int i=0; i<database.size(); i++){
            x = Integer.parseInt(database.get(i).getVals("posX").get(0));
            y = Integer.parseInt(database.get(i).getVals("posY").get(0));
            sx = Integer.parseInt(database.get(i).getVals("sX").get(0));
            sy = Integer.parseInt(database.get(i).getVals("sY").get(0));
        }
        return BitmapOperations.getHalfImage(b,x,y,sx,sy);
    }
}
