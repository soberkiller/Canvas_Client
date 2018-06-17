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
public class JavaCodeReduction {
    
    private String reducedfiledetail;
    
    public JavaCodeReduction(ArrayList<String> filedetail){
        for(int i=0;i<filedetail.size();i++){
            String linedetail = filedetail.get(i);
            int indexOf1 = linedetail.indexOf("package");
            int indexOf2 = linedetail.indexOf("import");
            int indexOf3 = linedetail.indexOf("//");
            if(indexOf1 != -1||indexOf2 != -1){
                filedetail.set(i, "");
            }
            else{
                if(indexOf3 != -1){
                    filedetail.set(i,linedetail.substring(0, indexOf3));
                }
            } 
        } 
        
        String s1 = "";
        for(int i=0;i<filedetail.size();i++){
            s1 = s1 + filedetail.get(i);
        }
        String s2 = s1.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
        String s3 = s2.replaceAll("[\\pP\\p{Punct}]", "");
        String s4 = s3.replaceAll("abstract|assert|boolean|break|byte|case|catch|char|class|const", "");
        String s5 = s4.replaceAll("continue|default|do|double|else|enum|extends|final|finally|float", "");
        String s6 = s5.replaceAll("for|goto|if|implements|import|instanceof|int|interface|long|native", "");
        String s7 = s6.replaceAll("new|package|private|public|return|short|static|strictfp|super|switch", "");
        String s8 = s7.replaceAll("synchronized|this|throw|throws|transient|try|void|volatile|while", "");
        String s9 = s8.replaceAll(" ", "");
        reducedfiledetail = s9;
    }
    
    public String getReducedFileDetail(){
        return reducedfiledetail;
    }
    
}
