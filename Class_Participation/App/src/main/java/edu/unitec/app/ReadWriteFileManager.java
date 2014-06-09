package edu.unitec.app;

import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class reads a file with a list of students. File must follow this scheme: Account Number, Name, Major.
 * The file read must be a CSV file.
 */
public class ReadWriteFileManager {

    public List<Student> readFromFile(Context context,String sourceFileName){
        List<Student> StudentList = new ArrayList<Student>();
        FileReader fr = null;
        BufferedReader br = null;
        File file = null;
        String splitBy = ",";
        try{
            file = new File (sourceFileName);
            fr = new FileReader (file);
            br = new BufferedReader(fr);
            String line;
            while( ( line = br.readLine() )!= null ){
                String [] std = line.split(splitBy);
                StudentList.add(new Student(Integer.parseInt(std[0].trim()),std[1].trim(),std[2].trim()));
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if( null != fr ) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return StudentList;
    }
}
