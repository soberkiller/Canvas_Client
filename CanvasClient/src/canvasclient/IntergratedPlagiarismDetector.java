/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;
//import canvasclient.SimilarityByEDAOnPlainText;
import java.util.ArrayList;
import java.io.File;

/**
 *
 * @author Xiao
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

        for(File f2:sametypefile){
            //compute similarity by EDA on plain text
            SimilarityByEDAOnPlainText sim1 = new SimilarityByEDAOnPlainText(currentfile,f2);
            double simbyEDAOPT = sim1.getSimilarity();
            
            //compute similarity by EDA on reduced code

            //compute similarity by LCS on plain text
                
            //compute similarity by LCS on reduced code
                
            //intergrate the similarity of each part
            double similarityintegrated = simbyEDAOPT/2;
                
            if(similarityintegrated>similaritythreshold){
                suspectedfile.add(f2);
            }
        }
    }
    
    
    
    public ArrayList<File> getSuspectedFile(){
            return suspectedfile;
        }
    
}
