/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import canvasclient.ReadFile;
import java.io.File;
/**
 *
 * @author Xiao
 */
public class SimilarityByEDAOnPlainText {
    private double similarity;
    
    public SimilarityByEDAOnPlainText(File file1,File file2){
        String str1 = "";
        ReadFile rf1 = new ReadFile(file1);
        for(int i=0;i<rf1.getFileDetail().size();i++){
            str1=str1+rf1.getFileDetail().get(i);
        }
                    
        String str2 = "";
        ReadFile rf2 = new ReadFile(file2);
        for(int i=0;i<rf2.getFileDetail().size();i++){
            str2=str2+rf2.getFileDetail().get(i);
        }
         
        similarity= new EditDistanceAlgorithm().similarity(str1, str2);
        
    }
    
    public double getSimilarity(){
        return similarity;
    }
}
