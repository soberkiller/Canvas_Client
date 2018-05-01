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
        String s2 = s1.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
        String s3 = s2.replaceAll("[\\pP\\p{Punct}]", "");
        String s4 = s3.replaceAll("asm|auto|bool|break|case|catch|char|class|const|const_cast|continue|default|delete", "");
        String s5 = s4.replaceAll("do|double|dynamic_cast|else|enum|explicit|export|extern|false|float|for|friend|goto", "");
        String s6 = s5.replaceAll("if|inline|int|long|mutable|namespace|new|operator|private|protected|public|register", "");
        String s7 = s6.replaceAll("reinterpret_cast|return|short|signed|sizeof|static|static_cast|struct|switch|template", "");
        String s8 = s7.replaceAll("this|throw|true|try|typedef|typeid|typename|union|unsigned|using|virtual|void|volatile", "");
        String s9 = s8.replaceAll("wchar_t|while|alignas|alignof|char16_t|char32_t|constexpr|decltype|noexcept|nullptr|static_assert|thread_local", "");
        String s10 = s9.replaceAll(" ", "");
        reducedfiledetail = s10;
    }
    
    public String getReducedFileDetail(){
        return reducedfiledetail;
    }
    
}
