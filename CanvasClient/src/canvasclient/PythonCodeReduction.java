/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.util.ArrayList;

/**
 *
 * @author dragon
 */
public class PythonCodeReduction {
    
    private String reducedfiledetail;
    
    public PythonCodeReduction(ArrayList<String> filedetail){
        for(int i=0;i<filedetail.size();i++){
            String linedetail = filedetail.get(i);
            int indexOfa1 = linedetail.indexOf("\'\'\'");
            int indexOfa2 = linedetail.indexOf("\'\'\'",indexOfa1+1);
            int indexOfb1 = linedetail.indexOf("\"\"\"");
            int indexOfb2 = linedetail.indexOf("\"\"\"",indexOfb1+1);
            if(indexOfa1 != -1){
                if(indexOfa2 != -1){
                    filedetail.set(i, "");
                }
                else{
                    filedetail.set(i, linedetail.substring(0,indexOfa1));
                    filedetail.set(i+1, "\'\'\'"+filedetail.get(i+1));
                }
            }
            if(indexOfb1 != -1){
                if(indexOfb2 != -1){
                    filedetail.set(i, "");
                }
                else{
                    filedetail.set(i, linedetail.substring(0,indexOfb1));
                    filedetail.set(i+1, "\"\"\""+filedetail.get(i+1));
                }
            }
        } 

        for(int i=0;i<filedetail.size();i++){
            String linedetail = filedetail.get(i);
            int indexOf1 = linedetail.indexOf("import");
            int indexOf2 = linedetail.indexOf("#");
            if(indexOf1 != -1){
                filedetail.set(i, "");
            }
            else{
                if(indexOf2 != -1){
                    filedetail.set(i,linedetail.substring(0, indexOf2));
                }
            } 
        } 
        
        String s1 = "";
        for(int i=0;i<filedetail.size();i++){
            s1 = s1 + filedetail.get(i);
        }
        String s2 = s1.replaceAll("[\\pP\\p{Punct}]", "");
        String s3 = s2.replaceAll("False|None|True|and|as|assert|break|class|continue", "");
        String s4 = s3.replaceAll("def|del|elif|else|except|finally|for|from|global", "");
        String s5 = s4.replaceAll("if|import|in|is|lambda|nonlocal|not|or|pass|raise", "");
        String s6 = s5.replaceAll("return|try|while|with|yield", "");
        String s7 = s6.replaceAll(" ", "");
        reducedfiledetail = s7;
    }
    
    public String getReducedFileDetail(){
        return reducedfiledetail;
    }
    
}
