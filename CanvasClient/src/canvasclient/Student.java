/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

/**
 * @author MattSorrentino
 */
public class Student {


    private String studentName;

    private String studentSISID;

    private String studentEmail;

    private String studentID;

    //grade the student has in the course
    private double courseGrade;


    public Student(String studentName, String studentSISID, String studentEmail, String studentID) {
        this.studentName = studentName;
        this.studentSISID = studentSISID;
        this.studentEmail = studentEmail;
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentSISID() {
        return studentSISID;
    }

    public double getCourseGrade() {
        return courseGrade;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentID() {return studentID;};

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }


}
