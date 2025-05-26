package com.u063.morescreens.utils.network;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import com.u063.morescreens.utils.mathUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private final Socket socket;
    private int sx,sy;
    Bitmap bit;
    public Client(){
        socket = new Socket();
    }
    public void connect(String ip, int port){
        try {
            if(!socket.isConnected()) {
                socket.connect(new InetSocketAddress(ip, port));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void init(){
        bit = Bitmap.createBitmap(sx, sy, Bitmap.Config.ARGB_8888);
    }
    public ArrayList<Integer> read(){
        ArrayList<Integer> i = new ArrayList<>();
        Boolean thatsAll = false;
        while (!thatsAll){
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String s = "";

                for(int iw=0; iw<100*100; iw++){
                    //while(b){
                    int b = bf.read();
                    i.add(b);
                    Log.e("","recived: "+ b);

                }
                return i;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public ArrayList<String> readStr(){
        ArrayList<String> i = new ArrayList<>();
        Boolean thatsAll = false;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = "";

            for (int z = 0; z < 100 * 100; z++) {
                //while(b){
                s = bf.readLine();
                //Log.e("", s);
                i.add(s);
            }
            Log.e("", "thats all");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    public Bitmap readBitmap(int sx, int sy){
        /*ArrayList<String> a = new ArrayList<>();
        a = readStr();*/
        int y=0;
        int x=0;
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = "";

            for (int z = 0; z < sx * sy; z++) {
                //while(b){
                s = bf.readLine();
                if(s.length()>0) {
                    int b = (int) s.charAt(0);
                    // Log.e("", s);
                    //Log.e("", ""+b);
                    if (s.equals("n")) {
                        y += 1;
                        x = 0;
                    } else {
                        x += 1;
                        if (x < bit.getWidth() && y < bit.getHeight()) {
                        /*if(mathUtil.isNumeric(s)) {
                            bit.setPixel(x, y, Color.rgb(Integer.parseInt(b), 0, 0));
                        }*/
                            bit.setPixel(x, y, Color.rgb(b, 0, 0));
                        }
                    }
                }
            }
            Log.e("", "thats all");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bit;
    }
    public void setSx(int sx) {
        this.sx = sx;
    }
    public void setSy(int sy) {
        this.sy = sy;
    }
}