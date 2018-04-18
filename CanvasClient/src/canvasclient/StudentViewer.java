/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author MattSorrentino
 */
public class StudentViewer extends JFrame 
{
    
    private Container c;
    JButton b;
    
    Student currentStudent;
    
    public StudentViewer (Course currentCourse)
    {
        super("Student Viewer");

        setSize(768,500);
        this.setLocationRelativeTo(null);
        c = getContentPane();
        
        try 
        {
           UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
 
        
        JPanel studentListPanel = new JPanel();
        studentListPanel.setLayout(new GridLayout(currentCourse.getStudentsList().size(), 1));

        // Create a list of JButtons, one for each student in the current course
        // currentCourse is a setting, it is a static member of CanvasClient
        
        /*
        for (int i = 0; i < CanvasClient.getCurrentCourse().getStudentsList().size(); i++) 
        {
            studentListPanel.add(new JButton(CanvasClient.getCurrentCourse().getStudentsList().get(i).getStudentName()));
        }
        */
        
        for (int i = 0; i < currentCourse.getStudentsList().size(); i++) 
        {
            //studentListPanel.add(new JButton(currentCourse.getStudentsList().get(i).getStudentName()));
            System.out.println(currentCourse.getStudentsList().get(i).getStudentName());
            b = new JButton(currentCourse.getStudentsList().get(i).getStudentName());
            b.setPreferredSize(new Dimension(200, 50));
            b.setBackground(Color.WHITE);
            studentListPanel.add(b);
        }
        
        
        JScrollPane scroller = new JScrollPane(studentListPanel);
        scroller.setPreferredSize(new Dimension(254, 768));
        c.add(BorderLayout.WEST, scroller); 
        
        
        JPanel infoPanel = new JPanel();
        
        JLabel nameLabel = new JLabel(currentStudent.getStudentName());
        JLabel idLabel = new JLabel(currentStudent.getStudentID());
        JLabel gradeLabel = new JLabel();
        //parseDouble -----  (currentStudent.getCourseGrade()
        
        
        //add these to the panel and lay out.
        
        
        
        
        
        setVisible(true);
        
    }

}
    
    