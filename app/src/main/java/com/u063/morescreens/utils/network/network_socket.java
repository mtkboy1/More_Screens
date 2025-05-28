package com.u063.morescreens.utils.network;

import android.graphics.Bitmap;

import com.u063.morescreens.utils.network.udp.ServerUDP;

import java.net.Socket;

public class network_socket {
    private int type = 0;
    private Server server;
    private ServerUDP serverUDP;
    public network_socket(int type){
        this.type = type;
        if(type==0) {
            server = new Server(0);

        }
        if(type==1) {
            serverUDP = new ServerUDP();
        }
    }
    public void getClient(){
        if(type==0) {
            server.getClient();
        }
    }
    public void sendBitmap(Bitmap bitmap, int id){
        if(type==0) {
            server.sendBitmap(id,bitmap);
        } else {
            serverUDP.sendBitmap(bitmap);
        }
    }
}
