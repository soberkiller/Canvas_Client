/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author MattSorrentino
 */
public class CourseSelector extends JFrame 
{
    
    public CourseSelector()
    {
        super("Course Selector");
        setSize(300, 500);
        
        Container c = getContentPane();

        
        for (int i = 0; i < canvasClient.getCourseList().size(); i++)
        {
            JButton b = new JButton(canvasClient.getCourseList(i));
            c.add(b);
            b.addActionListener(CourseSelectorListener);
        }
        
        
        
        setVisible(true);
    }
    
    
    
    public static void main(String args[])
    {
        new CourseSelector();
    }
    
    
}

class CourseSelectorListener implements ActionListener
{
    @Override    
    public void actionPerformed(ActionEvent e)
        {
            
        }
}