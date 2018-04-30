/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author MattSorrentino
 */
public class TokenPrompt extends JFrame{

    private Container c;
    private static final String FILENAME = "token.dat";
    private final Base64.Encoder encoder = Base64.getEncoder();
    
    public TokenPrompt (File tokenFile)
    {
        super("Enter Canvas Token");

        setSize(450, 250);
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
        
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 30, 50, 40));
        /*
        
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
        
       
        c.add(BorderLayout.NORTH, programSettingsPanel);
        
        */
        
        
        JLabel tokenNotice = new JLabel("Please enter a valid Canvas token.");
        tokenNotice.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(tokenNotice);
        
        JTextField tokenField = new JTextField();
        
        
        mainPanel.add(tokenField);
        
        JPanel submitPanel = new JPanel();
        submitPanel.setLayout(new GridLayout(1, 3, 10, 10));
        
        JLabel spacer1 = new JLabel(" ");
        JLabel spacer2 = new JLabel(" ");

        JButton submitToken = new JButton("Save");
        submitToken.setBackground(Color.gray);
        submitToken.setFocusable(false);
        mainPanel.add(submitToken);
        
        submitToken.addActionListener(e -> {
            try {
                tokenFile.createNewFile();
                tokenFile.setReadable(true);
                tokenFile.setWritable(true);
                
                FileOutputStream fos = new FileOutputStream(FILENAME);
                byte[] data = tokenField.getText().getBytes();

                fos.write(encoder.encode(data));
                fos.close();
                new CanvasClient(null);
                setVisible(false);     
            } 
            catch (Exception ee) 
            {
                ee.printStackTrace();
            }
        });
        
        submitPanel.add(spacer1);
        submitPanel.add(submitToken);
        submitPanel.add(spacer2);
        
        mainPanel.add(submitPanel);
        c.add(mainPanel);
        
        
        setVisible(true);
    }

}
  
