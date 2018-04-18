/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.util.ArrayList;

/**
 *
 * @author MattSorrentino
 */

public class CanvasClient {

    private static Course currentCourse;
    
    private ArrayList<Course> courseList = new ArrayList<Course>(5);
    
    
    public CanvasClient(Course currentCourse)
    {
        
        //fetch all courses from canvas
        //create course object for each course
        //add course to courseList
        
        //currentCourse = courseList(0);
        //make temporary course objects for testing purposes.
        courseList.add(new Course("Java", "01234"));
        courseList.add(new Course("C++", "12345"));
        courseList.add(new Course("DS", "23456"));
        courseList.add(new Course("Software Eng", "34567"));
        
        //set currentCourse to be the Course object passed in 
        this.currentCourse = currentCourse;
        
        //if null, set currentCourse to be the first course loaded in through the API
        if(this.currentCourse == null)
        {
            currentCourse = courseList.get(0);
        }


        //create instance of main GUI
        GUI myGUI = new GUI(currentCourse, courseList);
        
        
        
        
    }
    
    public static Course getCurrentCourse()
    {
        return currentCourse;
    }

    public ArrayList<Course> getCourseList() 
    {
        return courseList;
    }
    

    public static void main(String[] args) 
    {
        new CanvasClient(null);
    }
    
}
