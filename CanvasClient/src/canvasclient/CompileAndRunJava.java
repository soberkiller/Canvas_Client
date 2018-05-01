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
public class CompileAndRunJava {
    private File parentfile;
    private String fullname;
    private String name;
    private double pregrade;
    private ArrayList result = new ArrayList();
    private ArrayList compileerror = new ArrayList();
    private ArrayList runerror = new ArrayList();
    private ArrayList runresult = new ArrayList();
    private double compileexit;
    private double runexit;
    
    
    public CompileAndRunJava(File file) throws Exception
    {
        parentfile = file.getParentFile();
        fullname = file.getName();
        name = fullname.substring(0, fullname.length()-5);
        
        Compile();
       
        if(compileexit==0){
            Run();
            if(runexit==0){
                result = runresult;
                pregrade = 100;
            }
            else{
                result = runerror;
                pregrade = 50;
            }
        }
        else{
            result = compileerror;
            pregrade=25;
        }
        
    }
    
    
    public void Compile() throws Exception{
        Process p = Runtime.getRuntime().exec("javac "+fullname, null, parentfile);
        InputStream is = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while((line = reader.readLine())!= null)
        {
            compileerror.add(line);
        }
        p.waitFor();
        is.close();
        reader.close();
        p.destroy();
        compileexit = p.exitValue();
    }
    
    public void Run() throws Exception{
        Process p = Runtime.getRuntime().exec("java "+name, null, parentfile);
        
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
