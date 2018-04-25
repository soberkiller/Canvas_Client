/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package canvasclient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MattSorrentino
 */
public class CourseSelector extends JFrame {
    
    ArrayList<JButton> buttonArray = new ArrayList(5);

    public CourseSelector() {
        super("Course Selector");
        this.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createSelector(List<Course> courseList) {
        setSize(200, courseList.size() * 50);

        Container c = getContentPane();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(courseList.size(), 1, 0, 0));
        for (int i = 0; i < courseList.size(); i++) {
            String s = courseList.get(i).getCourseName();
            buttonArray.add(new JButton(s));
            buttonPanel.add(buttonArray.get(i));
            final int a = i;
            buttonArray.get(i).setBackground(Color.white);
            buttonArray.get(i).setFocusable(false);
            buttonArray.get(i).addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            for (int j = 0; j < courseList.size(); j++) {
                                if (buttonArray.get(a).getText() == courseList.get(j).getCourseName()) {
                                    //create a new instance of the program with the selected course
                                    try {
                                        new CanvasClient(courseList.get(j));
                                    }
                                    catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
            );
        }
        c.add(buttonPanel);

        setVisible(true);
    }

}

