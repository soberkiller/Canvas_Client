/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static javax.swing.text.html.HTML.Tag.HEAD;

/**
 *
 * @author MattSorrentino
 */
public class SettingsPane extends PublicResouce
{
    private Container c;
    private static final String FILENAME = "token.dat";
    private final Base64.Encoder encoder = Base64.getEncoder();
    
    public SettingsPane (Course currentCourse)
    {
        setTitle("Settings");

        setSize(420, 380);
        this.setLocationRelativeTo(null);
        c = getContentPane();
               
        try 
        {
           UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        
        JPanel programSettingsPanel = new JPanel();
        programSettingsPanel.setLayout(new GridLayout(4, 2, 10, 10));
        programSettingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        
        JLabel tokenLabel = new JLabel("Token");
        JTextField tokenField = new JTextField();
        tokenField.addActionListener(e -> {
            if(e.getSource() == tokenField) {

                try {
                    FileOutputStream fos = new FileOutputStream(FILENAME);
                    byte[] data = tokenField.getText().getBytes();

                    fos.write(encoder.encode(data));
                    fos.close();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

                if(tokenField.getText() != "") {
//                    System.out.println(tokenField.getText());
                    tokenField.setText("");

                    if(tokenField.getText() != "") {
//                        System.out.println(tokenField.getText());
                        tokenField.setText("");
                    }
                }
            }
        });
        programSettingsPanel.add(tokenLabel);
        programSettingsPanel.add(tokenField);
        
        JLabel importDatesLabel = new JLabel("Import Assignment Dates");
        JButton importDatesButton = new JButton("Select File (.txt)");
        importDatesButton.setBackground(Color.white);
        importDatesButton.setFocusable(false);

        programSettingsPanel.add(importDatesLabel);
        programSettingsPanel.add(importDatesButton);

        // input for user Email and Password
        JLabel emailLabel = new JLabel("Email Address");
        JTextField addressField = new JTextField();
        JLabel passwordLabel = new JLabel("Password");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setEchoChar('*');

        // action Listener
        addressField.addActionListener(e->{
            if(e.getSource() == addressField) {
                if(addressField.getText().equals("") || passwordField.equals("")) {
                    JOptionPane.showMessageDialog(addressField,
                            "User Email or Password is missing!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    addressField.grabFocus();
                    return;
                }
                if(!addressField.getText().contains("@") || !(addressField.getText().endsWith(".edu") ||
                                                            addressField.getText().endsWith(".com") ||
                                                            addressField.getText().endsWith(".org"))) {
                    JOptionPane.showMessageDialog(addressField,
                            "User Email is invalid!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    addressField.grabFocus();
                    return;
                }
                passwordField.grabFocus();
            }
        });

        passwordField.addActionListener(e->{
            if(e.getSource() == passwordField) {
                if(addressField.getText().equals("") || passwordField.getText().equals("")) {
                    JOptionPane.showMessageDialog(addressField,
                            "User Email or Password is missing!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    passwordField.grabFocus();
                    return;
                }
                username = "";
                username = addressField.getText();
                password = "";
                password = passwordField.getText();
                passwordField.setText("");
            }
        });

        programSettingsPanel.add(emailLabel);
        programSettingsPanel.add(addressField);
        programSettingsPanel.add(passwordLabel);
        programSettingsPanel.add(passwordField);

        c.add(BorderLayout.NORTH, programSettingsPanel);
        
        
        JPanel courseSettingsPanel = new JPanel();
        c.add(courseSettingsPanel);
        
        setVisible(true);
    }

}
