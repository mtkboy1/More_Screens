package com.u063.morescreens.utils.Database;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

public class ManageDatabase {
    public ManageDatabase(){

    }
    public static ArrayList<Database> read(Context context){
        ArrayList<Database> databases = new ArrayList<>();
        File f = new File(context.getFilesDir()+"/");
        File fs[] = f.listFiles();
        for(File file:fs){
            databases.add(new Database(file.getName(), context));
        }
        return databases;
    }
}
