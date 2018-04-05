/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author MattSorrentino
 */
public class Course {
    
    // the name of the Course, data from Canvas
    private String courseName;
    
    // ArrayList of Assignment objects, one for each assignment
    private ArrayList<Assignment> assignmentsList = new ArrayList<>(50);
    
    //to determine when class is, used for assignment scheduling feature we will implement
    private ArrayList<LocalDateTime> classDateTime;
    
    // sets new assignment due dates to be due on next class day by default
    private boolean dueDateIsNextClassPeriod;
    
    // sets new assignment due time to be midnight after next class
    private boolean dueDateIsMidnightAfter;
    
    
    public Course() 
    {
        
        //fetch name of course from Canvas
        //fetch all Assignments from Canvas
        
        classDateTime = new ArrayList<>(1);
        
    }
    
    
    public String getCourseName()
    {
        return courseName;
    }

    public ArrayList<Assignment> getAssignmentsList()
    {
        return assignmentsList;
    }
    
    
}
