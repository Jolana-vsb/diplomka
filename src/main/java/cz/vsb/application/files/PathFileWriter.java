package cz.vsb.application.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PathFileWriter {

    private static BufferedWriter bufferedWriter;

    public static synchronized void write(String str){
        try {
            bufferedWriter.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startWriting(){
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("data/outputPaths2.txt", true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void stopWriting(){
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


