package cribbage;

import java.io.*;

import ch.aplu.jcardgame.*;
import java.util.ArrayList;

/**
 * W09 team 03
 * Instance object to dump the log to file
 */
public class Log{
    FileOutputStream o = null;
    static Log instance = null;
    static void init(String file){
        if(instance == null){
            instance = new Log(file);
        }
    }
    static Log instance(){
        return instance;
    }
    private Log(String file){
        try{
            o = new FileOutputStream(file);
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public void logToFile(String log){
        try{
            o.write(log.getBytes());;
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
