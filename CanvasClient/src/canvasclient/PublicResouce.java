package canvasclient;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

public abstract class PublicResouce extends JFrame{

    // set a hashmap for mapping id to student information
    static Map<String, Student> id_user_info = new HashMap<String, Student>();
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

    // for notification sending
    public static String username;
    public static String password;
    public static String dest;

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

    public void sendLateNotification() {
        String host = "smtp.stevens.edu";

        Properties props = new Properties();
        props.put("mail.smtp.port", "587");
        props.put("mail.debug", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.EnableSSL.enable","true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.trust", host);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Professor"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(dest));
            message.setSubject("Late Submission");
            message.setText("Hi Mr. L,"
                    + "\n\n This mail is to notify you that I still haven't get you submission. Please submit it ASAP!");
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
