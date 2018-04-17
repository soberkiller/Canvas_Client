/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author MattSorrentino
 */

//runtime r = Runtime.getRuntime();
//r.exec("Plagarism Dir");

/**
 *
 * @class GUI
 * Handles the GUI of the CanvasClient Application.
 */

public class GUI extends JFrame 
{
    private Container c;
    
    
    public GUI ()
    {
        super("Canvas Client");

        setSize(1024,768);
        c = getContentPane();
        
        addWindowListener(new MyWindowListener());
        
        try 
        {
           UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        //JLabel to be used as a spacer for GridLayout Panels
        JLabel spacer1 = new JLabel(" ");
        JLabel spacer2 = new JLabel(" ");
        
        
        ////////////////////////////////////////////////////////////////////////
        //
        //  topBar Panel - Top Bar of GUI 
        //
        //  Author: Matt Sorrentino
        //
        //  Contains 3 panels: currentCoursePanel, logoPanel, majorActionsPanel
        //  
        ////////////////////////////////////////////////////////////////////////
        
        JPanel topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(1024, 100));
        topBar.setBackground(Color.WHITE);

        topBar.setLayout(new GridLayout(1, 3, 70, 25));
        

        ////////////////////////////////////////////////////////////////////////

        JPanel currentCoursePanel = new JPanel();
        currentCoursePanel.setBackground(Color.WHITE);
        currentCoursePanel.setLayout(new GridLayout(2, 1, 10, 0));
        currentCoursePanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        //currentCoursePanel.add(spacer1);

        final String currentCourseName = "Java (EE 522)";
        
        JLabel currentCourseNameLabel = new JLabel(" " + currentCourseName);
        currentCoursePanel.add(currentCourseNameLabel);
        Font courseTitleFont = new Font("Helvetica", Font.BOLD, 16);
        currentCourseNameLabel.setFont(courseTitleFont);

        JPanel courseOptionsPanel = new JPanel();
        courseOptionsPanel.setBackground(Color.WHITE);
        courseOptionsPanel.setLayout(new GridLayout(1, 2, 10, 0));
                
        JButton courseSettingsButton = new JButton("Settings");
        //courseSettingsButton.setPreferredSize(new Dimension(70,40));
        courseSettingsButton.setBackground(Color.WHITE);
        courseOptionsPanel.add(courseSettingsButton);
        
        JButton switchCourseButton = new JButton("Switch Course");
        //switchCourseButton.setPreferredSize(new Dimension(120,40));
        switchCourseButton.setBackground(Color.WHITE);
        courseOptionsPanel.add(switchCourseButton);
        
        
        
        
        
        currentCoursePanel.add(courseOptionsPanel);
        
        topBar.add(currentCoursePanel);
        
        ////////////////////////////////////////////////////////////////////////
        
        JPanel logoPanel = new JPanel();
        //logoPanel.setPreferredSize(new Dimension(180, 100));
        logoPanel.setBackground(Color.WHITE);
        
        BufferedImage stevensLogo;
        try 
        {
            stevensLogo = ImageIO.read(new File("stevenslogo.jpeg"));
            Image dimg = stevensLogo.getScaledInstance(180, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            JLabel logoLabel = new JLabel(imageIcon1);
            logoPanel.add(BorderLayout.CENTER, logoLabel);  
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        
        topBar.add(logoPanel);

        ////////////////////////////////////////////////////////////////////////
                
        JPanel majorActionsPanel = new JPanel();
        majorActionsPanel.setBackground(Color.WHITE);
        majorActionsPanel.setLayout(new GridLayout(1, 1, 0, 0));
        majorActionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        
        //majorActionsPanel.add(spacer2);
        JButton pushChangesToCanvas = new JButton("Push To Canvas");
        //pushChangesToCanvas.setPreferredSize(new Dimension(160,80));
        majorActionsPanel.add(pushChangesToCanvas);
        pushChangesToCanvas.setBackground(Color.lightGray);

        
        topBar.add(majorActionsPanel);
        
        ////////////////////////////////////////////////////////////////////////
        
        c.add(BorderLayout.NORTH, topBar);
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
       
        ////////////////////////////////////////////////////////////////////////
        //
        //  leftToolbar Panel - Left Bar of GUI
        //
        //  Author: Matt Sorrentino
        //
        //  Contains buttons
        //  
        ////////////////////////////////////////////////////////////////////////
        /*
        JPanel leftToolbar = new JPanel();
        leftToolbar.setPreferredSize(new Dimension(100, 768));
        leftToolbar.setLayout(new GridLayout(3, 1, 0, 0));
        leftToolbar.setBorder(new EmptyBorder(0, 0, 0, 0));
        leftToolbar.setBackground(Color.GRAY);
        
        
        
        JButton editAssignmentButton = new JButton("Edit");
        editAssignmentButton.setPreferredSize(new Dimension(80, 80));
        editAssignmentButton.setBackground(Color.lightGray);
        leftToolbar.add(editAssignmentButton);
        
        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setPreferredSize(new Dimension(80, 80));
        newAssignmentButton.setBackground(Color.lightGray);
        leftToolbar.add(newAssignmentButton);
        
        JButton courseSettingsButton = new JButton("Course Settings");
        courseSettingsButton.setPreferredSize(new Dimension(80, 80));
        courseSettingsButton.setBackground(Color.lightGray);
        leftToolbar.add(courseSettingsButton);
        
        c.add(BorderLayout.WEST, leftToolbar);
        */

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        
        ////////////////////////////////////////////////////////////////////////
        //
        //  main Panel - The main panel of the main window
        //
        //  Author: Matt Sorrentino
        //
        //  Contains whatever content is necessary for the current view
        //  
        ////////////////////////////////////////////////////////////////////////
        
        JPanel main = new JPanel();
        main.setBackground(Color.gray);
        c.add(BorderLayout.CENTER, main);
        
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        
        ////////////////////////////////////////////////////////////////////////
        //
        //  assignmentsPanel - The panel containing the scrollview of assignments
        //
        //  Author: Matt Sorrentino
        //
        //  Contains JScrollPane which has a list of assignments for the current class
        //  Contains the New Assignment button 
        //  
        ////////////////////////////////////////////////////////////////////////
        
        
        
        //temporary value for the number of assignments a class has
        int numberOfButtons=15;

        JPanel assignmentsPanel = new JPanel();
        
        /*
        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setBackground(Color.lightGray);
        assignmentsPanel.add(BorderLayout.NORTH, newAssignmentButton);
        */
        
        // Label to display the message indicating which button generated the event.

        //JLabel label=new JLabel();

        // Panel to accomodate the labels

        JPanel assignmentsListPanel = new JPanel(); 
        //panel.setMaximumSize(new Dimension(100, 768));
        assignmentsListPanel.setBackground(Color.WHITE);

        assignmentsListPanel.setLayout(new GridLayout(numberOfButtons+1, 1));

        JButton buttons[]=new JButton[numberOfButtons]; 

        //Using a for loop we create temporary JButtons.    
        
        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setBackground(Color.lightGray);
        assignmentsListPanel.add(newAssignmentButton);
        
        for(int i=0;i<numberOfButtons;i++){
            buttons[i] = new JButton("Assignment "+i);
            buttons[i].setPreferredSize(new Dimension(180, 50));
            buttons[i].setBackground(Color.WHITE);
            
            assignmentsListPanel.add(buttons[i]);
        }

     
       JScrollPane scroller = new JScrollPane(assignmentsListPanel);
       scroller.setPreferredSize(new Dimension(254, 768));
       c.add(BorderLayout.WEST, scroller);             
        
        setVisible(true);

    }
    
}

