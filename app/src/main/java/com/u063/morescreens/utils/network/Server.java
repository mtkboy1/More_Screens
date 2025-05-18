package com.u063.morescreens.utils.network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

public class Server {
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets = new ArrayList<>();
    //private Socket client;
    public Server(){
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(5050));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            while (true) {
                                Socket sock = serverSocket.accept();
                                //client = sock;
                                sockets.add(sock);
                                /*byte  b[] = new byte[127];
                                for(byte i=0; i<(byte) 127; i++){
                                    b[i]=i;
                                }
                                send(b);*/
                                Log.e("Client","new");
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }).start();
            Log.e("",serverSocket.getLocalSocketAddress().toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Server(int type){
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(5050));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void send(byte[] bytes){
        for (int i = 0; i < sockets.size(); i++) {
            if(!sockets.get(i).isClosed()) {
                try {
                    Log.e("Client", "" + i);
                    OutputStream o = sockets.get(i).getOutputStream();
                    o.write(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void send(byte[] bytes, int id){
        if(!sockets.get(id).isClosed()) {
            try {
                Log.e("Client", "" + id);
                OutputStream o = sockets.get(id).getOutputStream();
                o.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void getClient(){
        Socket sock = null;
        try {
            sock = serverSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sockets.add(sock);
        Log.e("Client","new");
    }
    public ArrayList<Socket> getSockets(){
        return sockets;
    }
}