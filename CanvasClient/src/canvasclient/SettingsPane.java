/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.*;
import java.util.*;
import javax.swing.*;

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
        JPanel courseSettingsPanel = new JPanel();
        
        setVisible(true);
    }
        
}
