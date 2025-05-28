package com.u063.morescreens.utils;

import android.graphics.Bitmap;

public class BitmapUtil {
    static public Bitmap setBackground(Bitmap bitmap,int color){
        Bitmap bit = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        for(int x=0; x<bit.getWidth(); x++){
            for(int y=0; y<bit.getHeight(); y++){
                bit.setPixel(x,y,color);
            }
        }
        return bit;
    }
    static public Bitmap drawRect(Bitmap bitmap, int x, int y, int sizeX, int sizeY, int color){ //рисуем квадрат
        Bitmap bit = bitmap;
        int sY = sizeY+y;
        int sX = sizeX+x;
        if(y>=bit.getHeight()){
            y=bit.getHeight()-1;
        }
        if(x>=bit.getWidth()){
            x=bit.getWidth()-1;
        }
        if(x+sizeX>=bit.getWidth()){
            sX = bit.getWidth()-1;
        }
        if(y+sizeY>=bit.getHeight()){
            sY = bit.getHeight()-1;
        }
        for (int i = x; i < sX; i++) {
            bit.setPixel(i, y, color);
        }
        for(int i=x; i < sX; i++){
            bit.setPixel(i,sY,color);
        }
        for (int i = y; i < sY; i++) {
            bit.setPixel(sX, i, color);
        }
        for(int i=y; i<sY; i++){
            bit.setPixel(x,i,color);
        }

        return bit;
    }
}
