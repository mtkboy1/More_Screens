package com.u063.morescreens;

import static com.u063.morescreens.utils.BitmapUtil.drawRect;
import static com.u063.morescreens.utils.BitmapUtil.setBackground;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.u063.morescreens.utils.Database.Database;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    Bitmap b = Bitmap.createBitmap(640,480, Bitmap.Config.ARGB_8888);
    ArrayList<Database> database = new ArrayList<>();
    ArrayList<EditText> posX = new ArrayList<>();
    ArrayList<EditText> posY = new ArrayList<>();
    ArrayList<EditText> sizeX = new ArrayList<>();
    ArrayList<EditText> sizeY = new ArrayList<>();
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.editor_activity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editor), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        img = findViewById(R.id.img);
        b = setBackground(b,Color.BLACK);
        img.setImageBitmap(b);


    }

    public void addScreen(View w){
        database.add(new Database(""+posX.size(),this));
        database.get(posX.size()).read();
        database.get(posX.size()).createTable("posX");
        database.get(posX.size()).createTable("posY");
        database.get(posX.size()).createTable("sX");
        database.get(posX.size()).createTable("sY");
        TextView tx = new TextView(this);
        tx.setText("Screen " + posX.size());
        EditText etx = new EditText(this);
        etx.setHint("pos X");
        EditText ety = new EditText(this);
        ety.setHint("pos Y");
        EditText etsx = new EditText(this);
        etsx.setHint("size X");
        EditText etsy = new EditText(this);
        etsy.setHint("size Y");

        LinearLayout ll = findViewById(R.id.displays);
        ll.addView(tx);
        ll.addView(etx);
        ll.addView(ety);
        ll.addView(etsx);
        ll.addView(etsy);

        posX.add(etx);
        posY.add(ety);
        sizeX.add(etsx);
        sizeY.add(etsy);

    }
    public void update(View w){
        b = setBackground(b,Color.BLACK);
        for(int i=0; i<posX.size(); i++){
            database.get(i).removeAll("posX");
            database.get(i).removeAll("posY");
            database.get(i).removeAll("sX");
            database.get(i).removeAll("sY");
            int x = Integer.parseInt(posX.get(i).getText().toString());
            int y = Integer.parseInt(posY.get(i).getText().toString());
            int sX = Integer.parseInt(sizeX.get(i).getText().toString());
            int sY = Integer.parseInt(sizeY.get(i).getText().toString());
            database.get(i).putVal("posX",""+x);
            database.get(i).putVal("posY",""+y);
            database.get(i).putVal("sX",""+sX);
            database.get(i).putVal("sY",""+sY);
            b=drawRect(b,x,y,sX,sY,Color.RED);
        }
        img.setImageBitmap(b);
    }
}
