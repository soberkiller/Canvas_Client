/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.canvasgroup.canvasclient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MattSorrentino
 */

public class CanvasClient {

    private static Course currentCourse;

    private ArrayList<Course> courseList = new ArrayList<Course>();
    private List<String> fields = new ArrayList<String>();
    public ConnectionPool connection;
    private String responses = "";
    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String POST = "POST";

    public CanvasClient(Course currentCourse) {

        //fetch all courses from canvas
        //create course object for each course
        //add course to courseList

        //currentCourse = courseList(0);
        //make temporary course objects for testing purposes.
        fields.add("courses");
        connection = new ConnectionPool(fields, 0.1);
        connection.setMethod(GET);
        getCourses(courseList);
        fields.clear();

        //set currentCourse to be the Course object passed in 
        this.currentCourse = currentCourse;

        //if null, set currentCourse to be the first course loaded in through the API
        if (this.currentCourse == null) {
            currentCourse = courseList.get(0);
        }


        //create instance of main GUI
        GUI myGUI = new GUI(currentCourse, courseList);


    }

    public static void main(String[] args) {
        new CanvasClient(null);
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    public static Course getCurrentCourse() {
        return currentCourse;
    }

    public void getCourses(ArrayList<Course> courseList) {
        if(responses != "")
            responses = "";
        responses = connection.buildConnection();
        String[] rawResp = responses.split(",");
        if(rawResp != null) {
            List<String> strID = new ArrayList<>();
            List<String> strName = new ArrayList<>();
            for (String s : rawResp) {
                if (s.startsWith("{"))
                    s = s.substring(1);
                if (s.charAt(s.length() - 1) == '}')
                    s = s.substring(0, s.length() - 1);
                if (s.startsWith("[{"))
                    s = s.substring(2);
                if (s.charAt(s.length() - 2) == ']')
                    s = s.substring(0, s.length() - 3);

                // get useful information from responses
                if (s.startsWith("\"id\"")) {
                    strID.add(s.substring(5));
                }
                if (s.startsWith("\"name\"")) {
                    strName.add(s.substring(8, s.length() - 1));
                }
            }
            for(int i = 0; i < strID.size(); i++) {
                courseList.add(new Course(strName.get(i), strID.get(i)));
            }
        }
    }

}
