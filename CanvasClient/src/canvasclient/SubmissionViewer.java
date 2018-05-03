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
import java.util.ArrayList;
import java.util.List;
//import canvasclient.Submission;
//import canvasclient.ReadFile;
//import canvasclient.SubmissionOfOtherStudents;
//import canvasclient.IntergratedPlagiarismDetector;


/**
 *
 * @author Xiao
 */
public class SubmissionViewer extends PublicResouce{
   
    private String filename;
    private File file;


    public SubmissionViewer(Submission2 submission) {
        // get submissions for current assignment
        // add methods for getting submission

        setTitle(submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getParentFile().getName()
        +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getParentFile().getName()
                +" "+submission.getattachedFiles().get(0).getParentFile().getName());

        setSize(768,668);
        setResizable(false);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        setLocation((int)(width-this.getWidth())/2,(int)(height-this.getHeight())/2);
        Container c = getContentPane();
        
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(600,668));
        SpringLayout layout = new SpringLayout();
        JPanel rightPanel = new JPanel(layout);
        rightPanel.setPreferredSize(new Dimension(168,668));
        c.add(BorderLayout.WEST,leftPanel);
        c.add(BorderLayout.EAST,rightPanel);
        
        
        JComboBox selectfile = new JComboBox();
        selectfile.setPreferredSize(new Dimension(300,30));
        selectfile.addItem("Select File");
        for(int i=0;i<submission.getattachedFiles().size();i++)
        {
            selectfile.addItem(submission.getattachedFiles().get(i).getName());
        }
        JLabel filelb = new JLabel("AttachedFiles");
        filelb.setPreferredSize(new Dimension(550,20));
        JLabel codelb = new JLabel("Code");
        codelb.setPreferredSize(new Dimension(550,20));
        JLabel resultlb = new JLabel("Result");
        resultlb.setPreferredSize(new Dimension(550,20));
        JTextArea codeta = new JTextArea();
        JTextArea resultta = new JTextArea();
        
        JScrollPane jspcode=new JScrollPane(codeta);
        jspcode.setPreferredSize(new Dimension(550,250));
        JScrollPane jspresult=new JScrollPane(resultta);
        jspresult.setPreferredSize(new Dimension(550,250));
        
        leftPanel.add(filelb);
        leftPanel.add(selectfile);
        leftPanel.add(codelb);
        leftPanel.add(jspcode);
        leftPanel.add(resultlb);
        leftPanel.add(jspresult);
        
        
        JButton run = new JButton("run");
        run.setPreferredSize(new Dimension(50,20));
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
        rightPanel.add(run);
        rightPanel.add(gradelb);
        rightPanel.add(gradetf);
        rightPanel.add(savegrade);
        rightPanel.add(commentlb);
        rightPanel.add(commenttf);
        rightPanel.add(savecomment);
        
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
                        file = new File(submission.getattachedFiles().get(0).getParent()+"/"+filename);
                        ReadFile rf = new ReadFile(file);
                        for(int i=0;i<rf.getFileDetail().size();i++){
                            codeta.setText(codeta.getText()+rf.getFileDetail().get(i));
                            codeta.append("\n");
                        }
                    }  
                }
                
            }    
        );
        
        String path =submission.getattachedFiles().get(0).getParent();
        
        savegrade.addActionListener(
            new ActionListener() 
            {
                public void actionPerformed (ActionEvent e1)
                {
                    double gra = Double.valueOf(gradetf.getText());
                    submission.setGrade(gra);
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
                    submission.setComments(com);
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
        
        
        
        setVisible(true);
    }
   /* public static void main(String args[]){
      new SubmissionViewer(new Submission2());
    }*/

   // set Viewer visible
}
