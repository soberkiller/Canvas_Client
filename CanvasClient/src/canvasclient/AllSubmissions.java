/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

/**
 *
 * @author Xiao
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
//import canvasclient.Submission2;

/**
 *
 * @author lvtia
 */
public class AllSubmissions extends PublicResouce {
    // set up connection
    public ConnectionPool connection;
    private String responses = "";

    // list of submssion
    List<Submission> submissionList = new ArrayList<>();

    public AllSubmissions() {

        // get submission from Canvas
        if(!fields.isEmpty())
            fields.clear();
        fields.add("courses");
        fields.add(currentCourse.getCourseID());
        fields.add("assignments");
        fields.add(currentAssignment.getAssignmentID());
        fields.add("submissions");

        ConnectionPool submission = null;
        try {
            submission = new ConnectionPool(fields, 0.1,  new String(decoder.decode(getOAUTH2()), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        submission.setMethod(GET);
        getSubmission(submissionList);


        setSize(1024,1000);
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
        
        final String currentAssignment = "Assignment";
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
        
        JPanel logoPanel = new JPanel();
        
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
        
        JPanel majorActionsPanel = new JPanel();
        majorActionsPanel.setBackground(Color.WHITE);
        majorActionsPanel.setLayout(new GridLayout(1, 1, 0, 0));
        majorActionsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        
        c.add(BorderLayout.NORTH, topBar);
        
        SubmissionViewer view = new SubmissionViewer(new Submission2());
        JPanel viewPanel = (JPanel)view.getContentPane();
        c.add(BorderLayout.CENTER, viewPanel);
        
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
                new SubmissionViewer(new Submission2());
            }
            });
            submissionsListPanel.add(buttons[i]);
        }

     
       JScrollPane scroller = new JScrollPane(submissionsListPanel);
       scroller.setPreferredSize(new Dimension(254, 768));
       c.add(BorderLayout.WEST, scroller);
        
        
        setVisible(true);
    }
    public void getSubmission(List<Submission> submissionList) {
        if (responses != "")
            responses = "";
        responses = connection.buildConnection();
        if (responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
                List<String> strGrade = new ArrayList<>();
                List<String> strID = new ArrayList<>();
                List<String> strSubmitTime = new ArrayList<>();
                List<String> strLate = new ArrayList<>();
                List<String> strUrl = new ArrayList<>();
                List<String> strFileName = new ArrayList<>();
                List<List<String>> fileNameList = new ArrayList<>();
                List<List<String>> urlList = new ArrayList<>();

                for (String s : rawResp) {
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.length() > 1 && s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);

                    // get useful information from responses
                    if (s.startsWith("\"grade\"")) {
                        strGrade.add(s.substring(5));
                    }
                    if (s.startsWith("\"submitted_at\"")) {
                        strSubmitTime.add(s.substring(15, s.length() - 1));
                    }
                    if (s.startsWith("\"user_id\"")) {
                        strID.add(s.substring(8, s.length() - 1));
                    }
                    if (s.startsWith("\"late\"")) {
                        strLate.add(s.substring(12, s.length() - 1));
                    }
                    if (s.startsWith("\"filename\"")) {
                        strFileName.add(s.substring(12, s.length() - 1));
                    }
                    if (s.startsWith("\"url\"")) {
                        strUrl.add(s.substring(12, s.length() - 1));
                    }
                }

                fileNameList.add(strFileName);
            }
        }
    }
   /* public static void main(String[] args) {
        AllSubmissions as = new AllSubmissions();
    }*/
}