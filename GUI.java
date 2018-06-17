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
        logoPanel.setPreferredSize(new Dimension(250, 100));
        logoPanel.setBackground(Color.WHITE);
        
        BufferedImage stevensLogo;
        try 
        {
            stevensLogo = ImageIO.read(new File("stevenslogo.jpeg"));
            Image dimg = stevensLogo.getScaledInstance(180, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            JLabel logoLabel = new JLabel(imageIcon1);
            logoPanel.add(BorderLayout.WEST, logoLabel);  
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
        titlePanel.setPreferredSize(new Dimension(400, 100));
        titlePanel.setBackground(Color.WHITE);
                // temporary course name

        final String courseTitle = "Java (EE 552)";
        
        Font courseTitleFont = new Font("Helvetica", Font.BOLD, 36);

        JLabel currentCourseName = new JLabel(courseTitle);
        //currentCourseName.setHorizontalAlignment(SwingConstants.RIGHT);
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
        switchCourseButton.setPreferredSize(new Dimension(125, 80));
        //switchCourseButton.setBackground(Color.RED);
        //clearButton.addActionListener(new ButtonListener());
        switchPanel.add(BorderLayout.CENTER, switchCourseButton);
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

=======
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MattSorrentino
 */

//runtime r = Runtime.getRuntime();
//r.exec("Plagarism Dir");

/**
 *
 * @class GUI
 * Handles the GUI of the CanvasClient Application.
 */

public class GUI extends JFrame {
    private Container c;


    public GUI(Course currentCourse, ArrayList<Course> courseList) {
        super(currentCourse.getCourseName());

        setSize(1568, 968);
        this.setLocationRelativeTo(null);
        c = getContentPane();

        addWindowListener(new MyWindowListener());

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
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

        JLabel currentCourseNameLabel = new JLabel(" " + currentCourse.getCourseName());
        currentCoursePanel.add(currentCourseNameLabel);
        Font courseTitleFont = new Font("Helvetica", Font.BOLD, 16);
        currentCourseNameLabel.setFont(courseTitleFont);

        JPanel courseOptionsPanel = new JPanel();
        courseOptionsPanel.setBackground(Color.WHITE);
        courseOptionsPanel.setLayout(new GridLayout(1, 2, 10, 0));
                
        JButton settingsButton = new JButton("Settings");
        settingsButton.setBackground(Color.WHITE);
        settingsButton.setFocusable(false);
        courseOptionsPanel.add(settingsButton);
        settingsButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    new SettingsPane(currentCourse);
                }
            }  
        );

        JButton switchCourseButton = new JButton("Switch Course");
        //switchCourseButton.setPreferredSize(new Dimension(120,40));
        switchCourseButton.setBackground(Color.WHITE);
        switchCourseButton.setFocusable(false);
        courseOptionsPanel.add(switchCourseButton);
        switchCourseButton.addActionListener(
            new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    new CourseSelector(courseList);
                    setVisible(false); //closes previous GUI, garbage collector will delete instance eventually
                }
            }
        );


        currentCoursePanel.add(courseOptionsPanel);

        topBar.add(currentCoursePanel);

        ////////////////////////////////////////////////////////////////////////

        JPanel logoPanel = new JPanel();
        //logoPanel.setPreferredSize(new Dimension(180, 100));
        logoPanel.setBackground(Color.WHITE);

        BufferedImage stevensLogo;
        try {
            stevensLogo = ImageIO.read(new File("stevenslogo.jpeg"));
            Image dimg = stevensLogo.getScaledInstance(180, 90, Image.SCALE_SMOOTH);
            ImageIcon imageIcon1 = new ImageIcon(dimg);
            JLabel logoLabel = new JLabel(imageIcon1);
            logoPanel.add(BorderLayout.CENTER, logoLabel);
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


        topBar.add(logoPanel);

        ////////////////////////////////////////////////////////////////////////

        JPanel majorActionsPanel = new JPanel();
        majorActionsPanel.setBackground(Color.WHITE);
        majorActionsPanel.setLayout(new GridLayout(1, 1, 0, 0));
        majorActionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        /*
        //majorActionsPanel.add(spacer2);
        JButton pushChangesToCanvas = new JButton("Push To Canvas");
        //pushChangesToCanvas.setPreferredSize(new Dimension(160,80));
        majorActionsPanel.add(pushChangesToCanvas);
        pushChangesToCanvas.setBackground(Color.lightGray);
        */

        JButton launchStudentViewer = new JButton("View Students");
        //pushChangesToCanvas.setPreferredSize(new Dimension(160,80));
        majorActionsPanel.add(launchStudentViewer);
        launchStudentViewer.setFocusable(false);
        launchStudentViewer.setBackground(Color.lightGray);
        launchStudentViewer.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        StudentViewer sv = new StudentViewer(currentCourse);
                    }
                }
        );


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

        Main main = new Main();
        c.add(BorderLayout.CENTER, main.jPanel1);

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


        //fetch number of assignments the course has
        int numberOfAssignments = currentCourse.getAssignmentsList().size();

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

        assignmentsListPanel.setLayout(new GridLayout(numberOfAssignments + 1, 1));

        JButton buttons[] = new JButton[numberOfAssignments];

        //Using a for loop we create temporary JButtons.    

        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setBackground(Color.lightGray);
        assignmentsListPanel.add(newAssignmentButton);

        for(int i = 0; i < numberOfAssignments; i++)
        {
            String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
            String middleTags = "</h3><p>";
            String closingTags = "</p></body></html>";
            String lineBreak = "<br/>";
            
            String assignmentTitle = currentCourse.getAssignmentsList().get(i).getAssignmentName();
            String assignmentDueDate = "due: " + currentCourse.getAssignmentsList().get(i).getDueDate();  
            String assignmentCloseDate = "closes: " + currentCourse.getAssignmentsList().get(i).getCloseDate();  
            String Description = currentCourse.getAssignmentsList().get(i).getAssignmentDescription();
            String grade = Double.toString(new Submission().getSubmissionGrade());
            
            buttons[i] = new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags);
            buttons[i].setHorizontalAlignment(SwingConstants.LEFT);
            buttons[i].setPreferredSize(new Dimension(180, 100));
            buttons[i].setBackground(Color.WHITE);

            assignmentsListPanel.add(buttons[i]);
            buttons[i].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            main.jTextArea1.append(Description);
            main.jTextField10.setText(assignmentTitle);
            main.jTextField6.setText(assignmentDueDate);
            main.jTextField7.setText(assignmentCloseDate);
            main.jTextField8.setText(grade);
            }
            });
        }


        JScrollPane scroller = new JScrollPane(assignmentsListPanel);
        scroller.setPreferredSize(new Dimension(254, 768));
        c.add(BorderLayout.WEST, scroller);

        setVisible(true);

    }

}

