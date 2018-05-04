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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MattSorrentino
 */


/**
 *
 * @class GUI
 * Handles the main GUI frame of the CanvasClient Application.
 */

public class GUI extends PublicResouce {
    private static Course cCourse;

    private Container c;
    private int status;

    private static List<JButton> buttonsAssignment;
    private static JPanel assignmentsListPanel = new JPanel();
    private JLabel currentCourseNameLabel;
    private JScrollPane scroller;
    private JButton newAssignmentButton = new JButton("New Assignment");

    private final Base64.Decoder decoder = Base64.getDecoder();

    private final String FILENAME = "token.dat";

    
    /**
     * Draws main frame of program and pulls assignment data from Canvas.
     * @param currentCourse indicates the course currently selected.
     * @param courseList contains all courses for which the user has teacher, course administrator, TA, or grader permissions.
     * 
     */

    public GUI(Course currentCourse, ArrayList<Course> courseList) {
        cCourse = currentCourse;
        CourseSelector CSelector = new CourseSelector();
        setTitle(cCourse.getCourseName());
        setSize(1250, 768);
        this.setLocationRelativeTo(null);
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

        
        ////////////////////////////////////////////////////////////////////////
        //
        //  main Panel - The main panel of the main window
        //
        //  Author: Matt Sorrentino
        //
        //  Contains whatever content is necessary for the current view
        //  
        ////////////////////////////////////////////////////////////////////////
        

        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));
       
        assignmentName = new JLabel(cCourse.getAssignmentsList().get(0).getAssignmentName());
        Font assignmentNameFont = new Font("Helvetica", Font.BOLD , 30);        
        assignmentName.setFont(assignmentNameFont);
        assignmentNameButtonPanel.add(assignmentName);
        assignmentNameButtonPanel.add(new JLabel("          "));       
        assignmentNameField.setEditable(true);
        assignmentNameField.setBackground(Color.WHITE);
        assignmentNameField.setVisible(false);
        assignmentNameField.setFont(assignmentNameFont);
        assignmentNameButtonPanel.add(BorderLayout.WEST, assignmentNameField);
        
        editAssignment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (editAssignment.getText().equals("Edit")){
                	editAssignment.setText("Update");
                	editMode();            	
                } else {
                  //verification input by YYF
                  List<String> fields = new ArrayList<String>();
                  String endpoints = "";
                  fields.add("courses");
                  fields.add(cCourse.getCourseID());
                  fields.add("assignments");
                  endpoints += "?";
                  fields.add(endpoints);
                  ConnectionPool connection;
                	if (status == -1) {                        
						try {
							
							connection = new ConnectionPool(fields, 0.1,  new String(decoder.decode(getOAUTH2()), "UTF-8"));
							String newAssignmentId= connection.addAssignment(cCourse.getCourseID(), assignmentNameField.getText(), dateAvailableField.getText(),
									dateDueField.getText(), dateClosingField.getText(), pointsField.getText(), fileTypesField.getText(), 
									descriptionArea.getText());
							cCourse.getAssignmentsList().add(connection.getSingleAssignments(cCourse.getCourseID(), newAssignmentId));
							//addAssignmentButton(cCourse.getAssignmentsList().get(cCourse.getAssignmentsList().size()-1));
							resetAPB(currentCourse);
							clearText();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                	} else {
                		
                		try {
                			connection = new ConnectionPool(fields, 0.1,  new String(decoder.decode(getOAUTH2()), "UTF-8"));
							connection.updateAssignment(cCourse.getCourseID(), cCourse.getAssignmentsList().get(status).getAssignmentID(),assignmentNameField.getText(), dateAvailableField.getText(),
										dateDueField.getText(), dateClosingField.getText(), pointsField.getText(), fileTypesField.getText(), 
										descriptionArea.getText());
							cCourse.getAssignmentsList().set(status, connection.getSingleAssignments(cCourse.getCourseID(), cCourse.getAssignmentsList().get(status).getAssignmentID()));
							updateAssignmentButton(cCourse.getAssignmentsList().get(status),status);
							editAssignment.setText("Edit");
							readMode();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
							            	
                	}
                }
            }
        }
);
        editAssignment.setFocusable(false);
        editAssignment.setPreferredSize(new Dimension(100,50));
        assignmentNameButtonPanel.add(BorderLayout.EAST, editAssignment);


        dateAvailableField.setEditable(false);
        dateAvailableField.setBackground(Color.WHITE);
        dateDueField.setEditable(false);
        dateDueField.setBackground(Color.WHITE);
        dateClosingField.setEditable(false);
        dateClosingField.setBackground(Color.WHITE);
        pointsField.setEditable(false);
        pointsField.setBackground(Color.WHITE);
        latePenaltyField.setEditable(false);
        latePenaltyField.setBackground(Color.WHITE);
        fileTypesField.setEditable(false);
        fileTypesField.setBackground(Color.WHITE);
        descriptionArea.setEditable(false);
        descriptionArea.setContentType("text/html");
        descriptionScroll.setViewportView(descriptionArea);

        assignmentInfoPanel.setLayout(new GridLayout(2, 4, 20, 20));
        //assignmentInfoPanel.setBorder(new EmptyBorder(50, 50, 50, 50));

        dateAvailablePanel.setLayout(new GridLayout(1, 2, 10, 10));
        dateAvailablePanel.add(dateAvailable);
        dateAvailablePanel.add(dateAvailableField);
        assignmentInfoPanel.add(dateAvailablePanel);

        dateDuePanel.setLayout(new GridLayout(1, 2, 10, 10));
        dateDuePanel.add(dateDue);
        dateDuePanel.add(dateDueField);
        assignmentInfoPanel.add(dateDuePanel);

        JPanel dateClosingPanel = new JPanel();
        dateClosingPanel.setLayout(new GridLayout(1, 2, 10, 10));
        dateClosingPanel.add(dateClosing);
        dateClosingPanel.add(dateClosingField);
        assignmentInfoPanel.add(dateClosingPanel);

        pointsPanel.setLayout(new GridLayout(1, 2, 10, 10));
        pointsPanel.add(points);
        pointsPanel.add(pointsField);
        assignmentInfoPanel.add(pointsPanel);

        latePenaltyPanel.setLayout(new GridLayout(1, 2, 10, 10));
        latePenaltyPanel.add(latePenalty);
        latePenaltyPanel.add(latePenaltyField);
        assignmentInfoPanel.add(latePenaltyPanel);

        fileTypesPanel.setLayout(new GridLayout(1, 2, 10, 10));
        fileTypesPanel.add(fileTypes);
        fileTypesPanel.add(fileTypesField);
        assignmentInfoPanel.add(fileTypesPanel);

        assignmentDescriptionPanel.setLayout(new GridLayout(1, 1, 10, 10));
        assignmentDescriptionPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        descriptionArea.setText(cCourse.getAssignmentsList().get(0).getAssignmentDescription());
        descriptionArea.setMargin(new Insets(20, 20, 20, 20));
        assignmentDescriptionPanel.add(descriptionScroll);

        headerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        headerPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        headerPanel.add(assignmentNameButtonPanel);
        headerPanel.add(assignmentInfoPanel);
        mainPanel.add(headerPanel);
        mainPanel.add(assignmentDescriptionPanel);
        mainPanel.add(assignmentGetSubmissionsPanel);

        viewSubmissionsPanel.setLayout(new GridLayout(2, 1, 20, 20));
        viewSubmissionsPanel.setBorder(new EmptyBorder(30, 50, 30, 50));

        JButton viewSubmissions = new JButton("View Submissions");

        viewSubmissions.addActionListener( e -> {
                if(e.getSource() == viewSubmissions) {
                    new SubmissionViewer(currentAssignment);
                }
        });

        viewSubmissions.setBackground(Color.gray);
        viewSubmissions.setForeground(Color.white);
        viewSubmissions.setFocusable(false);
        viewSubmissionsPanel.add(submissionsCount);
        viewSubmissionsPanel.add(viewSubmissions);
  
        
        assignmentGetSubmissionsPanel.add(viewSubmissionsPanel);
        
        c.add(BorderLayout.CENTER, mainPanel);
        //c.add(BorderLayout.CENTER, main.jPanel1);


        ////////////////////////////////////////////////////////////////////////

        
   
        assignmentsListPanel.setBackground(Color.WHITE);
        
        //commented out gridlayout because it was giving us a problem.
        //when we added more assignments, it would switch to two columns.
        //numberOfAssignments*110+220 gives enough room for all asignment buttons plus extra for added ones.
        //this isn't a permanant fix
        //assignmentsListPanel.setPreferredSize(new Dimension(282, numberOfAssignments*110+440));
        assignmentsListPanel.setLayout(new GridLayout(numberOfAssignments + 1, 1));    


        newAssignmentButton.setFocusable(false);
        newAssignmentButton.setBackground(Color.lightGray);
        newAssignmentButton.addActionListener(e -> {
            if(e.getSource() == newAssignmentButton) {
            		editMode();                    
                    clearText();
                    status=-1;
                    assignmentNameField.setText("");
                    assignmentNameField.setPreferredSize(new Dimension(400, 50));
                    editAssignment.setText("Update");
            }
        });
        assignmentsListPanel.add(newAssignmentButton);
        buttonsAssignment = new ArrayList<>(numberOfAssignments);

        // init assignment panel
        initAssignmentPanel();
        for(int i = 0; i < numberOfAssignments; i++)
        {
            String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
            String middleTags = "</h3><p>";
            String closingTags = "</p></body></html>";
            String lineBreak = "<br/>";
            
            String assignmentTitle = cCourse.getAssignmentsList().get(i).getAssignmentName();
            String assignmentDueDate = "due: " + cCourse.getAssignmentsList().get(i).getDueDate();
            String assignmentCloseDate = "closing: " + cCourse.getAssignmentsList().get(i).getCloseDate();
            final int a = i;
            buttonsAssignment.add(new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags));
            buttonsAssignment.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            buttonsAssignment.get(i).setPreferredSize(new Dimension(180, 100));
            buttonsAssignment.get(i).setBackground(Color.WHITE);
            buttonsAssignment.get(i).setFocusable(false);
            buttonsAssignment.get(i).addActionListener(e-> {
                if(e.getSource() == buttonsAssignment.get(a)) {
                    for (int k = 0; k < numberOfAssignments; k++) {
                        buttonsAssignment.get(k).setBackground(Color.white);
                    }
                    buttonsAssignment.get(a).setBackground(Color.cyan);

                    currentAssignment = new Assignment(cCourse.getAssignmentsList().get(a).getAssignmentName(), cCourse.getAssignmentsList().get(a).getAssignmentID());

                    status = a;
                    readMode();

                    assignmentName.setText(cCourse.getAssignmentsList().get(a).getAssignmentName());
                    dateAvailableField.setText(cCourse.getAssignmentsList().get(a).getOpenDate()); //cCourse.getAssignmentsList().get(0).getDateAvailable());
                    dateDueField.setText(cCourse.getAssignmentsList().get(a).getDueDate());
                    dateClosingField.setText(cCourse.getAssignmentsList().get(a).getCloseDate());
//                    pointsField.setText(cCourse.getAssignmentsList().get(a).getPoints());
                    latePenaltyField.setText(""+cCourse.getAssignmentsList().get(a).getPercentPenalty());
                    fileTypesField.setText(cCourse.getAssignmentsList().get(a).getSubmissionTypes());
                    descriptionArea.setText(cCourse.getAssignmentsList().get(a).getAssignmentDescription());
                    // the following Text may not be necessary
                    submissionsCount.setText("This assignment currently has " + cCourse.getAssignmentsList().get(a).getSubmissionsList().size() + " submissions.");

                }
            });
            buttonsAssignment.get(0).setBackground(Color.cyan);
            assignmentsListPanel.add(buttonsAssignment.get(i));
        }


        scroller = new JScrollPane(assignmentsListPanel);
        scroller.setPreferredSize(new Dimension(300, 768));
        c.add(BorderLayout.WEST, scroller);

        setVisible(true);

    }

//    // get token from token.dat
//    public String getOAUTH2() {
//        File tFile = new File(FILENAME);
//        StringBuffer content = new StringBuffer();
//        // the length of stream read from file is larger than the content of that file, so have to deal with it
//        Course.getFromFile(tFile, content);
//
//        return content.toString();
//    }

    // reset current course
    public void setCurrentCourse(Course currentCourse) {
        cCourse = currentCourse;
    }

    public void editMode() {  
    	assignmentNameField.setText(assignmentName.getText());
    	assignmentNameField.setVisible(true);
    	assignmentName.setVisible(false);
    	dateAvailableField.setEditable(true);
        dateDueField.setEditable(true);
        dateClosingField.setEditable(true);
        pointsField.setEditable(true);
        latePenaltyField.setEditable(true);
        fileTypesField.setEditable(true);
        fileTypesField.setEditable(true);
        descriptionArea.setEditable(true);
        viewSubmissionsPanel.setVisible(false);
    }
    
    public void clearText() {
    	viewSubmissionsPanel.setVisible(false);
    	assignmentNameField.setText("");
    	dateAvailableField.setText(""); 
        dateDueField.setText("");
        dateClosingField.setText("");
        pointsField.setText("");
        latePenaltyField.setText("");
        fileTypesField.setText("");
        descriptionArea.setText("");
    }
    
    public void readMode() {   
    	if (status>=0&&cCourse.getAssignmentsList().get(status)!=null)
    		assignmentName.setText(cCourse.getAssignmentsList().get(status).getAssignmentName());
    	assignmentName.setVisible(true);
    	assignmentNameField.setVisible(false);
        editAssignment.setText("Edit");
    	dateAvailableField.setEditable(false);
        dateDueField.setEditable(false);
        dateClosingField.setEditable(false);
        pointsField.setEditable(false);
        latePenaltyField.setEditable(false);
        fileTypesField.setEditable(false);
        fileTypesField.setEditable(false);
        descriptionArea.setEditable(false);
        viewSubmissionsPanel.setVisible(true);
    }
    // reset current assignment panel and assignment buttons
    public void resetAPB(Course currentCourse) {
        setCurrentCourse(currentCourse);
        assignmentsListPanel.removeAll();

        setTitle(cCourse.getCourseName());
        currentCourseNameLabel.setText(cCourse.getCourseName());

        // init assignment panel
        initAssignmentPanel();

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
            String assignmentCloseDate = "closing: " + cCourse.getAssignmentsList().get(i).getCloseDate();
            final int a = i;
            buttonsAssignment.add(new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags));
            buttonsAssignment.get(i).setHorizontalAlignment(SwingConstants.LEFT);
            buttonsAssignment.get(i).setPreferredSize(new Dimension(180, 100));
            buttonsAssignment.get(i).setBackground(Color.WHITE);


            buttonsAssignment.get(i).addActionListener(e-> {
                if(e.getSource() == buttonsAssignment.get(a)) {
                    for (int k = 0; k < numAssignments; k++)
                    {
                        buttonsAssignment.get(k).setBackground(Color.white);
                    }
                    buttonsAssignment.get(a).setBackground(Color.cyan);

                    currentAssignment = cCourse.getAssignmentsList().get(a);

                    readMode();

                    status = a;

                    assignmentName.setText(cCourse.getAssignmentsList().get(a).getAssignmentName());
                    dateAvailableField.setText(cCourse.getAssignmentsList().get(a).getOpenDate()); //cCourse.getAssignmentsList().get(0).getDateAvailable());
                    dateDueField.setText(cCourse.getAssignmentsList().get(a).getDueDate());
                    dateClosingField.setText(cCourse.getAssignmentsList().get(a).getCloseDate());
//                    pointsField.setText(cCourse.getAssignmentsList().get(a).getPoints());
                    latePenaltyField.setText(""+cCourse.getAssignmentsList().get(a).getPercentPenalty());
                    fileTypesField.setText(cCourse.getAssignmentsList().get(a).getSubmissionTypes());
                    descriptionArea.setText(cCourse.getAssignmentsList().get(a).getAssignmentDescription());
                    // the following Text may not be necessary
                    submissionsCount.setText("This assignment currently has " + cCourse.getAssignmentsList().get(a).getSubmissionsList().size() + " submissions.");
                }
            });
            buttonsAssignment.get(0).setBackground(Color.cyan);
            assignmentsListPanel.add(buttonsAssignment.get(i));
            setVisible(true);

        }
        assignmentsListPanel.repaint();

    }

    // inner class for switching current course to desired course
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
                                currentCourse = courseList.get(a);
                                this.setVisible(false);
                            }
                        }
                );
            }
            c.add(buttonPanel);

            setVisible(true);
        }
    }

    // init assignment panel
    public void initAssignmentPanel() {
        currentAssignment = cCourse.getAssignmentsList().get(0);
        assignmentName.setText(cCourse.getAssignmentsList().get(0).getAssignmentName());
        dateAvailableField.setText(cCourse.getAssignmentsList().get(0).getOpenDate()); //cCourse.getAssignmentsList().get(0).getDateAvailable());
        dateDueField.setText(cCourse.getAssignmentsList().get(0).getDueDate());
        dateClosingField.setText(cCourse.getAssignmentsList().get(0).getCloseDate());
//        pointsField.setText(cCourse.getAssignmentsList().get(0).getPoints());
        latePenaltyField.setText(""+cCourse.getAssignmentsList().get(0).getPercentPenalty());
        fileTypesField.setText(cCourse.getAssignmentsList().get(0).getSubmissionTypes());
        descriptionArea.setText(cCourse.getAssignmentsList().get(0).getAssignmentDescription());
        submissionsCount.setText("This assignment currently has " + cCourse.getAssignmentsList().get(0).getSubmissionsList().size() + " submissions.");

    }

//Button layout issue    
//    void addAssignmentButton(Assignment e) {
//        String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
//        String middleTags = "</h3><p>";
//        String closingTags = "</p></body></html>";
//        String lineBreak = "<br/>";
//        
//        String assignmentTitle = e.getAssignmentName();
//        String assignmentDueDate = "due: " + e.getDueDate();
//        String assignmentCloseDate = "closes: " + e.getCloseDate();
//        int i = cCourse.getAssignmentsList().size()-1;
//        final int a = i;
//        buttonsAssignment.add(new JButton(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags));
//      //  buttonsAssignment.get(i).setHorizontalAlignment(SwingConstants.LEFT);
//        buttonsAssignment.get(i).setPreferredSize(new Dimension(180, 100));
//        buttonsAssignment.get(i).setBackground(Color.WHITE);
//        buttonsAssignment.get(i).setFocusable(false);
//        buttonsAssignment.get(i).addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            if(e.getSource() == buttonsAssignment.get(a)) {
//                    buttonsAssignment.get(i).setBackground(Color.white);
//                buttonsAssignment.get(i).setBackground(Color.cyan);
//                readMode();
//                status = i;
//                assignmentName.setText(cCourse.getAssignmentsList().get(i).getAssignmentName());
//                dateAvailableField.setText(cCourse.getAssignmentsList().get(i).getOpenDate()); //cCourse.getAssignmentsList().get(0).getDateAvailable());
//                dateDueField.setText(cCourse.getAssignmentsList().get(i).getDueDate());
//                dateClosingField.setText(cCourse.getAssignmentsList().get(i).getCloseDate());
//                pointsField.setText(cCourse.getAssignmentsList().get(i).getPoints());
//                latePenaltyField.setText(""+cCourse.getAssignmentsList().get(i).getPercentPenalty());
//                fileTypesField.setText(cCourse.getAssignmentsList().get(i).getSubmissionTypes());
//                descriptionArea.setText(cCourse.getAssignmentsList().get(i).getAssignmentDescription());
//                submissionsCount.setText("This assignment currently has " + cCourse.getAssignmentsList().get(i).getSubmissionsList().size() + " submissions.");
//
//            }}
//        });
//        assignmentsListPanel.add(buttonsAssignment.get(i));
//    }
    
    void updateAssignmentButton(Assignment e, int index) {
        String openTags = "<html><body><h3 style='padding-top:0px; margin-top:0px'>";
        String middleTags = "</h3><p>";
        String closingTags = "</p></body></html>";
        String lineBreak = "<br/>";        
        String assignmentTitle = e.getAssignmentName();
        String assignmentDueDate = "due: " + e.getDueDate();
        String assignmentCloseDate = "closing: " + e.getCloseDate();
        buttonsAssignment.get(index).setText(openTags + assignmentTitle + middleTags + assignmentDueDate + lineBreak + assignmentCloseDate + closingTags);
    }
}

