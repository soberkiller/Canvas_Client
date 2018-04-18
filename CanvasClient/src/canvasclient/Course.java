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
    
    // the ID of the Course, data from Canvas
    private String courseID;
    
    // ArrayList of Assignment objects, one for each assignment
    private ArrayList<Student> studentsList = new ArrayList<>(50);
    
    // ArrayList of Assignment objects, one for each assignment
    private ArrayList<Assignment> assignmentsList = new ArrayList<>(50);
    
    //to determine when class is, used for assignment scheduling feature we will implement
    private ArrayList<LocalDateTime> classDateTime;
    
    // sets new assignment due dates to be due on next class day by default
    private boolean dueDateIsNextClassPeriod;
    
    // sets new assignment due time to be midnight after next class
    private boolean dueDateIsMidnightAfter;
    
    
    public Course(String courseName, String courseID) 
    {
        
        this.courseName = courseName;
        this.courseID = courseID;
        
        classDateTime = new ArrayList<>(1);
        
        //fetch name and ID of course from Canvas
        //fetch all Assignments from Canvas and add to assignmentsList : new Assignment(assignmentName, assignmentID);
        //fetch all Students from Canvas and add to studentsList
        
        //adding fake assignments to test
        assignmentsList.add(new Assignment("first assignment", "1043295"));
        assignmentsList.add(new Assignment("2 assignment", "1043295"));
        assignmentsList.add(new Assignment("3 assignment", "1043295"));
        assignmentsList.add(new Assignment("4 assignment", "1043295"));
        assignmentsList.add(new Assignment("5 assignment", "1043295"));
        assignmentsList.add(new Assignment("6 assignment", "1043295"));
        assignmentsList.add(new Assignment("7 assignment", "1043295"));
        assignmentsList.add(new Assignment("8 assignment", "1043295"));
        assignmentsList.add(new Assignment("9 assignment", "1043295"));
        assignmentsList.add(new Assignment("10 assignment", "1043295"));
        assignmentsList.add(new Assignment("11 assignment", "1043295"));
        assignmentsList.add(new Assignment("12 assignment", "1043295"));
        assignmentsList.add(new Assignment("13 assignment", "1043295"));
        
        //adding fake students to test
        studentsList.add(new Student("student 1", "133463"));
        studentsList.add(new Student("student 2", "133463"));
        studentsList.add(new Student("student 3", "133463"));
        studentsList.add(new Student("student 4", "133463"));
        studentsList.add(new Student("student 5", "133463"));
        studentsList.add(new Student("student 6", "133463"));
        studentsList.add(new Student("student 7", "133463"));
        studentsList.add(new Student("student 8", "133463"));
        studentsList.add(new Student("student 9", "133463"));
        studentsList.add(new Student("student 10", "133463"));
        studentsList.add(new Student("student 11", "133463"));
        studentsList.add(new Student("student 12", "133463"));
        studentsList.add(new Student("student 13", "133463"));
        
       
    }
    
    
    public String getCourseName()
    {
        return courseName;
    }

    public String getCourseID() 
    {
        return courseID;
    }
    
    public ArrayList<Assignment> getAssignmentsList()
    {
        return assignmentsList;
    }
    
    public ArrayList<Student> getStudentsList()
    {
        return studentsList;
    }
    
    public ArrayList<LocalDateTime> getClassDateTime() 
    {
        return classDateTime;
    }

    public void setClassDateTime(ArrayList<LocalDateTime> classDateTime) 
    {
        this.classDateTime = classDateTime;
    }

    public boolean isDueDateIsMidnightAfter() 
    {
        return dueDateIsMidnightAfter;
    }

    public boolean isDueDateIsNextClassPeriod() 
    {
        return dueDateIsNextClassPeriod;
    }

    public void setDueDateIsMidnightAfter(boolean dueDateIsMidnightAfter) 
    {
        this.dueDateIsMidnightAfter = dueDateIsMidnightAfter;
    }

    public void setDueDateIsNextClassPeriod(boolean dueDateIsNextClassPeriod) 
    {
        this.dueDateIsNextClassPeriod = dueDateIsNextClassPeriod;
    }
    
    
    
}
