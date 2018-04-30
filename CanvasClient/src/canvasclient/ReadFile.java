/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Xiao
 */
public class ReadFile {
    
    private ArrayList<String> filedetail = new ArrayList<>();
    
    public ReadFile(File file){
        BufferedReader reader = null;
                        try {
                            reader = new BufferedReader(new FileReader(file));
                            String tempString = null;
                            while ((tempString = reader.readLine()) != null) {
                                filedetail.add(tempString);
                            }
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                }
                            }
                        }
    }
    
    public ArrayList<String> getFileDetail(){
        return filedetail;
    }
    
}
