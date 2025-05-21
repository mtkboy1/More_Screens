package com.u063.morescreens.utils.network;

import android.util.Log;

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
    public Client(String ip, int port){
        socket = new Socket();
    }
    public void connect(String ip, int port){
        try {
            socket.connect(new InetSocketAddress(ip,port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            for (int z = 0; z < sx * sy; z++) {
                //while(b){
                s = bf.readLine();
                i.add(s);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.e("", "thats all");
        return i;
    }

    public void setSx(int sx) {
        this.sx = sx;
    }
    public void setSy(int sy) {
        this.sy = sy;
    }
}