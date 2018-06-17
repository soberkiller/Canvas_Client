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
public class CodeReduction {
    
    private String reducedfiledetail;
    
    public CodeReduction(ArrayList<String> filedetail, String type){
        if(type.equals(".java")){
            JavaCodeReduction jcr = new JavaCodeReduction(filedetail);
            reducedfiledetail = jcr.getReducedFileDetail();
        }
        else if(type.equals(".cpp")){
            CppCodeReduction ccr = new CppCodeReduction(filedetail);
            reducedfiledetail = ccr.getReducedFileDetail();
        }
        else if(type.equals(".py")){
            PythonCodeReduction pcr = new PythonCodeReduction(filedetail);
            reducedfiledetail = pcr.getReducedFileDetail();
        }
    }
    
    public String getReducedFileDetail(){
        return reducedfiledetail;
    }
    
}
