/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author MattSorrentino
 */
public class SettingsPane extends JFrame
{
    private Container c;
    
    
    public SettingsPane (Course currentCourse)
    {
        super("Settings");

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
        programSettingsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        programSettingsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        
        JLabel tokenLabel = new JLabel("Token");
        JTextField tokenField = new JTextField();
        programSettingsPanel.add(tokenLabel);
        programSettingsPanel.add(tokenField);
        
        JLabel importDatesLabel = new JLabel("Import Assignment Dates");
        JButton importDatesButton = new JButton("Select File (.txt)");
        importDatesButton.setBackground(Color.white);
        importDatesButton.setFocusable(false);
        programSettingsPanel.add(importDatesLabel);
        programSettingsPanel.add(importDatesButton);
        
        
        c.add(BorderLayout.NORTH, programSettingsPanel);
        
        
        JPanel courseSettingsPanel = new JPanel();
        c.add(courseSettingsPanel);
        
        setVisible(true);
    }
        
}
