/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author dragon
 */
public class RunPython {
    private File parentfile;
    private String fullname;
    private double pregrade;
    private ArrayList result = new ArrayList();
    private ArrayList runerror = new ArrayList();
    private ArrayList runresult = new ArrayList();
    private double runexit;
    
    
    public RunPython(File file) throws Exception
    {
        parentfile = file.getParentFile();
        fullname = file.getName();
        
        Run();
       
        if(runexit==0){
            result = runresult;
            pregrade=100;
        }  
        else{
            result = runerror;
            pregrade=50;
        }
        
    }
    
    
    public void Run() throws Exception{
        Process p = Runtime.getRuntime().exec("python "+fullname, null, parentfile);
        
        InputStream is1 = p.getInputStream();
        BufferedReader reader1 = new BufferedReader(new InputStreamReader(is1));
        String line1;
        while((line1 = reader1.readLine())!= null)
        {
            runresult.add(line1);
        }
        
        InputStream is2 = p.getErrorStream();
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        String line2;
        while((line2 = reader2.readLine())!= null)
        {
            runerror.add(line2);
        }
        
        p.waitFor();
        is1.close();
        reader1.close();
        is2.close();
        reader2.close();
        p.destroy();
        runexit = p.exitValue();
    }
    
    public ArrayList getResult(){
        return result;
    }
    
    public double getPregrade(){
        return pregrade;
    }
}
