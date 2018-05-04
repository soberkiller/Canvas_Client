/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MattSorrentino
 */
public class StudentViewer extends JFrame {

    private JButton b;
    private JPanel infoPanel;
    private Student currentStudent;
    
    private JLabel nameLabel, idLabel, emailLabel, gradeLabel;
    
    
    private Container c;

    public StudentViewer(Course currentCourse) {
        super("Student Viewer");

        setSize(768, 400);
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
            // System.out.println(currentCourse.getStudentsList().get(i).getStudentName());
            final Student thisStudent = currentCourse.getStudentsList().get(i);
            b = new JButton(thisStudent.getStudentName());
            b.setPreferredSize(new Dimension(200, 30));
            b.setBackground(Color.WHITE);
            b.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        currentStudent = thisStudent;
                        updateInfoPanel();
                    }
                }
            );
            /*
            b.addFocusListener(
                new FocusListener()
                {
                    @Override
                    public void focusGained(FocusEvent e)
                    {
                        b.setBackground(Color.getHSBColor(210, 60, 100));
                        b.setForeground(Color.WHITE);
                        System.out.println("focusGained");
                    }

                    @Override
                    public void focusLost(FocusEvent e) 
                    {
                        b.setBackground(Color.WHITE);
                        b.setForeground(Color.BLACK);
                        System.out.println("focusLost");
                    }
                }
            );
            */
            studentListPanel.add(b);
        }


        JScrollPane scroller = new JScrollPane(studentListPanel);
        scroller.setPreferredSize(new Dimension(254, 768));
        c.add(BorderLayout.WEST, scroller);

        //set temp current student for testing
        currentStudent = currentCourse.getStudentsList().get(0);

        infoPanel = new JPanel();
        infoPanel.setBackground(Color.white);
        infoPanel.setLayout(new GridLayout(3, 1, 20, 0));
        setInfoPanel();

        //add these to the panel and lay out.


        setVisible(true);

    }

    public void setInfoPanel() {
        JPanel headerPanel = new JPanel();
        JPanel imagePanel = new JPanel();
        JPanel nameidPanel = new JPanel();
        nameidPanel.setLayout(new GridLayout(2, 1, 10, 0));
        nameidPanel.setBorder(new EmptyBorder(25, 25, 25, 25));


        nameLabel = new JLabel(currentStudent.getStudentName());
        Font nameFont = new Font("Helvetica", Font.BOLD, 32);
        nameLabel.setFont(nameFont);
        idLabel = new JLabel(currentStudent.getStudentSISID());


        BufferedImage profilePic;
        try {
            profilePic = ImageIO.read(new File("profilepic.png"));
            Image dimg = profilePic.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            JLabel picLabel = new JLabel(imageIcon1);
            imagePanel.add(BorderLayout.CENTER, picLabel);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(2, 1, 10, 0));
        dataPanel.setBorder(new EmptyBorder(25, 25, 25, 25));
        dataPanel.setBackground(Color.white);

        emailLabel = new JLabel("Email: " + currentStudent.getStudentEmail());
        gradeLabel = new JLabel("Course Grade: " + currentStudent.getCourseGrade());

        dataPanel.add(emailLabel);
        dataPanel.add(gradeLabel);


        //JLabel gradeLabel = new JLabel(Double.toString(currentStudent.getCourseGrade()));

        nameidPanel.add(nameLabel);
        nameidPanel.add(idLabel);
        headerPanel.add(imagePanel);
        headerPanel.add(nameidPanel);


        infoPanel.add(BorderLayout.NORTH, headerPanel);
        //infoPanel.add(BorderLayout.CENTER, idLabel);
        infoPanel.add(BorderLayout.SOUTH, dataPanel);

        c.add(infoPanel);
    }
    
    
    public void updateInfoPanel() {
        
        nameLabel.setText(currentStudent.getStudentName());
        
        idLabel.setText(currentStudent.getStudentSISID());

        /*
        BufferedImage profilePic;
        try {
            profilePic = ImageIO.read(new File("profilepic.png"));
            Image dimg = profilePic.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            JLabel picLabel = new JLabel(imageIcon1);
            imagePanel.add(BorderLayout.CENTER, picLabel);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        */

        emailLabel.setText("Email: " + currentStudent.getStudentEmail());
        gradeLabel.setText("Course Grade: " + currentStudent.getCourseGrade());


        //JLabel gradeLabel = new JLabel(Double.toString(currentStudent.getCourseGrade()));

      
    }
}
    
    