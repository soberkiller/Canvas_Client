/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team.canvasgroup.canvasclient;

/**
 * @author MattSorrentino
 */
public class Student {


    private String studentName;

    private String studentID;

    private String studentEmail;

    //grade the student has in the course
    private double courseGrade;


    public Student(String studentName, String studentID) {
        this.studentName = studentName;
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public double getCourseGrade() {
        return courseGrade;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }


}
