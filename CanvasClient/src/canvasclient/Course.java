/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author MattSorrentino
 */
public class Course extends PublicResouce {

    // the name of the Course, data from Canvas
    private String courseName;

    // the ID of the Course, data from Canvas
    private String courseID;

    // ArrayList of Assignment objects, one for each assignment
    private ArrayList<Student> studentsList = new ArrayList<>(50);

    // ArrayList of Assignment objects, one for each assignment
    private List<Assignment> assignmentsList = new ArrayList<>();

    //to determine when class is, used for assignment scheduling feature we will implement
    private ArrayList<String> classDateTime;

    // sets new assignment due dates to be due on next class day by default
    private boolean dueDateIsNextClassPeriod;

    // sets new assignment due time to be midnight after next class
    private boolean dueDateIsMidnightAfter;

    // set up connection
    public ConnectionPool connection;
    private String responses = "";

    // get token from file

    public Course(String courseName, String courseID) throws UnsupportedEncodingException {

        this.courseName = courseName;
        this.courseID = courseID;

        classDateTime = new ArrayList<>(1);

        //fetch name and ID of course from Canvas
        //fetch all Assignments from Canvas and add to assignmentsList : new Assignment(assignmentName, assignmentID);

        if(!fields.isEmpty())
            fields.clear();

        fields.add("courses");
        fields.add(courseID);
        fields.add("assignments");
        connection = new ConnectionPool(fields, 0.1, new String(decoder.decode(getOAUTH2()), "UTF-8"));
        connection.setMethod(GET);
        getAssignments(assignmentsList);
        fields.clear();

        //adding fake assignments to test
//        assignmentsList.add(new Assignment("first assignment", "1043295"));
//        assignmentsList.add(new Assignment("2 assignment", "1043295"));
//        assignmentsList.add(new Assignment("3 assignment", "1043295"));
//        assignmentsList.add(new Assignment("4 assignment", "1043295"));
//        assignmentsList.add(new Assignment("5 assignment", "1043295"));
//        assignmentsList.add(new Assignment("6 assignment", "1043295"));
//        assignmentsList.add(new Assignment("7 assignment", "1043295"));
//        assignmentsList.add(new Assignment("8 assignment", "1043295"));
//        assignmentsList.add(new Assignment("9 assignment", "1043295"));
//        assignmentsList.add(new Assignment("10 assignment", "1043295"));
//        assignmentsList.add(new Assignment("11 assignment", "1043295"));
//        assignmentsList.add(new Assignment("12 assignment", "1043295"));
//        assignmentsList.add(new Assignment("13 assignment", "1043295"));

        //fetch all Students from Canvas and add to studentsList
        
        //adding fake students to test
        studentsList.add(new Student("student 1", "133463"));
        studentsList.get(0).setStudentEmail("emailme@stevens.edu");
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


    public String getCourseName() {
        return courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public List<Assignment> getAssignmentsList() {
        return assignmentsList;
    }

    public ArrayList<Student> getStudentsList() {
        return studentsList;
    }

    public ArrayList<String> getClassDateTime() {
        return classDateTime;
    }

    public void setClassDateTime(ArrayList<String> classDateTime) {
        this.classDateTime = classDateTime;
    }

    public boolean isDueDateIsMidnightAfter() {
        return dueDateIsMidnightAfter;
    }

    public void setDueDateIsMidnightAfter(boolean dueDateIsMidnightAfter) {
        this.dueDateIsMidnightAfter = dueDateIsMidnightAfter;
    }

    public boolean isDueDateIsNextClassPeriod() {
        return dueDateIsNextClassPeriod;
    }

    public void setDueDateIsNextClassPeriod(boolean dueDateIsNextClassPeriod) {
        this.dueDateIsNextClassPeriod = dueDateIsNextClassPeriod;
    }

    public void getAssignments(List<Assignment> assignmentsList) {
        if(responses != "")
            responses = "";
        responses = connection.buildConnection();
        if(responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
                List<String> strID = new ArrayList<>();
                List<String> strName = new ArrayList<>();
                List<String> openDate = new ArrayList<>();
                List<String> closeDate = new ArrayList<>();
                List<String> dueDate = new ArrayList<>();
                List<String> descrip = new ArrayList<>();
                String des = "";

                for (String s : rawResp) {
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.length() > 1 && s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);
                    if (!s.startsWith("\""))
                        if (!des.isEmpty())
                            des += s;

                    // get useful information from responses
                    if (s.startsWith("\"id\"")) {
                        strID.add(s.substring(5));
                    }
                    if (s.startsWith("\"name\"")) {
                        strName.add(s.substring(8, s.length() - 1));
                    }
                    if (s.startsWith("\"created_at\"")) {
                        openDate.add((s.substring(14, s.length() - 1)));
                    }
                    if (s.startsWith("\"due_at\"")) {
                        if (!des.isEmpty()) {
                            descrip.add(des);
                            des = "";
                        }
                        if (s.substring(9).equals("null")) {
                            dueDate.add(s.substring(9));
                        } else {
                            dueDate.add(s.substring(10, s.length() - 1));
                        }
                    }
                    if(s.startsWith("\"lock_info\"")) {
                        closeDate.remove(closeDate.size()-1);
                        closeDate.remove(closeDate.size()-1);

                    }
                    if (s.startsWith("\"lock_at\"")) {
                        if (s.substring(10).equals("null")) {
                            closeDate.add(s.substring(10));
                            closeDate.add(s.substring(10));
                        } else {
                            closeDate.add(s.substring(11, s.length() - 1));
                            closeDate.add(s.substring(11, s.length() - 1));
                        }
                    }
                    if (s.startsWith("\"description\"")) {
                        if (!des.isEmpty()) {
                            des = "";
                            des += s.substring(14);
                        } else {
                            des += s.substring(14);
                        }

                    }
//                System.out.println(s);
                }

                for (int i = 0; i < strID.size(); i++) {
                    assignmentsList.add(new Assignment(strName.get(i), strID.get(i)));
                    assignmentsList.get(i).setAssignmentDescription(descrip.get(i));
                    assignmentsList.get(i).setCloseDate(closeDate.get(i * 2));
                    assignmentsList.get(i).setDueDate(dueDate.get(i));
                    assignmentsList.get(i).setOpenDate(openDate.get(i));
                }
            }
        } else {
            assignmentsList.add(new Assignment("Unavailable", "Unavailable"));
            assignmentsList.get(0).setAssignmentDescription("Unavailable");
            assignmentsList.get(0).setCloseDate("Unavailable");
            assignmentsList.get(0).setDueDate("Unavailable");
            assignmentsList.get(0).setOpenDate("Unavailable");
        }
    }

    public String getOAUTH2() {
        File tFile = new File(FILENAME);
        StringBuffer content = new StringBuffer();
        // the length of stream read from file is larger than the content of that file, so have to deal with it
        getFromFile(tFile, content);

        return content.toString();
    }

    static void getFromFile(File tfile, StringBuffer content) {
        try {
            byte[] data = new byte[1024];
            FileInputStream fis = new FileInputStream(tfile);
            int n = fis.read(data);
            if(n != -1) {
                content.append(new String(data, 0, n));
            }
            fis.close();

        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.getMessage();
        }
    }

}
