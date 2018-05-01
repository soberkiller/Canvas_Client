/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

//import canvasclient.Save_Grade;
//import canvasclient.Save_Comment;
import canvasclient.RunPython;
import canvasclient.CompileAndRunCpp;
import canvasclient.CompileAndRunJava;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
//import canvasclient.Submission;
//import canvasclient.ReadFile;
//import canvasclient.SubmissionOfOtherStudents;
//import canvasclient.IntergratedPlagiarismDetector;


/**
 *
 * @author Xiao
 */
public class SubmissionViewer extends JFrame{
   
    private String filename;
    private File file;
    
    private Submission currentSubmission;
    
    public SubmissionViewer(Assignment currentAssignment)
    {
        
        super("Submissions for " + currentAssignment.getAssignmentName());
        /*
        super(submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getParentFile().getName()
        +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getName());
        */
        
        
        
        File a = new File("/Desktop/Servlet.java");
        currentAssignment.getSubmissionsList().add(new Submission(new Student("First Last", "12345678", "test@stevens.edu")));
        currentAssignment.getSubmissionsList().get(0).getAttachedFiles().add(a);
        currentAssignment.getSubmissionsList().get(0).setSubmissionTime("temp time");
        
        
        currentSubmission = currentAssignment.getSubmissionsList().get(0);

        setSize(1024,820);
        this.setLocationRelativeTo(null);
        Container c = getContentPane();
   //     addWindowListener(new MyWindowListener());
        
        try 
        {
           UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        JLabel spacer1 = new JLabel(" ");
        JLabel spacer2 = new JLabel(" ");
        
        JPanel topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(1024, 100));
        topBar.setBackground(Color.WHITE);
        topBar.setLayout(new GridLayout(1, 3, 70, 25));
        
        JPanel assignmentName = new JPanel();
        assignmentName.setBackground(Color.WHITE);
        assignmentName.setLayout(new GridLayout(2, 1, 10, 0));
        assignmentName.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        //final String currentAssignment = "Assignment";
        JLabel currentCourseNameLabel = new JLabel(" " + currentAssignment);
        assignmentName.add(currentCourseNameLabel);
        Font assignmentTitleFont = new Font("Helvetica", Font.BOLD, 16);
        currentCourseNameLabel.setFont(assignmentTitleFont);
        
        JPanel submissionOptionsPanel = new JPanel();
        submissionOptionsPanel.setBackground(Color.WHITE);
        submissionOptionsPanel.setLayout(new GridLayout(1, 2, 10, 0));
        
       /* JButton submissionSettingsButton = new JButton("Settings");
        submissionSettingsButton.setBackground(Color.WHITE);
        submissionOptionsPanel.add(submissionSettingsButton);
        
        assignmentName.add(submissionOptionsPanel);
        
        topBar.add(assignmentName);*/
        
        
        //SubmissionViewer view = new SubmissionViewer(new Submission2());
        //JPanel viewPanel = (JPanel)view.getContentPane();
        //c.add(BorderLayout.CENTER, viewPanel);
        
        //Course currentCourse = new Course();
        int numberOfButtons=20;//new Course().getStudentsList().size();
      //  JPanel submissionsPanel = new JPanel();
        
        JPanel submissionsListPanel = new JPanel(); 
        submissionsListPanel.setBackground(Color.WHITE);

        submissionsListPanel.setLayout(new GridLayout(numberOfButtons+1, 1));

        JButton buttons[]=new JButton[numberOfButtons]; 
        
       /* JButton newSubmissionButton = new JButton("New Submission");
        newSubmissionButton.setBackground(Color.lightGray);
        submissionsListPanel.add(newSubmissionButton);*/
       
        
        
        for(int i=0;i<numberOfButtons;i++){
            buttons[i] = new JButton("Submission "+(i+1));
            buttons[i].setPreferredSize(new Dimension(180, 50));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
           // ArrayList<File> attachFile = new ArrayList<File>(new Submission2().getattachedFiles());
            //String comment = new Submission2().getComments();
            //new SubmissionViewer(new Submission2());
            }
            });
            submissionsListPanel.add(buttons[i]);
        }

     
       JScrollPane scroller = new JScrollPane(submissionsListPanel);
       scroller.setPreferredSize(new Dimension(254, 768));
       c.add(BorderLayout.WEST, scroller);
        
   
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 0, 0));
        
        JPanel infoPanel = new JPanel();
        JPanel dataPanel = new JPanel();
        //JPanel graderPanel = new JPanel();
        JPanel actionPanel = new JPanel();
        
        //infoPanel.setPreferredSize(new Dimension(770,50));
        infoPanel.setLayout(new GridLayout(3, 3, 20, 20));
        infoPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        dataPanel.setLayout(new GridLayout(1, 2, 20, 20));
        dataPanel.setBorder(new EmptyBorder(0, 30, 0, 30));
        
        
        //graderPanel.setBackground(Color.white);
        
        actionPanel.setPreferredSize(new Dimension(770,100));
        actionPanel.setLayout(new GridLayout(2, 1, 20, 20));
        actionPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        
        JLabel studentName = new JLabel(currentSubmission.getStudent().getStudentName());
        JLabel studentID = new JLabel(currentSubmission.getStudent().getStudentID());
        JLabel submissionTime = new JLabel("Submitted at: " + currentSubmission.getSubmissionTime());
        
        JComboBox selectfile = new JComboBox();
        selectfile.addItem("Select File");
        for(int i=0;i<currentAssignment.getSubmissionsList().get(0).getAttachedFiles().size();i++)
        {
            selectfile.addItem(currentAssignment.getSubmissionsList().get(0).getAttachedFiles().get(i).getName());
        }
        
        //JLabel filelb = new JLabel("AttachedFiles");
        JButton run = new JButton("run");
        
        JTextField studentCommentsField = new JTextField(currentSubmission.getStudentComments());
        
        infoPanel.add(studentName);
        infoPanel.add(studentID);
        infoPanel.add(submissionTime);
        //infoPanel.add(filelb);
        infoPanel.add(selectfile);
        infoPanel.add(run);
        infoPanel.add(studentCommentsField);
        infoPanel.add(new JLabel("Code"));
        infoPanel.add(new JLabel(" "));
        infoPanel.add(new JLabel("Output"));
        
        
        JPanel gradeandCommentsPanel = new JPanel();
        JPanel actionOptionsPanel = new JPanel();
        actionOptionsPanel.setLayout(new GridLayout(1, 3, 10, 10));
        
        JLabel gradeLabel = new JLabel("Grade");
        JLabel commentsLabel = new JLabel("Comments");
        
        String grade = Double.toString(currentSubmission.getSubmissionGrade());
        JTextField gradeField = new JTextField(grade);
        gradeField.setPreferredSize(new Dimension(50, 30));
        JTextField commentsField = new JTextField(currentSubmission.getGraderComments());
        commentsField.setPreferredSize(new Dimension(400, 30));
        
        gradeandCommentsPanel.add(gradeLabel);
        gradeandCommentsPanel.add(gradeField);
        gradeandCommentsPanel.add(new JLabel("           "));
        gradeandCommentsPanel.add(commentsLabel);
        gradeandCommentsPanel.add(commentsField);
        
        
        JButton setExpectedResult = new JButton("Set Expected Result");
        setExpectedResult.setBackground(Color.white);
        setExpectedResult.setFocusable(false);
        actionOptionsPanel.add(setExpectedResult);
        
        JButton save = new JButton("Save");
        save.setBackground(Color.cyan);
        save.setFocusable(false);
        actionOptionsPanel.add(save);
        
        JButton nextSubmission = new JButton("Next Submission");
        nextSubmission.setBackground(Color.white);
        nextSubmission.setFocusable(false);
        actionOptionsPanel.add(nextSubmission);
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1, 10, 10));
        leftPanel.setPreferredSize(new Dimension(768,678));
        SpringLayout layout = new SpringLayout();
        //JPanel rightPanel = new JPanel(layout);
        //rightPanel.setPreferredSize(new Dimension(168,678));
        //graderPanel.add(BorderLayout.WEST,leftPanel);
        //graderPanel.add(BorderLayout.EAST,rightPanel);
        
        
        
        //JLabel codelb = new JLabel("Code");
        //codelb.setPreferredSize(new Dimension(550,20));
        //JLabel resultlb = new JLabel("Result");
        //resultlb.setPreferredSize(new Dimension(550,20));
        JTextArea codeta = new JTextArea();
        JTextArea resultta = new JTextArea();
        
        JScrollPane jspcode=new JScrollPane(codeta);
        jspcode.setPreferredSize(new Dimension(350,250));
        JScrollPane jspresult=new JScrollPane(resultta);
        jspresult.setPreferredSize(new Dimension(350,250));
        
        
        //dataPanel.add(codelb);
        dataPanel.add(jspcode);
        //dataPanel.add(resultlb);
        dataPanel.add(jspresult);
        
        
        
        JButton savegrade = new JButton("Save Grade");
        savegrade.setPreferredSize(new Dimension(100,20));
        JButton savecomment = new JButton("Save Comment");
        savecomment.setPreferredSize(new Dimension(120,20));
        JTextField gradetf = new JTextField();
        gradetf.setPreferredSize(new Dimension(50,30));
        JTextField commenttf = new JTextField();
        commenttf.setPreferredSize(new Dimension(110,30));
        JLabel gradelb = new JLabel("Grade");
        gradelb.setPreferredSize(new Dimension(150,20));
        JLabel commentlb = new JLabel("Comment");
        commentlb.setPreferredSize(new Dimension(150,20));
        //leftPanel.add(run);
        //graderPanel.add(gradelb);
        //graderPanel.add(gradetf);
        //graderPanel.add(savegrade);
        //graderPanel.add(commentlb);
        //graderPanel.add(commenttf);
        //graderPanel.add(savecomment);
        
        SpringLayout.Constraints runCons = layout.getConstraints(run);
        runCons.setX(Spring.constant(59));
        runCons.setY(Spring.constant(34));
        SpringLayout.Constraints gradelbCons = layout.getConstraints(gradelb);
        gradelbCons.setX(Spring.constant(9));
        gradelbCons.setY(Spring.constant(140));
        SpringLayout.Constraints gradetfCons = layout.getConstraints(gradetf);
        gradetfCons.setX(Spring.constant(59));
        gradetfCons.setY(Spring.constant(180));
        SpringLayout.Constraints savegradeCons = layout.getConstraints(savegrade);
        savegradeCons.setX(Spring.constant(34));
        savegradeCons.setY(Spring.constant(230));
        SpringLayout.Constraints commentlbCons = layout.getConstraints(commentlb);
        commentlbCons.setX(Spring.constant(9));
        commentlbCons.setY(Spring.constant(430));
        SpringLayout.Constraints commenttfCons = layout.getConstraints(commenttf);
        commenttfCons.setX(Spring.constant(29));
        commenttfCons.setY(Spring.constant(470));
        SpringLayout.Constraints savecommentCons = layout.getConstraints(savecomment);
        savecommentCons.setX(Spring.constant(24));
        savecommentCons.setY(Spring.constant(520));
        
        
        selectfile.addItemListener(
            new ItemListener() 
            {
                public void itemStateChanged (ItemEvent e1)
                {
                    if(e1.getStateChange() == ItemEvent.SELECTED)
                    {
                        codeta.setText("");
                        resultta.setText("");
                        gradetf.setText("");
                        commenttf.setText("");
                        filename = e1.getItem().toString();
                        file = new File(currentAssignment.getSubmissionsList().get(0).getAttachedFiles().get(0).getParent()+"/"+filename);
                        ReadFile rf = new ReadFile(file);
                        for(int i=0;i<rf.getFileDetail().size();i++){
                            codeta.setText(codeta.getText()+rf.getFileDetail().get(i));
                            codeta.append("\n");
                        }
                    }  
                }
                
            }    
        );
        
        String path = currentAssignment.getSubmissionsList().get(0).getAttachedFiles().get(0).getParent();
        
        savegrade.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed (ActionEvent e1)
                {
                    double gra = Double.valueOf(gradetf.getText());
                    currentSubmission.setSubmissionGrade(gra);
                    try
                    {
                    Save_Grade sg= new Save_Grade(gra,path);
                    }
                    catch(IOException e2)
                    {
                    }
                }
                
            }    
        );
        
        savecomment.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed (ActionEvent e1)
                {
                    String com = commenttf.getText();
                    currentSubmission.setGraderComments(com);
                    try
                    {
                    Save_Comment sc= new Save_Comment(com,path);
                    }
                    catch(IOException e2)
                    {
                    }
                }
                
            }    
        );
        
        run.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed (ActionEvent e1)
                {
                    resultta.setText("");
                    try
                    {
                    if(filename.endsWith(".java")){
                        CompileAndRunJava cj = new CompileAndRunJava(file);
                        gradetf.setText(""+cj.getPregrade());
                        for(int i=0;i<cj.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cj.getResult().get(i));
                            resultta.append("\n");
                        }
                    }
                    else if(filename.endsWith(".cpp")){
                        CompileAndRunCpp cc = new CompileAndRunCpp(file);
                        gradetf.setText(""+cc.getPregrade());
                        for(int i=0;i<cc.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cc.getResult().get(i));
                            resultta.append("\n");
                        }
                    }
                    else if(filename.endsWith(".py")){
                        RunPython cp = new RunPython(file);
                        gradetf.setText(""+cp.getPregrade());
                        for(int i=0;i<cp.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cp.getResult().get(i));
                            resultta.append("\n");
                        }
                    }
                    }
                    catch(Exception e2)
                    {
                    }
                    
                    SubmissionOfOtherStudents subofothers = new SubmissionOfOtherStudents(file);
                    ArrayList<File> fileofotherstudents = subofothers.getSubmissionOfOtherStudents();
                    String type = "";
                    if(filename.endsWith(".java")){
                        type = ".java";
                    }
                    else if(filename.endsWith(".cpp")){
                        type = ".cpp";
                    }
                    else if(filename.endsWith(".py")){
                        type = ".py";
                    }
                    if(type.equals(".java")||type.equals(".cpp")||type.equals(".py")){
                        IntergratedPlagiarismDetector detector = 
                                new IntergratedPlagiarismDetector(file,fileofotherstudents,type);
                        if(detector.getSuspectedFile().isEmpty()){
                            resultta.append("\n");
                            resultta.append("\n");
                            resultta.setText(resultta.getText()+"No Plagiarism Detected");
                        }
                        else{
                            resultta.append("\n");
                            resultta.append("\n");
                            resultta.setText(resultta.getText()+"WARNING: Plagiarism Detected!");
                            resultta.append("\n");
                            resultta.append("\n");
                            resultta.setText(resultta.getText()+"Suspected File:");
                            resultta.append("\n");
                            resultta.append("\n");
                            for(File f:detector.getSuspectedFile()){
                                resultta.setText(resultta.getText()+f);
                                resultta.append("\n");
                                resultta.append("\n");
                            }
                        }
                    }
                }
                
            }    
        );
        
        
        actionPanel.add(gradeandCommentsPanel);
        actionPanel.add(actionOptionsPanel);
        
        
        mainPanel.add(infoPanel);
        mainPanel.add(dataPanel);
        //mainPanel.add(graderPanel);
        mainPanel.add(actionPanel);
        
        c.add(mainPanel);
        
        setVisible(true);
    }
    
    
   /* public static void main(String args[]){
      new SubmissionViewer(new Submission2());
    }*/

   // set Viewer visible
    
}
