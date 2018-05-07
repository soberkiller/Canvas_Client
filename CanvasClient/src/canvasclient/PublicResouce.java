package canvasclient;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;

import java.awt.Color;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
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
    
    public Date isDateFormatValid(String dateString,JTextField Jtext, String Jtextname, StringBuilder errorInformation) 
    {   
    	Jtext.setBackground(Color.white);
    	if (!dateString.isEmpty()) {
    			if (dateString.length()!=10) {
					Jtext.setBackground(Color.red);
					errorInformation.append(Jtextname+" date length is not 10. It should be YYYY-MM-DD\n");
					return null;
    			}
    	    	try {	
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                df.setLenient(false);
				Date date = df.parse(dateString);
	            return date;
			} catch (ParseException e) {				
				Jtext.setBackground(Color.red);
				errorInformation.append(Jtextname+" date format is incorrect. It should be YYYY-MM-DD\n");
				return null;
			}
    	}
    	return null;
    }

    
    public String isDateLogicValid(Date date1,JTextField Jtext1, String Jtextname1,Date date2,JTextField Jtext2, String Jtextname2) 
    {   
   
    	if (date1!=null&&date2!=null&&date1.before(date2)) {
			Jtext1.setBackground(Color.red);
			Jtext2.setBackground(Color.red);
			System.out.println(Jtextname1+" date connot be before "+ Jtextname2+"date.");
    		return Jtextname1+" date connot be beofre "+ Jtextname2+"date.\n";
    	}
    	return "";
    }
    
    public String isNumberValid(String pointString,JTextField Jtext, String Jtextname) {
    	Jtext.setBackground(Color.WHITE);
    	if(!pointString.isEmpty()) {
    		if(pointString.startsWith("0")&&pointString.length()>2&&pointString.charAt(1)!='.') {
    			Jtext.setBackground(Color.RED);
    			return Jtextname+" format is incorrect. Please remove unnecessary \"0\".\n";  
    		};	
    		if(pointString.endsWith(".")||pointString.startsWith(".")) {
    			Jtext.setBackground(Color.RED);
    			return Jtextname+" format is incorrect. Can't start or end with \".\".\n";  
    		};	
    		Pattern pattern=Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
    		                               
    		Matcher match=pattern.matcher(pointString);   
    		if(match.matches()==false){   
    			Jtext.setBackground(Color.RED);
    			return Jtextname+" format is incorrect. It is not number OR more than two digits after dot.\n";   
    		}else{   
    			return "";   
    		} 
    	}
    	return "";
    }
    
    public String isFileTypesValid(String FileTypesString,JTextField Jtext, String Jtextname) {
    	Jtext.setBackground(Color.WHITE);
    	if(!FileTypesString.isEmpty()||FileTypesString.equals("null")) {
    		if(FileTypesString.startsWith(",")||FileTypesString.endsWith(",")) {
    			Jtext.setBackground(Color.RED);
    			return Jtextname+" format is incorrect. Can't start or end with \",\".\n";  
    		};	
    		Pattern pattern=Pattern.compile("^[A-Za-z0-9,]+$"); 
    		Matcher match=pattern.matcher(FileTypesString);   
    		if(match.matches()==false){   
    			Jtext.setBackground(Color.RED);
    			return Jtextname+" format is incorrect. It could include alphabet, digit and comma(for split)";   
    		}else{   
    			return "";   
    		} 
    	}
    	return "";
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
