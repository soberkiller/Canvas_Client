/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
/**
 *
 * @author dragon
 */
public class SaveExpectedResult extends JFrame{
    
    public SaveExpectedResult(String path){
        super("Set Expected Result");
        
        setSize(550,300);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        setLocation((int)(width-this.getWidth())/2,(int)(height-this.getHeight())/2);
        
        Container c = this.getContentPane();
        JPanel jp = new JPanel();
        jp.setPreferredSize(new Dimension(550,300));
        c.add(jp);
        JTextArea erta = new JTextArea();
        JScrollPane jsp =new JScrollPane(erta);
        jsp.setPreferredSize(new Dimension(540,240));
        JButton save = new JButton("Save");
        save.setPreferredSize(new Dimension(100,20));
        jp.add(jsp);
        jp.add(save);
        
        File erfile = new File(path+"/expectedresult.txt");
        if(erfile.exists()){
            ReadFile rf = new ReadFile(erfile);
            for(int i=0;i<rf.getFileDetail().size();i++){
                erta.setText(erta.getText()+rf.getFileDetail().get(i));
                erta.append("\n");
            }
        }
        
        save.addActionListener(e->{
            try{
            FileOutputStream fs = new FileOutputStream(new File(path+"/expectedresult.txt"));
            PrintStream p = new PrintStream(fs);
            p.println(erta.getText());
            p.close();}
            catch(IOException e1){
            }
            dispose();
        });
        setVisible(true);
    }
    
}
