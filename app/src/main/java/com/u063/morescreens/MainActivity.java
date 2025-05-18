package com.u063.morescreens;

import static com.u063.morescreens.utils.BitmapUtil.drawRect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.u063.morescreens.utils.Database.Database;
import com.u063.morescreens.utils.Database.ManageDatabase;
import com.u063.morescreens.utils.image.BitmapOperations;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img = findViewById(R.id.small);
        Bitmap b = Bitmap.createBitmap(800,600, Bitmap.Config.ARGB_8888);
        int size = 60;
        for(int i=0; i<10; i++){
            if(i%2==0) {
                for (int i1 = 0; i1 < 10; i1 += 2) {
                    for (int x = i1 * size; x < i1 * size + size; x++) {
                        for (int y = i * size; y < i * size + size; y++) {
                            b.setPixel(x, y, Color.BLACK);
                        }
                    }
                }
            } else {
                for (int i1 = -1; i1 < 10; i1 += 2) {
                    if(i1>=0) {
                        for (int x = i1 * size; x < i1 * size + size; x++) {
                            for (int y = i * size; y < i * size + size; y++) {
                                b.setPixel(x, y, Color.BLACK);
                            }
                        }
                    }
                }
            }
        }
        img.setImageBitmap(b);

        img1 = findViewById(R.id.big);
        //img1.setImageBitmap(readData(b));
    }

    private static int getAnInt(int yc, int y) {
        return y - yc;
    }

    public void open(View w){
        Intent i = new Intent(MainActivity.this,EditActivity.class);
        startActivity(i);
    }
    public void screens(View w){
        Intent i = new Intent(MainActivity.this,network_activity.class);
        startActivity(i);
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