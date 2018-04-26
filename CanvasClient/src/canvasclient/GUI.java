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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class GUI extends JFrame {
    private static Course cCourse;

    private Container c;

    private static List<JButton> buttonsAssignment;
    private static JPanel assignmentsListPanel = new JPanel();
    private JLabel currentCourseNameLabel;
    private JScrollPane scroller;
    private JButton newAssignmentButton = new JButton("New Assignment");
    private Main main;

    private final Base64.Decoder decoder = Base64.getDecoder();
    private final String FILENAME = "token.dat";

    public GUI(Course currentCourse, ArrayList<Course> courseList) {
        cCourse = currentCourse;
        CourseSelector CSelector = new CourseSelector();
        setTitle(cCourse.getCourseName());
        setSize(1024, 768);
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


        currentCourseNameLabel = new JLabel(" " + cCourse.getCourseName());

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
                    new SettingsPane(cCourse);
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
                    CSelector.createSelector(courseList);
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
                        // add students
                        StudentViewer sv = new StudentViewer(cCourse);

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

        main = new Main();
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
        int numberOfAssignments = cCourse.getAssignmentsList().size();

        JPanel assignmentsPanel = new JPanel();
        
        

        /*
        JButton newAssignmentButton = new JButton("New Assignment");
        newAssignmentButton.setBackground(Color.lightGray);
        assignmentsPanel.add(BorderLayout.NORTH, newAssignmentButton);
        */


        // Label to display the message indicating which button generated the event.

        //JLabel label=new JLabel();

        // Panel to accomodate the labels


        //panel.setMaximumSize(new Dimension(100, 768));
        assignmentsListPanel.setBackground(Color.WHITE);

        assignmentsListPanel.setLayout(new GridLayout(numberOfAssignments + 1, 1));



        //Using a for loop we create temporary JButtons.    


        newAssignmentButton.setFocusable(false);
        newAssignmentButton.setBackground(Color.lightGray);
        newAssignmentButton.addActionListener(e -> {
            if(e.getSource() == newAssignmentButton) {

                try {
                    // ini field and endpoint;
                    List<String> fields = new ArrayList<String>();
                    final String GET = "GET";
                    final String PUT = "PUT";
                    final String POST = "POST";

                    String endpoints = "";

                    fields.add("courses");
                    fields.add(cCourse.getCourseID());
                    fields.add("assignments");
                    endpoints += "?";

                    fields.add(endpoints);
                    ConnectionPool newAssignment = new ConnectionPool(fields, 0.1,  new String(decoder.decode(getOAUTH2()), "UTF-8"));
                    newAssignment.setMethod(POST);
                    // add function to set up new assignment


                    // get response from Canvas and refresh main window. If new assignment is added, add new assignment button on the left.

                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });
        assignmentsListPanel.add(newAssignmentButton);
        buttonsAssignment = new ArrayList<>(numberOfAssignments);
        for(int i = 0; i < numberOfAssignments; i++)
        {
            String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
            String middleTags = "</h3><p>";
            String closingTags = "</p></body></html>";
            String lineBreak = "<br/>";
            
            String assignmentTitle = cCourse.getAssignmentsList().get(i).getAssignmentName();
            String assignmentDueDate = "due: " + cCourse.getAssignmentsList().get(i).getDueDate();
            String assignmentCloseDate = "closes: " + cCourse.getAssignmentsList().get(i).getCloseDate();
            final int a = i;
            buttonsAssignment.add(new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags));
            buttonsAssignment.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            buttonsAssignment.get(i).setPreferredSize(new Dimension(180, 100));
            buttonsAssignment.get(i).setBackground(Color.WHITE);
            buttonsAssignment.get(i).addActionListener(e-> {
                if(e.getSource() == buttonsAssignment.get(a)) {
                    main.jTextArea1.setText(cCourse.getAssignmentsList().get(a).getAssignmentDescription());
                    main.jTextField10.setText(cCourse.getAssignmentsList().get(a).getAssignmentName());
                    main.jTextField6.setText(cCourse.getAssignmentsList().get(a).getDueDate());
                    main.jTextField7.setText(cCourse.getAssignmentsList().get(a).getCloseDate());
                    main.jTextField8.setText("Unavailable");
                }
            });

            assignmentsListPanel.add(buttonsAssignment.get(i));
        }
//        assignmentsListPanel.removeAll();


        scroller = new JScrollPane(assignmentsListPanel);
        scroller.setPreferredSize(new Dimension(254, 768));
        c.add(BorderLayout.WEST, scroller);

        setVisible(true);

    }

    // get token from token.dat
    public String getOAUTH2() {
        File tFile = new File(FILENAME);
        StringBuffer content = new StringBuffer();
        // the length of stream read from file is larger than the content of that file, so have to deal with it
        Course.getFromFile(tFile, content);

        return content.toString();
    }

    // reset current course
    public void setCurrentCourse(Course currentCourse) {
        cCourse = currentCourse;
    }

    // reset current assignment panel and assignment buttons
    public void resetAPB(Course currentCourse) {
        setCurrentCourse(currentCourse);
        assignmentsListPanel.removeAll();

        setTitle(cCourse.getCourseName());
        currentCourseNameLabel.setText(cCourse.getCourseName());


        int numAssignments = cCourse.getAssignmentsList().size();
        assignmentsListPanel.setLayout(new GridLayout(numAssignments + 1, 1));
        assignmentsListPanel.add(newAssignmentButton);
        // previous array of button is still there ??
        buttonsAssignment = new ArrayList<>(numAssignments);
        for(int i = 0; i < numAssignments; i++)
        {
            String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
            String middleTags = "</h3><p>";
            String closingTags = "</p></body></html>";
            String lineBreak = "<br/>";

            String assignmentTitle = cCourse.getAssignmentsList().get(i).getAssignmentName();
            String assignmentDueDate = "due: " + cCourse.getAssignmentsList().get(i).getDueDate();
            String assignmentCloseDate = "closes: " + cCourse.getAssignmentsList().get(i).getCloseDate();
            final int a = i;
            buttonsAssignment.add(new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags));
            buttonsAssignment.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            buttonsAssignment.get(i).setPreferredSize(new Dimension(180, 100));
            buttonsAssignment.get(i).setBackground(Color.WHITE);

            buttonsAssignment.get(i).addActionListener(e-> {
                if(e.getSource() == buttonsAssignment.get(a)) {
                    main.jTextArea1.setText(cCourse.getAssignmentsList().get(a).getAssignmentDescription());
                    main.jTextField10.setText(cCourse.getAssignmentsList().get(a).getAssignmentName());
                    main.jTextField6.setText(cCourse.getAssignmentsList().get(a).getDueDate());
                    main.jTextField7.setText(cCourse.getAssignmentsList().get(a).getCloseDate());
                    main.jTextField8.setText("Unavailable");
                }
            });
            assignmentsListPanel.add(buttonsAssignment.get(i));
            setVisible(true);

        }
        assignmentsListPanel.repaint();

    }

    // inner class for switching current course to desire course
    public class CourseSelector extends JFrame {
        public CourseSelector() {
            super("Course Selector");
            this.setLocationRelativeTo(null);

            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void createSelector(List<Course> courseList) {
            List<JButton> buttonArray = new ArrayList();
            setSize(200, courseList.size() * 50);

            Container c = getContentPane();
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(courseList.size(), 1, 0, 0));

            for (int i = 0; i < courseList.size(); i++) {
                String s = courseList.get(i).getCourseName();
                buttonArray.add(new JButton(s));
                buttonPanel.add(buttonArray.get(i));
                final int a = i;
                buttonArray.get(i).setBackground(Color.white);
                buttonArray.get(i).setFocusable(false);
                buttonArray.get(i).addActionListener(
                        e -> {
                            if(e.getSource() == buttonArray.get(a)) {
                                //                                new CanvasClient(courseList.get(a));
                                resetAPB(courseList.get(a));

                                this.setVisible(false);
                            }
                        }
                );
            }
            c.add(buttonPanel);

            setVisible(true);
        }
    }

}

