/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author MattSorrentino
 */
public class StudentViewer extends JFrame 
{
    
    private Container c;
    
    public StudentViewer ()
    {
        super("Student Viewer");

        setSize(768,768);
        c = getContentPane();

        
        JPanel studentListPanel = new JPanel();

        // Create a list of JButtons, one for each student in the current course
        // currentCourse is a setting, it is a static member of CanvasClient
        
        for (int i = 0; i < CanvasClient.getCurrentCourse().getStudentsList().size(); i++) 
        {
            studentListPanel.add(new JButton(CanvasClient.getCurrentCourse().getStudentsList().get(i).getStudentName()));
        }
        
        setVisible(true);
        
    }
            
        
    public static void main (String args[])
    {
        StudentViewer sv = new StudentViewer();
    }

}
    
    