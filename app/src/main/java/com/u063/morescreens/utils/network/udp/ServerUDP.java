package com.u063.morescreens.utils.network.udp;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class ServerUDP {
    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    ArrayList<InetAddress> inetAddresses = new ArrayList<>();
    ArrayList<Integer> ports = new ArrayList<>();
    public ServerUDP() {
        try {
            socket = new DatagramSocket(4445);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendBitmap(Bitmap bitmap){
        byte[] bb = new byte[256];
        byte[] b = new byte[bitmap.getWidth()*bitmap.getHeight()];
        for(int i=0; i<b.length; i++){
            b[i] = (byte) Color.red(bitmap.getPixel(i%bitmap.getWidth(), i/bitmap.getWidth()));
        }
        DatagramPacket packet = new DatagramPacket(bb,bb.length);

        try {
            socket.receive(packet);
            inetAddresses.add(packet.getAddress());
            ports.add(packet.getPort());

            DatagramPacket packetSend = new DatagramPacket(b, b.length, packet.getAddress(),packet.getPort());
            socket.send(packetSend);
            Log.e("","recived: "+packet.getAddress()+packet.getPort());
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
