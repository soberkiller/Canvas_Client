package com.killer.connecting;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class appDemo extends JFrame implements ListSelectionListener, ActionListener {
    int WIDTH = 1000;
    int HEIGHT = 500;

    private  String[] tList;        // content of List
    private JList<String> list;
    private DefaultListModel listModel;
    private JComboBox combBox;
    private static final String getData = "Connect";
    private JButton getButton;
    private String[] courseName = {
            "<----------------------------->",
    };
//=======================================================================================
    private Map<String, List<String>> endpoints = new HashMap<String, List<String>>();
    private Map<String, String> course_id_name = new HashMap<String, String>();

    private Map<String, List<String>> user_info = new HashMap<String, List<String>>();
//=======================================================================================
    private String[] field = {
            "courses"
    };
    String url = "https://canvas.instructure.com/api/v1";
    ConnectionPool connection = new ConnectionPool(field, url, 0.1);

    public appDemo() {

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
        list.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScrollPane = new JScrollPane(list);
//------------ button ----------------------------
        getButton = new JButton(getData);
        getButton.addActionListener(new ConnectListener());
        buttonPanel.add(getButton);
//------------ comb Box ----------------------------
        combBox = new JComboBox(courseName);
        combBox.setEditable(false);
        combBox.addActionListener(this);
        combBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(combBox);
        Panel.add(listScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.PAGE_END);
        add(Panel, BorderLayout.CENTER);
        setVisible(true);

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                return;

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
        if (course == "<----------------------------->")
            return;
        String fields[] = {
                "courses",
                course_id_name.get(course),                 // haspmap find
                "students"
        };
        connection.setMethod("GET");                        // set method
        connection.setAPI(fields);                           // set endpoint
        String response = connection.buildConnection();     // connect to server
        String[] rawResp = response.split(",");       // get response
        user_info.clear();
        List<String> strID = new ArrayList<String>();
        List<String> strName = new ArrayList<String>();
        List<String> strId = new ArrayList<String>();
        List<String> strEmail = new ArrayList<String>();
        if(rawResp != null) {
            for (String s : rawResp) {
                System.out.println(s);
                if (s.startsWith("{"))
                    s = s.substring(1);
                if (s.charAt(s.length() - 1) == '}')
                    s = s.substring(0, s.length() - 1);
                if (s.startsWith("[{"))
                    s = s.substring(2);
                if (s.charAt(s.length() - 2) == ']') {
                    s = s.substring(0, s.length() - 3);
                }
                if(s.startsWith("\"login")) {
                    System.out.println(s.length());
                }
                if(s.startsWith("\"id\"")) {
                    strID.add(s.substring(5));
                }
                if(s.startsWith("\"name\"")) {
                    strName.add(s.substring(8, s.length() - 1));
                }
                if(s.startsWith("\"sis")) {
                    strId.add(s.substring(15, s.length() - 1));
                }
                if(s.startsWith("\"login")) {
                    strEmail.add(s.substring(12, s.length() - 1));
                }
            }
            if(!strID.isEmpty()) {
                for (int i = 0; i < strID.size(); i++) {
                    List<String> comb = new ArrayList<String>();
                    if(!strId.isEmpty())
                        comb.add(strId.get(i));
                    if(!strName.isEmpty())
                        comb.add(strName.get(i));
                    if(!strEmail.isEmpty())
                        comb.add(strEmail.get(i));
                    user_info.put(strID.get(i), comb);
                }
            }
            listModel.removeAllElements();
            for(Map.Entry<String, List<String>> entry: user_info.entrySet()) {
                String key = entry.getKey();
                String dis = "";
                List<String> comb;
                comb = entry.getValue();
                for(String s : comb) {
                    dis += s + "           ";
                }

                listModel.addElement(dis);
            }
        }
    }
// get course name
    class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            connection.setMethod("GET");
            String response = connection.buildConnection();
            String[] rawResp = response.split(",");
            List<String> strID = new ArrayList<String>();
            List<String> strName = new ArrayList<String>();
            if(rawResp != null)
                for (String s : rawResp) {
                    if(s.startsWith("{"))
                        s = s.substring(1);
                    if(s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if(s.startsWith("[{"))
                        s = s.substring(2);
                    if(s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);
                    if(s.startsWith("\"id\"")) {
                        strID.add(s.substring(5));
                    }
                    if(s.startsWith("\"name\"")) {
                        strName.add(s.substring(8, s.length() - 1));
                    }
                }
                if(!strID.isEmpty() && !strName.isEmpty()) {
                    for (int i = 0; i < strID.size(); i++) {
                        course_id_name.put(strName.get(i), strID.get(i));
                    }
                }
                endpoints.put("id", strID);
                endpoints.put("name", strName);
//                for(Map.Entry<String, List<String>> entry : endpoints.entrySet()) {
//                    String key = entry.getKey();
//                    List<String> vals = entry.getValue();
//                    System.out.println(vals.get(0));
//                }
                for(Map.Entry<String, String> entry : course_id_name.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();
                    combBox.addItem(key);
                    System.out.println(key + ":" + val);
                }
        }
    }

    public void setEndpoints(String a, List<String> b) {
        endpoints.put(a, b);
    }

    public void setCourseName(String[] c) {
        for(int i = 0; i < c.length; i++)
            courseName[i] = c[i];
    }
}
