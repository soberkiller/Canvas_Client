/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author lvtia
 */
public class AllSubmissions extends JFrame{
    public AllSubmissions(){
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
        
        JButton submissionSettingsButton = new JButton("Settings");
        submissionSettingsButton.setBackground(Color.WHITE);
        submissionOptionsPanel.add(submissionSettingsButton);
        
        assignmentName.add(submissionOptionsPanel);
        
        topBar.add(assignmentName);
        
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
        
        submissionDetail subMain = new submissionDetail();
        
        c.add(BorderLayout.CENTER, subMain.jPanel1);
        
        int numberOfButtons=20;
        JPanel submissionsPanel = new JPanel();
        
        JPanel submissionsListPanel = new JPanel(); 
        submissionsListPanel.setBackground(Color.WHITE);

        submissionsListPanel.setLayout(new GridLayout(numberOfButtons+1, 1));

        JButton buttons[]=new JButton[numberOfButtons]; 
        
        JButton newSubmissionButton = new JButton("New Submition");
        newSubmissionButton.setBackground(Color.lightGray);
        submissionsListPanel.add(newSubmissionButton);
        
        for(int i=0;i<numberOfButtons;i++){
            buttons[i] = new JButton("Submition "+i);
            buttons[i].setPreferredSize(new Dimension(180, 50));
            buttons[i].setBackground(Color.WHITE);
            buttons[i].addActionListener(new subListener());
            submissionsListPanel.add(buttons[i]);
        }

     
       JScrollPane scroller = new JScrollPane(submissionsListPanel);
       scroller.setPreferredSize(new Dimension(254, 768));
       c.add(BorderLayout.WEST, scroller);
        
        
        setVisible(true);
    }
    public class subListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
    /* if(e.getSource() == buttons[i]){
        
     }*/
    }
    }
    public static void main(String[] args) {
        AllSubmissions as = new AllSubmissions();
    }
}