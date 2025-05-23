package com.u063.morescreens.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import com.u063.morescreens.utils.Database.Database;
import com.u063.morescreens.utils.Database.ManageDatabase;

import java.util.ArrayList;

public class BitmapOperations {
    public static Bitmap readData(Bitmap bitmap, Context context){
        Bitmap b = bitmap;
        int x=10;
        int y=10;
        int sx=10;
        int sy=10;
        ArrayList<Database> database = ManageDatabase.read(context);
        for(int i=1; i<database.size(); i++){
            x = Integer.parseInt(database.get(database.size()-1).getVals("posX").get(0));
            y = Integer.parseInt(database.get(database.size()-1).getVals("posY").get(0));
            sx = Integer.parseInt(database.get(database.size()-1).getVals("sX").get(0));
            sy = Integer.parseInt(database.get(database.size()-1).getVals("sY").get(0));
        }
        return BitmapOperations.getHalfImage(b,x,y,sx,sy);
    }
    public static ArrayList<Integer> readData(Context context){
        ArrayList<Integer> b = new ArrayList<>();
        int x=10;
        int y=10;
        int sx=10;
        int sy=10;
        ArrayList<Database> database = ManageDatabase.read(context);
        for(int i=0; i<database.size(); i++){
            x = Integer.parseInt(database.get(database.size()-1).getVals("posX").get(0));
            y = Integer.parseInt(database.get(database.size()-1).getVals("posY").get(0));
            sx = Integer.parseInt(database.get(database.size()-1).getVals("sX").get(0));
            sy = Integer.parseInt(database.get(database.size()-1).getVals("sY").get(0));
        }
        b.add(x);
        b.add(y);
        b.add(sx);
        b.add(sy);
        return b;
    }
    public static Bitmap getHalfImage(Bitmap bitmap,int xc, int yc, int xs, int ys){
        Bitmap b = Bitmap.createBitmap(400,300, Bitmap.Config.ARGB_8888);
        int px = (b.getWidth())/xs;//b.getWidth()/(bitmap.getWidth()/2); //Кол-во пикселей по x
        int py = (b.getHeight())/ys;// b.getHeight()/bitmap.getHeight(); //Кол-во пикселей по y

        for(int x=xc; x<xc+xs; x++){
            for(int y=yc; y<yc+ys; y++){
                for(int ix=(x-xc)*px; ix<(x-xc)*px+px; ix++){
                    for(int iy = (y-yc) *py; iy<(y-yc)*py+py; iy++){
                        b.setPixel(ix,iy,bitmap.getPixel(x,y));
                    }
                }
            }
        }
        return b;
    }
    public static String[] getByteArray(int type, Bitmap bitmap){
        /*byte b[] = new byte[bitmap.getWidth()*bitmap.getHeight()];
        for(int x=0; x<bitmap.getWidth(); x++){
            for(int y=0; y<bitmap.getHeight(); y++){
                if(type==0) {
                    b[x+y] = (byte) Color.red(bitmap.getPixel(x, y));
                }
                if(type==1) {
                    b[x+y] = (byte) Color.green(bitmap.getPixel(x, y));
                }
                if(type==2) {
                    b[x+y] = (byte) Color.blue(bitmap.getPixel(x, y));
                }
            }
        }*/
        String b[] = new String[bitmap.getHeight()];
        for(int y=0; y<bitmap.getHeight(); y++){
            for(int x=0; x<bitmap.getWidth(); x++){
                b[y] += (char) Color.red(bitmap.getPixel(x, y));
                b[y] += "\n";
            }
            b[y] += "new";
            b[y] += "\n";
        }
        return b;
    }
    public static String[] getStringArray(int type, Bitmap bitmap){
        String b[] = new String[bitmap.getHeight()];
        for(int y=0; y<bitmap.getHeight(); y++){
            for(int x=0; x<bitmap.getWidth(); x++){
                b[y] += Color.red(bitmap.getPixel(x, y));
                b[y] += "\n";
            }
            b[y] += "new";
            b[y] += "\n";
        }
        return b;
    }
}
