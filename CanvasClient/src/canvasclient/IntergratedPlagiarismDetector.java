/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.util.ArrayList;
import java.io.File;
/**
 *
 * @author dragon
 */
public class IntergratedPlagiarismDetector {
    
    private ArrayList<File> sametypefile = new ArrayList<>();
    private ArrayList<File> suspectedfile = new ArrayList<>();
    private double similaritythreshold = 0.3;
    
    public IntergratedPlagiarismDetector(File currentfile, ArrayList<File> othersfile, String type){
        for(File f1:othersfile){
            String filename = f1.getName();
            if(filename.endsWith(type)){
                sametypefile.add(f1);
            }
        }
        
        //compute similarity

        ReadFile rf1 = new ReadFile(currentfile);
        ArrayList<String> filedetail1 = rf1.getFileDetail();
        
        String plaintext1 = "";
            for(int i=0;i<filedetail1.size();i++){
                plaintext1=plaintext1+filedetail1.get(i);
            }
        
        CodeReduction cr1 = new CodeReduction(filedetail1,type);
        String reducedcode1 = cr1.getReducedFileDetail();
        
        for(File f2:sametypefile){          
            ReadFile rf2 = new ReadFile(f2);
            ArrayList<String> filedetail2 = rf2.getFileDetail();
            
            //plaintext
            String plaintext2 = "";
            for(int i=0;i<filedetail2.size();i++){
                plaintext2=plaintext2+filedetail2.get(i);
            }
            
            //reduced code
            CodeReduction cr2 = new CodeReduction(filedetail2,type);
            String reducedcode2 = cr2.getReducedFileDetail();
            
            //compute similarity by EDA on plain text
            double simbyEDAOPT = new EditDistanceAlgorithm().similarity(plaintext1, plaintext2);
            
            //compute similarity by EDA on reduced code
            double simbyEDARDC = new EditDistanceAlgorithm().similarity(reducedcode1, reducedcode2);
            //compute similarity by LCS on plain text
                
            //compute similarity by LCS on reduced code
                
            //intergrate the similarity of each part
            double similarityintegrated = simbyEDAOPT*0.1+simbyEDARDC*0.9;
                
            if(similarityintegrated>similaritythreshold){
                suspectedfile.add(f2);
            }
        }
    }
    
    
    
    public ArrayList<File> getSuspectedFile(){
            return suspectedfile;
        }
    
}
