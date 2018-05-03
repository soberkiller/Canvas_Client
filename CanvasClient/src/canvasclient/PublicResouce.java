package canvasclient;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public abstract class PublicResouce extends JFrame{

    // set a hashmap for mapping id to student information
    public Map<String, Student> id_user_info = new HashMap<String, Student>();
    static Student currentStu;

    static Course currentCourse;
    static ArrayList<Course> courseList = new ArrayList<Course>();
    static CourseSelector CSelector = new CourseSelector();
    static Assignment currentAssignment;
    // for connection to Canvas
    static List<String> fields = new ArrayList<String>();
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String POST = "POST";

    // for Token
    public final Base64.Decoder decoder = Base64.getDecoder();
    public static final String FILENAME = "token.dat";

    // for assignment window
    public  JPanel mainPanel = new JPanel();

    public JPanel headerPanel = new JPanel();
    public JPanel assignmentNameButtonPanel = new JPanel();
    public JPanel assignmentNamePanel = new JPanel();
    public JPanel assignmentInfoPanel = new JPanel();
    public JPanel assignmentDescriptionPanel = new JPanel();
    public JPanel assignmentGetSubmissionsPanel = new JPanel();


    JLabel dateAvailable = new JLabel("Available");
    JLabel dateDue = new JLabel("Due");
    JLabel dateClosing = new JLabel("Closing");
    JLabel points = new JLabel("Points");
    JLabel latePenalty = new JLabel("Late Penalty");
    JLabel fileTypes = new JLabel("File Types");
    JButton editAssignment = new JButton("Edit");
    JPanel dateAvailablePanel = new JPanel();

    JPanel dateDuePanel = new JPanel();

    JPanel pointsPanel = new JPanel();

    JPanel latePenaltyPanel = new JPanel();

    JPanel fileTypesPanel = new JPanel();

    JTextPane descriptionArea = new JTextPane();

    JPanel viewSubmissionsPanel = new JPanel();

    JLabel submissionsCount = new JLabel();

    JLabel assignmentName = new JLabel();
    
    JTextField assignmentNameField = new JTextField();

    JTextField dateAvailableField = new JTextField();

    JTextField dateDueField = new JTextField();

    JTextField dateClosingField = new JTextField();

    JTextField pointsField = new JTextField();

    JTextField latePenaltyField = new JTextField();

    JTextField fileTypesField = new JTextField();

    JScrollPane descriptionScroll = new JScrollPane();


    // public methods

    //Convert unicode to String
    public String unicode2String(String unicode) {
        StringBuffer string = new StringBuffer();
        Pattern pattern1 = Pattern.compile("[\\\\][u][0-9abcde]{4}$");
        for (int i=0; i<unicode.length(); i++) {
            String out=""+unicode.charAt(i);
            if (i+6<unicode.length()&&pattern1.matcher(unicode.substring(i, i+6)).matches()) {
                out="";
                for (int j =i+2;j<i+6;j++) {
                    out=out+unicode.charAt(j);
                }
                int hexVal = Integer.parseInt(out, 16);
                out =""+ (char)hexVal;
                i=i+5;
            }
            string.append(out);
        }
        String result = string.toString();
        result = result.replace("\\r\\n","");
        return result;
    }


    public String getOAUTH2() {
        File tFile = new File(FILENAME);
        StringBuffer content = new StringBuffer();
        // the length of stream read from file is larger than the content of that file, so have to deal with it
        Course.getFromFile(tFile, content);

        return content.toString();
    }

}
