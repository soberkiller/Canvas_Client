/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MattSorrentino
 */

public class CanvasClient extends PublicResouce {
    public ConnectionPool connection;
    private String responses = "";
    public CanvasClient(Course currentCourse) throws UnsupportedEncodingException, ParseException {

        //fetch all courses from canvas
        //create course object for each course
        //add course to courseList

        //currentCourse = courseList(0);
        //make temporary course objects for testing purposes.

        //set currentCourse to be the Course object passed in 
        this.currentCourse = currentCourse;

        //if null, set currentCourse to be the first course loaded in through the API
        if (this.currentCourse == null) {

            if(!fields.isEmpty())
                fields.clear();
            fields.add("courses?per_page=100");

            connection = new ConnectionPool(fields, 0.1, new String(decoder.decode(getOAUTH2()), "UTF-8"));
            connection.setMethod(GET);
//        getCourses(courseList);
            //List Not Student role courses
            courseList = connection.getNotStudentCourses();
            fields.clear();
            if(courseList.size() > 0)
                currentCourse = courseList.get(0);
        }


        //create instance of main GUI
        new GUI(currentCourse, courseList);


    }

    public static void main(String[] args) throws IOException, ParseException {
        File tokenFile = new File(FILENAME);
        
        if(!tokenFile.exists()) 
        {
            new TokenPrompt(tokenFile); 
        }
        else
        {
            tokenFile.setReadable(true);
            tokenFile.setWritable(true);
            new CanvasClient(null);
        }
    }

    public ArrayList<Course> getCourseList() {
        return courseList;
    }

    static Course getCurrentCourse() {
        return currentCourse;
    }

    public String getOAUTH2() {
        File tFile = new File(FILENAME);
        StringBuffer content = new StringBuffer();
        // the length of stream read from file is larger than the content of that file, so have to deal with it
        Course.getFromFile(tFile, content);

        return content.toString();
    }

    public void getCourses(ArrayList<Course> courseList) throws UnsupportedEncodingException {
        if(responses != "")
            responses = "";
        responses = connection.buildConnection();

        if (responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
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
//                    System.out.println(s);
                }
                for (int i = 0; i < strID.size(); i++) {
                    courseList.add(new Course(strName.get(i), strID.get(i)));
                }
            }
        } else {
            courseList.add(new Course("Unavailable", "Unavailable"));
        }
    }

}
