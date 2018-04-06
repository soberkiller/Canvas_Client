package com.killer.connecting;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

public class appDemo extends JFrame implements ListSelectionListener, ActionListener {
    int WIDTH = 1000;
    int HEIGHT = 500;
            ;
    private  String[] tList;        // content of List
    private JList<String> list;
    private DefaultListModel listModel;
    private static final String getData = "Connect";
    private JButton getButton;
    private String[] courseName = {
            "<----------------------------->",
            "DOV DEV"
    };
    private String[] field = {
            "courses"
    };
    private HashMap<String, String> endpoints = new HashMap<String, String>();
    String url = "https://canvas.instructure.com/api/v1";
    ConnectionPool connection = new ConnectionPool(field, url, 0.1);

    public appDemo() {
        endpoints.put("DOV DEV", "10300000000000133");

        setTitle("appDemo--Only for Demonstration");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel Panel= new JPanel();
        JPanel buttonPanel = new JPanel();
        //------------ list----------------------------
        listModel = new DefaultListModel();
        listModel.addElement("<------------------------>");

        list = new JList<String>(listModel);
        list.setSelectedIndex(0);
        list.setPreferredSize(new Dimension(500, 400));
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.addListSelectionListener(this);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JScrollPane listScrollPane = new JScrollPane(list);
//------------ button ----------------------------
        getButton = new JButton(getData);
        getButton.addActionListener(new ConnectListener());
        buttonPanel.add(getButton);
//------------ comb Box ----------------------------
        JComboBox combBox = new JComboBox(courseName);
        combBox.setEditable(false);
        combBox.addActionListener(this);
        combBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(combBox);
        Panel.add(listScrollPane, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.PAGE_END);
        add(Panel, BorderLayout.CENTER);
        setVisible(true);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                getButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                getButton.setEnabled(true);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String course = (String)cb.getSelectedItem();
//        String field[] = {
//                "courses",
//                endpoints.get(course),    // hasp map find
//                "users"
//        };
        connection.setMethod("GET");
        String response = connection.buildConnection();
        String[] rawResp = response.split(",");
        if(rawResp != null)
            for (String s : rawResp)
                System.out.println(s);
    }
// get course name
    class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            System.out.println("Hello world!!");
            connection.setMethod("GET");
            String response = connection.buildConnection();
            String[] rawResp = response.split(",");
            if(rawResp != null)
                for (String s : rawResp)
                    System.out.println(s);
        }
    }

    public void setEndpoints(String a, String b) {
        endpoints.put(a, b);
    }

    public void setCourseName(String[] c) {
        for(int i = 0; i < c.length; i++)
            courseName[i] = c[i];
    }
}
