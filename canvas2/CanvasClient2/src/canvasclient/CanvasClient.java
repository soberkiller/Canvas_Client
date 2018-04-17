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
    
    
    public CanvasClient()
    {
        //create instance of main GUI
        GUI myGUI = new GUI();
        
        //fetch all courses from canvas
        //create course object for each course
        //add course to courseList
        
        //currentCourse = courseList(0);
        
        
    }
    
    public static Course getCurrentCourse()
    {
        return currentCourse;
    }
    
    public void setCurrentCourse(Course currentCourse)
    {
        this.currentCourse = currentCourse;
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }
    
    
    public static void main(String[] args) 
    {
        CanvasClient canvasClient = new CanvasClient();
    }
    
}
