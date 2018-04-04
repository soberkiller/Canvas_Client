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
        
        
        //JPanel toolbar = new JPanel();
        
        
        //TOP BAR
        //////////////////////////////////////////////////////////////
        
        JPanel topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(1024, 100));
        topBar.setBackground(Color.WHITE);
        
        //topBar.setLayout(new GridLayout(1, 3, 50, 0));
        
        JPanel logoPanel = new JPanel();
        logoPanel.setPreferredSize(new Dimension(180, 100));
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
        
        /*
        
        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setPreferredSize(new Dimension(160, 70));
        //clearButton.addActionListener(new ButtonListener());
        //classButton.setIcon(defaultIcon);
        topBar.add(BorderLayout.CENTER, newAssignmentButton);
        
        */
        /*
        JPanel topBarCourseSubPanel = new JPanel();
        //topBarCourseSubPanel.setLayout(new BoxLayout(topBarCourseSubPanel, BoxLayout.Y_AXIS));
        topBarCourseSubPanel.setPreferredSize(new Dimension(724, 110));
        topBarCourseSubPanel.setBackground(Color.WHITE);
        */
        //JLabel spacer = new JLabel("");
        //topBarCourseSubPanel.add(BorderLayout.WEST, spacer);
        
        JPanel titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(500, 100));
        titlePanel.setBackground(Color.WHITE);
                // temporary course name

        final String courseTitle = "Java (EE 552)";
        
        Font courseTitleFont = new Font("Helvetica", Font.BOLD, 36);

        JLabel currentCourseName = new JLabel(courseTitle);
        currentCourseName.setHorizontalAlignment(SwingConstants.RIGHT);
        currentCourseName.setFont(courseTitleFont);
        
        //currentCourseName.setAlignmentY(Component.RIGHT_ALIGNMENT);

        titlePanel.add(currentCourseName);
        //currentCourseName.setAlignmentY(100);
        
        //topBarCourseSubPanel.add(Box.createGlue());

        
        JPanel switchPanel = new JPanel();
        //switchPanel.setLayout(new GridLayout(3, 1, 0, 0));
        switchPanel.setPreferredSize(new Dimension(250, 100));
        switchPanel.setBackground(Color.WHITE);
        
        


        
        JButton switchCourseButton = new JButton("Switch Course");
        switchCourseButton.setPreferredSize(new Dimension(120, 80));
        JButton pushChangesToCanvasButton = new JButton("Push To Canvas");
        pushChangesToCanvasButton.setPreferredSize(new Dimension(120, 80));
        //switchCourseButton.setBackground(Color.RED);
        //clearButton.addActionListener(new ButtonListener());
        switchPanel.add(BorderLayout.WEST, switchCourseButton);
        switchPanel.add(BorderLayout.EAST, pushChangesToCanvasButton);

        //switchCourseButton.setAlignmentY(100);
        topBar.add(switchPanel);


        
        /*
        //Testing image backgrounds for JButton
        JButton switchCourseButton = new JButton("Switch Course");
        switchCourseButton.setPreferredSize(new Dimension(160, 70));
        
        
        BufferedImage buttonicon;
        try 
        {
            stevensLogo = ImageIO.read(new File("stevenslogo.jpeg"));
            Image dimg = stevensLogo.getScaledInstance(180, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            switchCourseButton.setIcon(imageIcon1);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        switchCourseButton.setBorderPainted(true);
        */
        topBar.add(BorderLayout.EAST, logoPanel);
        topBar.add(BorderLayout.EAST, titlePanel);
        topBar.add(BorderLayout.EAST, switchPanel);

        c.add(BorderLayout.NORTH, topBar);
        
        
        
        //////////////////////////////////////////////////////////////
        
        
        
        //LEFT-SIDE MENU
        //////////////////////////////////////////////////////////////
        JPanel leftToolbar = new JPanel();
        leftToolbar.setPreferredSize(new Dimension(100, 768));
        leftToolbar.setBackground(Color.GRAY);
        
        
        
        JButton classButton = new JButton("C");
        classButton.setPreferredSize(new Dimension(160, 50));
        //clearButton.addActionListener(new ButtonListener());
        //classButton.setIcon(defaultIcon);
        leftToolbar.add(classButton);
        
        JButton clearButton = new JButton("C");
        clearButton.setPreferredSize(new Dimension(160, 50));
        //clearButton.addActionListener(new ButtonListener());
        clearButton.setBackground(Color.GRAY);
        leftToolbar.add(clearButton);

        //leftToolbar.add(tf);
        
        c.add(BorderLayout.WEST, leftToolbar);


        
        
        
        JTextField tf = new JTextField();
        c.add(BorderLayout.CENTER, tf);
        
        
        
        
    int numberOfButtons=15;
    

    // Label to display the message indicating which button generated the event.

    //JLabel label=new JLabel();

    // Panel to accomodate the labels

    JPanel panel=new JPanel(); 
    //panel.setMaximumSize(new Dimension(100, 768));
    panel.setBackground(Color.WHITE);
    
    panel.setLayout(new GridLayout(numberOfButtons, 1));

    //We create an array of buttons whose number depends on our choice  

    JButton buttons[]=new JButton[numberOfButtons]; 


    //We create an instance of the class ButtonHandler which implements the interface
    //ActionListener. And this object "handler" is basically the object that handles 
    //the events.            

    //ButtonHandler handler=new ButtonHandler();  

    //Using a for loop we create JButtons.    

    for(int i=0;i<numberOfButtons;i++){
        buttons[i] = new JButton("Assignment"+i);
        buttons[i].setPreferredSize(new Dimension(180, 50));

        // here we register handler as the event listener for all the buttons.i.e if in 
        // case an event occurs in any of the buttons, the event listener (in our case
        //the handler object) calls an appropriate method that handles the event.            

        //buttons[i].addActionListener(handler);

        // add each button to the panel

        panel.add(buttons[i]);
    }

   // we add the label to the JFrame 
   //toolbar.add(label,BorderLayout.EAST);
   // we add ScrollPane to the JFrame and in turn add Panel to the ScrollPane.
   //add(label,BorderLayout.SOUTH);
   // we add ScrollPane to the JFrame and in turn add Panel to the ScrollPane.
   JScrollPane scroller = new JScrollPane(panel);
   scroller.setPreferredSize(new Dimension(200, 768));
   c.add(BorderLayout.EAST, scroller);     
        
        
        
        
        
        
        
        
        
        //toolbar.add(BorderLayout.WEST, leftToolbar);
        
        setVisible(true);

    }
    
    
    
    
    
}

