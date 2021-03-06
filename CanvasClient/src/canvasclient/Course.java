/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * @author MattSorrentino
 */
public class Course extends PublicResouce {

    // the name of the Course, data from Canvas
    private String courseName;

    // the ID of the Course, data from Canvas
    private String courseID;

    // the ID of the Course, data from Canvas
    private Date courseStartDate;
    
    // ArrayList of Assignment objects, one for each assignment
    private List<Student> studentsList = new ArrayList<>();

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
        fields.add("?include[]=all_dates");
        //list at most 100 assignments for course
        fields.add("assignments?&per_page=100");

        connection = new ConnectionPool(fields, 0.1, new String(decoder.decode(getOAUTH2()), "UTF-8"));
        connection.setMethod(GET);
        getAssignments(assignmentsList);
        fields.clear();

        // making directories for each assignment in current course, name them by their ID
        for(Assignment a : assignmentsList) {
            File dir = new File("./" + this.courseID + "/" + a.getAssignmentID());
            if(!dir.exists())
                dir.mkdir();
        }

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
        fields.add("courses");
        fields.add(courseID);
        fields.add("students");
        connection = new ConnectionPool(fields, 0.1, new String(decoder.decode(getOAUTH2()), "UTF-8"));
        connection.setMethod(GET);
        getStudentsinfo(studentsList);
        fields.clear();

        //adding fake students to test
//        studentsList.add(new Student("student 1", "133463"));
//        studentsList.get(0).setStudentEmail("emailme@stevens.edu");
//        studentsList.add(new Student("student 2", "133463"));
//        studentsList.add(new Student("student 3", "133463"));
//        studentsList.add(new Student("student 4", "133463"));
//        studentsList.add(new Student("student 5", "133463"));
//        studentsList.add(new Student("student 6", "133463"));
//        studentsList.add(new Student("student 7", "133463"));
//        studentsList.add(new Student("student 8", "133463"));
//        studentsList.add(new Student("student 9", "133463"));
//        studentsList.add(new Student("student 10", "133463"));
//        studentsList.add(new Student("student 11", "133463"));
//        studentsList.add(new Student("student 12", "133463"));
//        studentsList.add(new Student("student 13", "133463"));


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

    public List<Student> getStudentsList() {
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
    
    
    public Date getCourseStartDate() {
		return courseStartDate;
	}

    /**
     * Sort the course list, newest first
     * @author yifang
     *
     */
    @SuppressWarnings("deprecation")
    //Sort by start date, newest first
    public static class Comparators {
        public static Comparator<Course> DateSort = new Comparator<Course>() {           
			public int compare(Course c2, Course c1) {           	
                return c1.getCourseStartDate().getYear() > c2.getCourseStartDate().getYear() ? 1 
                        : c1.getCourseStartDate().getYear() < c2.getCourseStartDate().getYear() ? -1
                                : c1.getCourseStartDate().getMonth() > c2.getCourseStartDate().getMonth() ? 1
                                : c1.getCourseStartDate().getMonth() < c2.getCourseStartDate().getMonth() ? -1
                                : c1.getCourseStartDate().getDay() > c2.getCourseStartDate().getDay() ? 1
                                : c1.getCourseStartDate().getDay() < c2.getCourseStartDate().getDay() ?-1
                                : 0;          		
            }
        };
    }
    

	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
    
    
    public void getStudentsinfo(List<Student> studentsList) {
        if(responses != "")
            responses = "";
        responses = connection.buildConnection();
        if(responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
                List<String> str_sis_ID = new ArrayList<>();
                List<String> strID = new ArrayList<>();
                List<String> strName = new ArrayList<>();
                List<String> strMail = new ArrayList<>();

                for (String s : rawResp) {
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.length() > 1 && s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);

                    // get useful information from responses
                    if (s.startsWith("\"id\"")) {           // user_id
                        strID.add(s.substring(5));
//                        System.out.println(s.substring(5));
                    }
                    if (s.startsWith("\"sis_user_id\"")) {
                        str_sis_ID.add(s.substring(15, s.length() - 1));
                    }
                    if (s.startsWith("\"name\"")) {
                        strName.add(s.substring(8, s.length() - 1));
                    }
                    if (s.startsWith("\"login_id\"")) {
                        strMail.add(s.substring(12, s.length() - 1));
                    }
                }

//  if current user does not have access that is higher than teacher, current user will not get student id and student Email
                if(str_sis_ID.size() == 0) {
                    for (int i = 0; i < strName.size(); i++) {
                        str_sis_ID.add("Unavailable");
                        strMail.add("Unavailable");
                    }
                }
                for (int i = 0; i < strName.size(); i++) {
                    studentsList.add(new Student(strName.get(i), str_sis_ID.get(i), strMail.get(i), strID.get(i)));
                    // for get student's information by user_id
                    if(!id_user_info.containsKey(strID.get(i)))
                        id_user_info.put(strID.get(i), studentsList.get(i));
                }

            } else {
                studentsList.add(new Student("Unavailable", "Unavailable", "Unavailable", "Unavailable"));
            }
        }
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
                List<String> points = new ArrayList<>();
                List<String> subType = new ArrayList<>();
                String des = "";
                String allowExtention = "";

                for (String s : rawResp) {
//                	System.out.println(s);
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.length() > 1 && s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);
                    if (!s.startsWith("\"")) {
                        if (!des.isEmpty())
                            des += s;
                    }
                    /**
                     * @author yifang
                     * It is simpler than original one. Roll back if get problem
                     */
 //                   if (s.substring(0,1).equals("\"") && !s.startsWith("\"published\"")) {
//                        if (!allowExtention.isEmpty())
//                            allowExtention = allowExtention + "," + s.substring(1, s.length() - 1);
 //                   }
                    if (!allowExtention.isEmpty() &&!allowExtention.endsWith("\"")) {
                    	allowExtention=allowExtention+","+s.substring(1, s.length() - 1);
                    }

                    // get useful information from responses
                    if (s.startsWith("\"id\"") && !s.substring(5, 6).equals("\"")) {
                        strID.add(s.substring(5));
                    }
                    if (s.startsWith("\"name\"")) {
                        strName.add(s.substring(8, s.length() - 1));
                    }
                    if (s.startsWith("\"unlock_at\"")) {
                        if (s.substring(12).equals("null")) {
                            openDate.add(s.substring(12));
                        } else {
                        openDate.add((s.substring(13, s.length() - 11)));
                        }
                    }
                    if (s.startsWith("\"description\"")) {  
                    	if (!des.isEmpty()) {
                            //des = "";
                            des = s.substring(14);
                        } else {                   	
                            des += s.substring(14);
                        }                   	
                    }
                    if (s.startsWith("\"due_at\"")) {
                        if (!des.isEmpty()) {
                            /**@author yifang
                             * Remove the end code
                             */
                        	des= des.replace("<script src=\\\"https://instructure-uploads.s3.amazonaws.com/account_10300000000000001/attachments/2602729/canvas_ga.js\\\"></script>", "");                              
                            descrip.add(des);
                            des = "";
                        }
                        if (s.substring(9).equals("null")) {
                            dueDate.add(s.substring(9));
                        } else {
                            dueDate.add(s.substring(10, s.length() - 11));
                        }
                    }
                    if(s.startsWith("\"lock_info\"") || s.startsWith("\"discussion_topic\"")) {
                        closeDate.remove(closeDate.size()-1);
                    }
                    if (s.startsWith("\"lock_at\"")) {
                        if (s.substring(10).equals("null")) {
                            closeDate.add(s.substring(10));
                        } else {
                            closeDate.add(s.substring(11, s.length() - 11));
                        }
                    }
                    /**
                     * @author yifang
                     * It is simpler than original one. Roll back if get problem
                     */
                    if (s.startsWith("\"allowed_extensions\"")) {
//                        if (!allowExtention.isEmpty()) {
//                            allowExtention = "";
//                                allowExtention += s.substring(23, s.length() - 1);
//                        } else {
//                                allowExtention += s.substring(23, s.length() - 1);
//                        }
//                        System.out.println("bb"+allowExtention);
                    	allowExtention=s.substring(23,s.length()-1);
                    }
                    if (s.startsWith("\"points_possible\"")) {
                    	points.add(s.substring(18));
                    }
                    if (s.startsWith("\"published\"")) {
                        if (!allowExtention.isEmpty()) {
                            subType.add(allowExtention.substring(0, allowExtention.length() - 1));
                            allowExtention = "";
                        } else {
                            subType.add("null");
                        }

                        // reserved for published
                    }
//                    System.out.println(s);

                }
                // do not delete the following. these codes are for test purpose.
//                for(int i = 0; i < closeDate.size(); i++)
//                System.out.println(closeDate.get(i));
//                System.out.println(strID.size());
//                System.out.println(strName.size());
//                System.out.println(descrip.size());
//                System.out.println(closeDate.size());
//                System.out.println(dueDate.size());
//                System.out.println(openDate.size());
                for (int i = 0; i < strID.size(); i++) {
                    assignmentsList.add(new Assignment(strName.get(i), strID.get(i)));
                    //filter the first and last "
                    if (descrip.get(i).charAt(0)=='"'&&descrip.get(i).charAt(descrip.get(i).length()-1)=='"')                    	
                    	assignmentsList.get(i).setAssignmentDescription(descrip.get(i).substring(1, descrip.get(i).length()-1));
                    else
                    	assignmentsList.get(i).setAssignmentDescription(descrip.get(i));
                    assignmentsList.get(i).setCloseDate(closeDate.get(i));
                    assignmentsList.get(i).setDueDate(dueDate.get(i));
                    assignmentsList.get(i).setOpenDate(openDate.get(i));
                    assignmentsList.get(i).setSubmissionTypes(subType.get(i));
                    assignmentsList.get(i).setPoints(points.get(i));
                }
            }
        } else {
            assignmentsList.add(new Assignment("Unavailable", "Unavailable"));
            assignmentsList.get(0).setAssignmentDescription("Unavailable");
            assignmentsList.get(0).setCloseDate("Unavailable");
            assignmentsList.get(0).setDueDate("Unavailable");
            assignmentsList.get(0).setOpenDate("Unavailable");
            assignmentsList.get(0).setPoints("Unavailable");
            assignmentsList.get(0).setSubmissionTypes("Unavailable");
        }
    }

    // get token from token.dat
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

    void addAssignment(Assignment assignment) {
    	if (assignment!=null&&assignment.getAssignmentName().equals("Unavailable"))
    		this.assignmentsList.add(assignment);
    }
}
