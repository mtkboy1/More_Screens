package com.u063.morescreens.utils.Database;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {
    String name;
    Context context;
    HashMap<String, ArrayList<String>> table = new HashMap<>();
    ArrayList<String> keys = new ArrayList<>();
    public Database(String name, Context context){
        this.name = name;
        this.context = context;
        read();
    }
    private void save(){
        File f = new File(context.getFilesDir()+"/"+name);
        f.mkdir();
        for(int i=0; i<keys.size(); i++) {
            f = new File(context.getFilesDir() + "/" + name + "/" + keys.get(i));
            try {
                f.createNewFile();
            } catch (IOException e) {throw new RuntimeException(e);}
            try {
                OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(f));
                ArrayList<String> strs = getVals(keys.get(i));
                for(int ch=0; ch<strs.size(); ch++){
                    String s = strs.get(ch);
                    if(ch!=strs.size()-1){
                        outputStream.write(s+"\n");
                    } else {
                        outputStream.write(s);
                    }
                }
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {throw new RuntimeException(e);}
        }
    }
    public void createTable(String key){
        table.put(key,new ArrayList<String>());
        keys.add(key);
    }
    public void putVal(String key, String val){
        table.get(key).add(val);
        save();
    }
    public void removeVal(String key, int i){
        table.get(key).remove(i);
        save();
    }
    public void removeAll(String key){
        for(int i=0; i<table.get(key).size(); i++){
            table.get(key).remove(0);
        }
    }
    public void read(){
        File f = new File(context.getFilesDir()+"/"+name);
        File fs[] = f.listFiles();

        if (fs != null) {
            for(File ff:fs){
                table.put(ff.getName(),new ArrayList<String>());
                try {
                    BufferedReader in = new BufferedReader(new FileReader(context.getFilesDir()+"/"+name+"/"+ff.getName()));
                    String s = "in.readLine()";
                    while((s=in.readLine())!=null){
                        putVal(ff.getName(),s);
                        Log.e("daar",""+s);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    public ArrayList<String> getVals(String key){
        return table.get(key);
    }
}
