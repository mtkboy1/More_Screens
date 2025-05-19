package com.u063.morescreens.utils.image;

import android.graphics.Bitmap;
import android.graphics.Color;

public class BitmapOperations {
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
    public static byte[] getByteArray(int type, Bitmap bitmap){
        byte b[] = new byte[(bitmap.getHeight()*bitmap.getWidth())+1];
        for(int x=0; x<bitmap.getWidth(); x++){
            for(int y=0; y<bitmap.getHeight(); y++){
                if(type==0) {
                    b[x*y] = (byte) Color.red(bitmap.getPixel(x, y));
                }
                if(type==1) {
                    b[x*y] = (byte) Color.green(bitmap.getPixel(x, y));
                }
                if(type==2) {
                    b[x*y] = (byte) Color.blue(bitmap.getPixel(x, y));
                }
            }
        }
        b[b.length-1] = 0;
        return b;
    }
}
