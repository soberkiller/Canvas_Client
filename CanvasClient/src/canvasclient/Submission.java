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
 * @author MattSorrentino
 */

public class Submission {

    //SUBMISSION DATA IS TAKEN FROM CANVAS


    // Sudent object of student who made the submission
    private Student student;

    // time and date of submission
    private String submissionTime;

    //the grade given for the submission
    private double submissionGrade;

    // comments the student added to the submission
    private String studentComments;
    
    // comments the grader added to the submission
    private String graderComments;

    // ArrayList of files the student submitted
    // We'd like to be able to process submissions with multiple files
    // WE MUST VERIFY THAT THE "FILE" CLASS WILL WORK FOR OUR SYSTEM
    private ArrayList<File> attachedFiles;



    public Submission(Student student) {
        this.student = student;
        attachedFiles = new ArrayList<>(1);
    }

    public Student getStudent() {
        return student;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }

    public String getGraderComments() {
        return graderComments;
    }

    public void setGraderComments(String graderComments) {
        this.graderComments = graderComments;
    }

    public String getStudentComments() {
        return studentComments;
    }

    public void setStudentComments(String studentComments) {
        this.studentComments = studentComments;
    }
    
    

    public ArrayList<File> getAttachedFiles() {
        return attachedFiles;
    }

 
    public double getSubmissionGrade() {
        return submissionGrade;
    }

    public void setSubmissionGrade(double submissionGrade) {
        this.submissionGrade = submissionGrade;
    }


}
