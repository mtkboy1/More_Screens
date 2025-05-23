package com.u063.morescreens.utils.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
                    for(int z=0; z<bytes.length; z++) {
                        Log.e("Client", "" + i);
                        BufferedWriter o = new BufferedWriter(new OutputStreamWriter(sockets.get(i).getOutputStream()));
                        //OutputStream o = sockets.get(i).getOutputStream();
                        //o.write(bytes);
                        o.write(bytes[z]);
                        o.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void send(String[] bytes){
        for (int i = 0; i < sockets.size(); i++) {
            if(!sockets.get(i).isClosed()) {
                try {
                    for(int z=0; z<bytes.length; z++) {
                        Log.e("Client", "" + i);
                        BufferedWriter o = new BufferedWriter(new OutputStreamWriter(sockets.get(i).getOutputStream()));
                        //OutputStream o = sockets.get(i).getOutputStream();
                        //o.write(bytes);
                        if(!bytes[z].equals("null255")) {
                            o.write(bytes[z]);
                        }
                        o.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public void send(byte[] bytes, int id){
        for (int i = 0; i < sockets.size(); i++) {
            if (!sockets.get(id).isClosed()) {
                try {
                    Log.e("Client", "" + id);
                    for (int z = 0; z < bytes.length; z++) {
                        //Log.e("Client", "" + i);
                        String bb = ""+(char) bytes[z];
                        BufferedWriter o = new BufferedWriter(new OutputStreamWriter(sockets.get(i).getOutputStream()));
                        //OutputStream o = sockets.get(i).getOutputStream();
                        //o.write(bytes);
                        o.write((String) bb+"\n");
                        o.flush();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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