/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Xiao
 */
public class Submission2 {
    
    //SUBMISSION DATA IS TAKEN FROM CANVAS
    

    // Sudent object of student who made the submission
    
    // time and date of submission
    private LocalDateTime submissionTime;
    
    // comments the student added to the submission
    private String comments;
    
    private double grade;
    
    // ArrayList of files the student submitted
    // We'd like to be able to process submissions with multiple files
    // WE MUST VERIFY THAT THE "FILE" CLASS WILL WORK FOR OUR SYSTEM
    private ArrayList<File> attachedFiles;
    
    
    public Submission2()
    {
        attachedFiles = new ArrayList<>();
        
        String path = "/Users/dragon/CanvasFiles/Java/Assignment2/Student3/Submission1/";
//        File submissionfolder = new File(path);
//        String[] filenamelist = submissionfolder.list();
//
//        for(String x:filenamelist){
            attachedFiles.add(new File(path));
//        }
        
       
    }
    
          
    public LocalDateTime getSubmissionTime()
    {
        return submissionTime;
    }
    
    public String getComments()
    {
        return comments;
    }
    
    public void setComments(String comments)
    {
        this.comments=comments;
    }
    
    public double getGrade()
    {
        return grade;
    }
    
    public void setGrade(double grade)
    {
        this.grade=grade;
    }
    
    public ArrayList<File> getattachedFiles()
    {
        return attachedFiles;
    }
    
}