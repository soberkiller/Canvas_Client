/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author dragon
 */
public class SubmissionOfOtherStudents {
    
    private ArrayList<File> allfilelist = new ArrayList<>();
    
    public SubmissionOfOtherStudents(File file){
        
        String path = file.getParentFile().getParent();
        String currentstudent = file.getParentFile().getName();
        File assignmentfolder = file.getParentFile().getParentFile(); 
        String[] studentlist = assignmentfolder.list();

        for(String x:studentlist){
            if(!(new File(path+"/"+x)).isDirectory()){
            }
            //submissions of currentstudent should not be included 
            else if(x.equals(currentstudent)){
            }
            else{
                File studentfolder = new File(path+"/"+x);
                String[] attachedfilelist = studentfolder.list();
                for(String y:attachedfilelist){
                    if(y.startsWith(".")){ 
                    }
                    else{
                        allfilelist.add(new File(path+"/"+x+"/"+y));
                       }
                }
            }
        }
    }
    
    public ArrayList<File> getSubmissionOfOtherStudents(){
        return allfilelist;
    }
    
}
