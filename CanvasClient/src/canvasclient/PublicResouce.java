package canvasclient;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public abstract class PublicResouce extends JFrame{

    static Course currentCourse;
    static ArrayList<Course> courseList = new ArrayList<Course>();
    static CourseSelector CSelector = new CourseSelector();
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

    JPanel dateAvailablePanel = new JPanel();

    JPanel dateDuePanel = new JPanel();

    JPanel pointsPanel = new JPanel();

    JPanel latePenaltyPanel = new JPanel();

    JPanel fileTypesPanel = new JPanel();

    JTextPane descriptionArea = new JTextPane();

    JPanel viewSubmissionsPanel = new JPanel();

    JLabel submissionsCount = new JLabel();

    JLabel assignmentName = new JLabel();

    JTextField dateAvailableField = new JTextField();

    JTextField dateDueField = new JTextField();

    JTextField dateClosingField = new JTextField();

    JTextField pointsField = new JTextField();

    JTextField latePenaltyField = new JTextField();

    JTextField fileTypesField = new JTextField();

    JScrollPane descriptionScroll = new JScrollPane();

}
