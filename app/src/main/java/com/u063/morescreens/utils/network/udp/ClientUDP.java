package com.u063.morescreens.utils.network.udp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientUDP {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf = new byte[256];

    public ClientUDP(String ip, int port) {
        try {
            address = InetAddress.getByName(ip);
            socket = new DatagramSocket();
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public Bitmap readBitmap(int sx, int sy) {
        byte b[] = new byte[sx*sy];
        Bitmap bitmap = Bitmap.createBitmap(sx,sy, Bitmap.Config.ARGB_8888);
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
            socket.send(packet);
            packet = new DatagramPacket(b, b.length);
            socket.receive(packet);
            byte[] received = packet.getData();
            Log.e("","recived: "+ b);
            for(int i=0; i<sx*sy; i++){
                bitmap.setPixel(i%sx,i/sx, Color.rgb(received[i],0,0));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }

    public void close() {
        socket.close();
    }
}
