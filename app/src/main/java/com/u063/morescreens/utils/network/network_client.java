package com.u063.morescreens.utils.network;

import android.graphics.Bitmap;

import com.u063.morescreens.utils.network.udp.ClientUDP;
import com.u063.morescreens.utils.network.udp.ServerUDP;

public class network_client {
    private int type = 0;
    private Client client = new Client();
    private ClientUDP clientUDP;
    public network_client(int type,int sx, int sy){
        this.type = type;

        if(type==0) {
            client.setSy(sy);
            client.setSx(sx);
            client.init();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    client.connect("192.168.0.109",5050);
                }
            });
        }
        if(type==1) {
            clientUDP = new ClientUDP("192.168.0.109", 5050);
        }
    }
    public Bitmap readBitmap(int sx, int sy){
        if(type==0){
            client.connect("192.168.0.109",5050);
            return client.readBitmap(sx,sy);
        } else {
            return clientUDP.readBitmap(sx,sy);
        }
    }
}
