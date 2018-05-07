/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.EmptyBorder;


/**
 *
 * @author Xiao
 */
public class SubmissionViewer extends PublicResouce{

    // set up connection
    public ConnectionPool submission;
    private String responses = "";

    private String filename;
    private File file;

    private Submission currentSubmission;

    // download object
    private DownloadSubmission ds;
    
    public SubmissionViewer(Assignment currentAssignment)
    {

        // get submission from Canvas
        if(!fields.isEmpty())
            fields.clear();
        fields.add("courses");
        fields.add(currentCourse.getCourseID());
        fields.add("assignments");
        fields.add(currentAssignment.getAssignmentID());
        fields.add("submissions?per_page=100");
        try {
            submission = new ConnectionPool(fields, 0.1,  new String(decoder.decode(getOAUTH2()), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
        submission.setMethod(GET);
        currentAssignment.setSubmissionsList(getSubmission());

        // make directories for submissions, name them by user_id who submitted
        for(Submission s : currentAssignment.getSubmissionsList()) {
            File dir = new File("./" + currentCourse.getCourseID() + "/" + currentAssignment.getAssignmentID() + "/" + s.getStudentId());
            if(!dir.exists())
                dir.mkdir();
        }

        // download files
        // make parent directory if it doesnt exist
        ds = new DownloadSubmission("./" + currentCourse.getCourseID() + "/" + currentAssignment.getAssignmentID());

        // make submission directory if they dont exist and then download files one by one
        for(int i = 0; i < currentAssignment.getSubmissionsList().size(); i++) {
            // Looping by number of files each submission has
            for(int j = 0; j < currentAssignment.getSubmissionsList().get(i).getFileName().size(); j++) {
                if(!currentAssignment.getSubmissionsList().get(i).getFileName().equals("unavailable")) {
                    ds.setAPI(currentAssignment.getSubmissionsList().get(i).getUrlList().get(j));
                    ds.resetFILEPATH("./" + currentCourse.getCourseID() + "/" + currentAssignment.getAssignmentID());
                    ds.setFILEPATH("/" + currentAssignment.getSubmissionsList().get(i).getStudentId() + "/");
                    ds.setDownload(currentAssignment.getSubmissionsList().get(i).getFileName().get(j));
                }
            }
        }


        setTitle("Submissions for " + currentAssignment.getAssignmentName());
        /*
        super(submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getParentFile().getName()

        +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getName());
        */
        
        // add path of file into AttachedFiles List
        for(int i = 0; i < currentAssignment.getSubmissionsList().size(); i++) {
            for(int j = 0; j < currentAssignment.getSubmissionsList().get(i).getFileName().size(); j++) {
                File a = new File("./" + currentCourse.getCourseID()
                                    + "/" + currentAssignment.getAssignmentID()
                                    + "/" + currentAssignment.getSubmissionsList().get(i).getStudentId()
                                    + "/" + currentAssignment.getSubmissionsList().get(i).getFileName().get(j));
                if(!a.exists())
                    continue;
                currentAssignment.getSubmissionsList().get(i).getAttachedFiles().add(a);
            }
        }
        
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
        int numberOfButtons = currentAssignment.getSubmissionsList().size();//new Course().getStudentsList().size();
      //  JPanel submissionsPanel = new JPanel();
        
        JPanel submissionsListPanel = new JPanel(); 
        submissionsListPanel.setBackground(Color.WHITE);

        submissionsListPanel.setLayout(new GridLayout(numberOfButtons+1, 1));

        JButton buttons[]=new JButton[numberOfButtons]; 
        
       /* JButton newSubmissionButton = new JButton("New Submission");
        newSubmissionButton.setBackground(Color.lightGray);
        submissionsListPanel.add(newSubmissionButton);*/

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

        JLabel studentName = new JLabel(id_user_info.get(currentSubmission.getStudentId()).getStudentName());
        JLabel studentID = new JLabel(id_user_info.get(currentSubmission.getStudentId()).getStudentID());
        JLabel submissionTime = new JLabel("Submitted at: " + currentSubmission.getSubmissionTime());


        String grade = currentSubmission.getGrade();
        JTextField gradeField = new JTextField(grade);
        gradeField.setPreferredSize(new Dimension(50, 30));
        JTextField commentsField = new JTextField(currentSubmission.getGraderComments());
        commentsField.setPreferredSize(new Dimension(400, 30));
        
        JComboBox selectfile = new JComboBox();
        selectfile.addItem("Select File");
        for(int i=0;i < currentSubmission.getFileName().size();i++)
        {
            selectfile.addItem(currentSubmission.getFileName().get(i));
        }

        for(int i=0;i<numberOfButtons;i++){
            buttons[i] = new JButton(id_user_info.get(currentAssignment.getSubmissionsList().get(i).getStudentId()).getStudentName());
            buttons[i].setPreferredSize(new Dimension(180, 50));
            buttons[i].setBackground(Color.WHITE);
            int index = i;
            buttons[i].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // ArrayList<File> attachFile = new ArrayList<File>(new Submission2().getattachedFiles());
                    //String comment = new Submission2().getComments();
                    //new SubmissionViewer(new Submission2());
                    if(e.getSource() == buttons[index]) {
                        currentSubmission = currentAssignment.getSubmissionsList().get(index);
                        studentName.setText(id_user_info.get(currentSubmission.getStudentId()).getStudentName());
                        studentID.setText(id_user_info.get(currentSubmission.getStudentId()).getStudentID());
                        submissionTime.setText("Submitted at: " + currentSubmission.getSubmissionTime());
                        gradeField.setText(currentSubmission.getGrade());
                        selectfile.removeAllItems();
                        selectfile.addItem("Select File");
                        for(int i=0;i < currentSubmission.getAttachedFiles().size();i++)
                        {
                            selectfile.addItem(currentSubmission.getAttachedFiles().get(i).getName());
                        }
                    }
                }
            });
            submissionsListPanel.add(buttons[i]);
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
        
        gradeandCommentsPanel.add(gradeLabel);
        gradeandCommentsPanel.add(gradeField);
        gradeandCommentsPanel.add(new JLabel("           "));
        gradeandCommentsPanel.add(commentsLabel);
        gradeandCommentsPanel.add(commentsField);
        
        
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
        
        
        
        
        JButton setExpectedResult = new JButton("Set Expected Result");
        setExpectedResult.setBackground(Color.white);
        setExpectedResult.setFocusable(false);
        actionOptionsPanel.add(setExpectedResult);
        setExpectedResult.addActionListener(e->{
            new SaveExpectedResult(currentSubmission.getAttachedFiles().get(0).getParentFile().getParent());
        });
        
        JButton save = new JButton("Save");
        save.setBackground(Color.cyan);
        save.setFocusable(false);
        actionOptionsPanel.add(save);
        save.addActionListener(e-> {
            if(e.getSource() == save) {
            	gradeField.setBackground(Color.WHITE);
            	commentsField.setBackground(Color.WHITE);
            	StringBuilder validationMessage=new StringBuilder("");
            	if(!gradeField.getText().isEmpty()||!commentsField.getText().isEmpty()) {            		
            		validationMessage.append(isNumberValid(gradeField.getText(),gradeField, gradeLabel.getText()));
            	}
            	System.out.println(validationMessage);
            	if (validationMessage.length()<1) {
                	double value = Double.parseDouble(gradeField.getText());
                	double point;
                	if (currentAssignment.getPoints()!=null&&!currentAssignment.getPoints().isEmpty()) {
                		point = Double.parseDouble(currentAssignment.getPoints());	
                		if (point!=0 && value >point*1.5) {
                			gradeField.setBackground(Color.RED);
                			validationMessage.append("Grade should be less than "+ point*1.5);
                		}
                	} else {
                		if (currentAssignment.getPoints()!=null) {
                		gradeField.setBackground(Color.RED);
                		validationMessage.append("Current assignment doesn't need to grade");
                		}
                	}              		
            	}

            	
            	if (validationMessage.length()>1) {
                	JOptionPane.showMessageDialog(null, validationMessage.toString() , "Error below",JOptionPane.ERROR_MESSAGE); 
                }else {
                	try {
						submission.gradeSubmission(currentCourse.getCourseID(), currentAssignment.getAssignmentID(), studentID.getText(), gradeField.getText(), commentsField.getText());
					} catch (IOException e2) {
						e2.printStackTrace();
					}
                }
            	
            }
        }
        );
        
        
        JButton nextSubmission = new JButton("Next Submission");
        nextSubmission.setBackground(Color.white);
        nextSubmission.setFocusable(false);
        actionOptionsPanel.add(nextSubmission);
        nextSubmission.addActionListener(e-> {
            if(e.getSource() == nextSubmission) {
                
                
                gradeField.setText("");
                commentsField.setText("");
                codeta.setText("");
                resultta.setText("");
                
                int index = currentAssignment.getSubmissionsList().indexOf(currentSubmission);
                if(index + 1 >= currentAssignment.getSubmissionsList().size())
                    index = -1;
                currentSubmission = currentAssignment.getSubmissionsList().get(index + 1);
                studentName.setText(id_user_info.get(currentSubmission.getStudentId()).getStudentName());
                studentID.setText(id_user_info.get(currentSubmission.getStudentId()).getStudentID());
                submissionTime.setText("Submitted at: " + currentSubmission.getSubmissionTime());
                gradeField.setText(currentSubmission.getGrade());
                selectfile.removeAllItems();
                selectfile.addItem("Select File");
                for(int i=0;i < currentSubmission.getAttachedFiles().size();i++)
                {
                    selectfile.addItem(currentSubmission.getAttachedFiles().get(i).getName());
                }
            }
        });
        
        
        
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
                e -> {
                    if(e.getStateChange() == ItemEvent.SELECTED) {
                        String content = (String) selectfile.getSelectedItem();
                        int index = selectfile.getSelectedIndex();
                        if(content.equals("Select File"))
                            return;
                        codeta.setText("");
                        resultta.setText("");
                        //No need to Clear @yyf
                        //gradeField.setText("");
                        //commentsField.setText("");
                        filename = content;
                        file = currentSubmission.getAttachedFiles().get(index-1);
                        ReadFile rf = new ReadFile(file);
                        for(int i=0;i<rf.getFileDetail().size();i++){
                            codeta.setText(codeta.getText()+rf.getFileDetail().get(i));
                            codeta.append("\n");

                    }
                }});

        // how to use the following code
//        String path = "";
//        if(currentAssignment.getSubmissionsList().get(0).getAttachedFiles().get(0).exists()) {
//            path = currentAssignment.getSubmissionsList().get(0).getAttachedFiles().get(0).getParent();
//            System.out.println(path);
//        }
//
//        String finalPath = path;
//        savegrade.addActionListener(
//            new ActionListener()
//            {
//                public void actionPerformed (ActionEvent e1)
//                {
//                    double gra = Double.valueOf(gradetf.getText());
//                    currentSubmission.setSubmissionGrade(gra);
//                    try
//                    {
//                    Save_Grade sg= new Save_Grade(gra, finalPath);
//                    }
//                    catch(IOException e2)
//                    {
//                    }
//                }
//
//            }
//        );
//
//        savecomment.addActionListener(
//            new ActionListener()
//            {
//                public void actionPerformed (ActionEvent e1)
//                {
//                    String com = commenttf.getText();
//                    currentSubmission.setGraderComments(com);
//                    try
//                    {
//                    Save_Comment sc= new Save_Comment(com,finalPath);
//                    }
//                    catch(IOException e2)
//                    {
//                    }
//                }
//
//            }
//        );
        
        run.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed (ActionEvent e1)
                {
                    resultta.setText("");
                    ArrayList<String> erdetail = new ArrayList<>();
                    File erfile = new File(currentSubmission.getAttachedFiles().get(0).getParentFile().getParent()+"/expectedresult.txt");
                    if(erfile.exists()){
                        ReadFile rfer = new ReadFile(erfile);
                        erdetail = rfer.getFileDetail();
                    }
                    try
                    {
                    if(filename.endsWith(".java")){
                        CompileAndRunJava cj = new CompileAndRunJava(file);
                        for(int i=0;i<cj.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cj.getResult().get(i));
                            resultta.append("\n");
                        }
                        double correctline = 0;
                        int grade = 0;
                        if(cj.getPregrade()==100.0){
                            if(!erdetail.isEmpty()){
                                for(int i=0;i<Math.min(erdetail.size(), cj.getResult().size());i++){
                                    if(cj.getResult().get(i).equals(erdetail.get(i))){
                                        correctline += 1;
                                    }
                                }
                                grade = (int)(correctline/erdetail.size()*100.0);
                            }
                            else{
                                grade = 100;
                            }
                        }
                        else{
                            grade = (int)cj.getPregrade();
                        }
                        gradeField.setText(""+grade);
                    }
                    else if(filename.endsWith(".cpp")){
                        CompileAndRunCpp cc = new CompileAndRunCpp(file);
                        for(int i=0;i<cc.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cc.getResult().get(i));
                            resultta.append("\n");
                        }
                        double correctline = 0;
                        int grade = 0;
                        if(cc.getPregrade()==100.0){
                            if(!erdetail.isEmpty()){
                                for(int i=0;i<Math.min(erdetail.size(), cc.getResult().size());i++){
                                    if(cc.getResult().get(i).equals(erdetail.get(i))){
                                        correctline += 1;
                                    }
                                }
                                grade = (int)(correctline/erdetail.size()*100.0);
                            }
                            else{
                                grade = 100;
                            }
                        }
                        else{
                            grade = (int)cc.getPregrade();
                        }
                        gradeField.setText(""+grade);
                    }
                    else if(filename.endsWith(".py")){
                        RunPython cp = new RunPython(file);
                        for(int i=0;i<cp.getResult().size();i++){
                            resultta.setText(resultta.getText()+(String)cp.getResult().get(i));
                            resultta.append("\n");
                        }
                        double correctline = 0;
                        int grade = 0;
                        if(cp.getPregrade()==100.0){
                            if(!erdetail.isEmpty()){
                                for(int i=0;i<Math.min(erdetail.size(), cp.getResult().size());i++){
                                    if(cp.getResult().get(i).equals(erdetail.get(i))){
                                        correctline += 1;
                                    }
                                }
                                grade = (int)(correctline/erdetail.size()*100.0);
                            }
                            else{
                                grade = 100;
                            }
                        }
                        else{
                            grade = (int)cp.getPregrade();
                        }
                        gradeField.setText(""+grade);
                    }
                    }
                    catch(Exception e2)
                    {
                    }
                    
                    if(file!=null){
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

    public ArrayList<Submission> getSubmission () {
        ArrayList<Submission> submissionList = new ArrayList<>();
        if (responses != "")
            responses = "";
        responses = submission.buildConnection();
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
//                    System.out.println(s);
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
                        if(s.substring(8).equals("null")) {
                            strGrade.add(s.substring(8));
                        } else {
                            strGrade.add(s.substring(9, s.length() - 1));
                        }
                    }
                    if (s.startsWith("\"submitted_at\"")) {
                        if(!strFileName.isEmpty()) {
                            fileNameList.add(strFileName);
                            urlList.add(strUrl);
                            strFileName = new ArrayList<>();
                            strUrl = new ArrayList<>();
                        }

                        if(s.substring(15).equals("null")) {
                            strSubmitTime.add(s.substring(15));
                            strFileName.add("unavailable");
                            strUrl.add("unavailable");
                                fileNameList.add(strFileName);
                                urlList.add(strUrl);
                                strFileName = new ArrayList<>();
                                strUrl = new ArrayList<>();
                        } else {
                            strSubmitTime.add(s.substring(16, s.length() - 1));
                        }
                    }
                    if (s.startsWith("\"user_id\"")) {
                        strID.add(s.substring(10));
                    }
                    if (s.startsWith("\"late\"")) {
                        strLate.add(s.substring(7));
                    }
                    if (s.startsWith("\"display_name\"")) {

                        strFileName.add(s.substring(16, s.length() - 1));
                    }
                    if (s.startsWith("\"url\"")) {
                        // filter our url
                        if((s.substring(6)).equals("null"))
                            continue;
                        strUrl.add(s.substring(7, s.length() - 1));
                    }
                }
                // in case filename and url are not added into List by the end of response.
                if(!strFileName.isEmpty()) {
                    fileNameList.add(strFileName);
                    urlList.add(strUrl);
                }
 
                for (int i = 0; i < strID.size(); i++) {
                    submissionList.add(new Submission(strID.get(i), strSubmitTime.get(i), strGrade.get(i),
                                                    strLate.get(i), fileNameList.get(i), urlList.get(i)));
                }
            }
        }

        return submissionList;
    }
    
   /* public static void main(String args[]){
      new SubmissionViewer(new Submission2());
    }*/

   // set Viewer visible
    
}
