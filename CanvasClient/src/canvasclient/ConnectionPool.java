package canvasclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


/**
 * @author Fangming Zhao, Yifang Yuan
 * April. 2018
 * @param updateConnection is for updating connection
 * @param finalConnection is for reading connection
 * @param field is for url generation
 * 
 */

public class ConnectionPool extends PublicResouce {
    private double API_VERSSION = 0;
    private static final String API = "https://sit.instructure.com/api/v1";
    private String url = "";
    private String METHOD = "GET";
    private String TYPE = "application/json";
    private String USER_AGENT = "Mozilla/5.0";
    private String OAUTH2 = "Bearer ";  // Token
    private String data = "";
    private URL connection;
    private HttpURLConnection finalConnection;
    private HttpURLConnection updateConnection;

    private String fields = "";
    public ConnectionPool(List<String> endpoint, double version, String oauth2) {
        this.API_VERSSION = version;
        this.OAUTH2 += oauth2;
        setURL(endpoint);
    }

    public String buildConnection() {
        StringBuilder  content = new StringBuilder();
        if(!this.getEndpoints().equalsIgnoreCase("") && !this.getEndpoints().isEmpty()) {
            try {
                connection = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(readWithAccess(connection, data)));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line + "\n");
                }
                reader.close();
                finalConnection.disconnect();
                return unicode2String(content.toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            return null;
        }
        return null;
    }

    private InputStream  readWithAccess(URL url, String data) {
        try {
            byte[] out = data.getBytes();
            finalConnection = (HttpURLConnection) url.openConnection();
            finalConnection.setRequestMethod(METHOD);
            finalConnection.setDoOutput(false);
            finalConnection.setDoInput(true);
            finalConnection.setRequestProperty("Content-Type", TYPE);
            finalConnection.setRequestProperty("Charset", "UTF-8");
            finalConnection.setRequestProperty("Authorization", OAUTH2);
            finalConnection.setUseCaches(false);
            finalConnection.connect();
            try {
                OutputStream os = finalConnection.getOutputStream();
                os.write(out);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
            return finalConnection.getInputStream();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public String getApiVersion() {
        return String.valueOf(API_VERSSION);
    }

    public String getMethod() {
        return this.METHOD;
    }

    public String getEndpoints() {
        return fields;
    }

    public void setUserAgent(String userAgent) {
        this.USER_AGENT = userAgent;
    }

    public void setMethod(String method) {
        this.METHOD = method;
    }
    public  void setURL(List<String> field) {
        if(fields != "")
            this.fields = "";
        if(url != "")
            url = "";
        url += API;
        for(int i = 0; i < field.size(); i++) {
            fields += '/' + field.get(i);
        }
        url += fields;
    }

    public void setSubmissionType(String type) {
        this.TYPE = type;
    }

    public void setOauth2(String oauth2) {
        this.OAUTH2 = oauth2;
    }

    /**
     * List all courses, but not student role
     * @return 
     * @throws UnsupportedEncodingException
     * @throws ParseException
     */
    public ArrayList<Course> getNotStudentCourses() throws UnsupportedEncodingException, ParseException {
    	//not include role type student and observer 
    	String[] role_type_factory={"teacher","ta","designer"};
    	HashSet<String> recorder_course=new HashSet<>();
    	ArrayList<Course> courseList =new ArrayList<>();
    	String url_backup=url;
    	String responses = "";
        List<String> fields = new ArrayList<>();
        fields.add("");
        //Get API result for every role in role_type_factory
    	for (String role_type:role_type_factory) {
    		fields.set(0, "courses?enrollment_type="+role_type);
    		setURL(fields);
    		responses = this.buildConnection();
    		if (responses != null) {
    			//split by courses
    			String[] rawResp = responses.split("(?=\"id\":)");
                if (rawResp.length > 1) {
                    String t_id="";
                    String t_name="";
                    String t_date="";
                    Date date = null;
                    for (String s1 : rawResp) {
                    	if (s1.startsWith("\"id\"")) {
                    		String[] rawResp2=s1.split(",");
                    		if (rawResp2 == null) {
                    			continue;
                    		}
                    		//get course id,name and start date
                    		for (String s2 : rawResp2) {
                    			if (s2.startsWith("\"id\"")) {
                    				t_id=s2.substring(5);
                    				if(recorder_course.contains(t_id)) {
                    					t_id="";
                    					break;
                    				}
                                }                    			
                    			if (s2.startsWith("\"name\"")) {
                                    t_name=s2.substring(8, s2.length() - 1);
                                }
                    			if (s2.startsWith("\"start_at\"")) {
                    				t_date=s2.substring(12,22);
                    			    date=new SimpleDateFormat("yyyy-MM-dd").parse(t_date);
                    			}
                    			//add to courselist
                                if (!t_id.isEmpty()&&!t_name.isEmpty()&&!t_date.isEmpty()) {
                                	//Course temp_course = new Course(t_name+"--"+t_date,t_id);
                                	Course temp_course = new Course(t_name,t_id);
                                	temp_course.setCourseStartDate(date);                          
                                    courseList.add(temp_course);
                                    recorder_course.add(t_id);
                                    t_id="";
                                	t_name="";
                                	t_date="";
                                	break;
                                }
                    		}
                    	}
                    }                    
                }
    		}
    	}
		url=url_backup;
		if (courseList.size()==0)
			courseList.add(new Course("Unavailable","Unavailable"));
		else
			//Sort newest first
			Collections.sort(courseList,Course.Comparators.DateSort);
		return courseList;
    }  
 
    /**
     * addAssignment function, used by GUI button
     * Process HTML, add default time for date
     * 
     */
    public String addAssignment(String courseId,String name,String startdate,String duedate,String closedate,String points, String FileType, String description) throws MalformedURLException, IOException {
    	List<String> fields = new ArrayList<>();
    	fields.add("courses/"+courseId+"/assignments");
    	String url_backup=url;
    	this.setURL(fields);
    	updateConnection=(HttpURLConnection)((new URL(url)).openConnection());
    	updateConnection.setDoOutput(true);
    	updateConnection.setRequestMethod("POST");
    	updateConnection.setRequestProperty("Content-Type", TYPE);
    	updateConnection.setRequestProperty("Authorization", OAUTH2);
    	StringBuilder inputsb=new StringBuilder(1024);
    	inputsb.append("{\"assignment\": {\"name\":\""+name+"\"");
    	if (!duedate.isEmpty()&&!duedate.equals("null"))
    		inputsb.append(",\"due_at\":\""+duedate+"T04:59:00Z\"");	
    	if (!startdate.isEmpty()&&!startdate.equals("null"))
    		inputsb.append(",\"unlock_at\":\""+startdate+"T04:59:00Z\"");	
    	if (!closedate.isEmpty()&&!closedate.equals("null"))
    		inputsb.append(",\"lock_at\":\""+closedate+"T04:59:00Z\"");	
    	if (!points.isEmpty()&&!points.equals("null"))
    		inputsb.append(",\"grading_type\":\"points\",\"points_possible\":"+points);	
    	if (!FileType.isEmpty()&&!FileType.equals("null"))
    		inputsb.append(",\"submission_types\":[\"online_upload\"],\"allowed_extensions\":[\""+FileType.replaceAll(",", "\",\"")+"\"]");	
    	if (description.length()>59) {
    		description=description.substring(40,description.length()-18);
    		if (description.endsWith("<br>\n")) {
    			description=description.substring(4,description.length()-1);
    		}
    		inputsb.append(",\"description\":\""+description.replace("<br>","\n").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "").replace("\n","<br>").replace("\\","").replaceAll(" ","&nbsp;")+"\"");
    		}
    	inputsb.append(",\"published\":true}}");
    	String input=inputsb.toString();
    	OutputStream os = updateConnection.getOutputStream();
    	os.write(input.getBytes());
    	os.flush();    	
    	BufferedReader br = new BufferedReader(new InputStreamReader((updateConnection.getInputStream())));	    	
    	input = br.readLine();
    	updateConnection.disconnect();
    	url=url_backup;
		if(input.length()>=7) {	
			return input.substring(6,input.indexOf(",\""));
		}  
		return "";
    	
    }
    
    /**
     * updateAssignment function, used by GUI button
     * Process HTML, add default time for date
     */
    public void updateAssignment(String courseId,String assignmentId,String name,String startdate,String duedate,String closedate,String points, String FileType, String description) throws MalformedURLException, IOException {
    	List<String> fields = new ArrayList<>();
    	fields.add("courses/"+courseId+"/assignments/"+assignmentId);
    	String url_backup=url;
    	this.setURL(fields);
    	updateConnection=(HttpURLConnection)((new URL(url)).openConnection());
    	updateConnection.setDoOutput(true);
    	updateConnection.setRequestMethod("PUT");
    	updateConnection.setRequestProperty("Content-Type", TYPE);
    	updateConnection.setRequestProperty("Authorization", OAUTH2);
    	StringBuilder inputsb=new StringBuilder(1024);
    	inputsb.append("{\"assignment\": {\"name\":\""+name+"\"");
    	if (!duedate.isEmpty()&&!duedate.equals("null"))
    		inputsb.append(",\"due_at\":\""+duedate+"T04:59:00Z\"");	
    	if (!startdate.isEmpty()&&!startdate.equals("null"))
    		inputsb.append(",\"unlock_at\":\""+startdate+"T04:59:00Z\"");	
    	if (!closedate.isEmpty()&&!closedate.equals("null"))
    		inputsb.append(",\"lock_at\":\""+closedate+"T04:59:00Z\"");	
    	if (!points.isEmpty()&&!points.equals("null"))
    		inputsb.append(",\"grading_type\":\"points\",\"points_possible\":"+points);	
    	if (!FileType.isEmpty()&&!FileType.equals("null"))
    		inputsb.append(",\"submission_types\":[\"online_upload\"],\"allowed_extensions\":[\""+FileType.replaceAll(",", "\",\"")+"\"]");	
    	if (description.length()>59) {
    		description=description.substring(40,description.length()-18);
    		if (description.endsWith("<br>\n")) {
    			description=description.substring(4,description.length()-1);
    		}
    		inputsb.append(",\"description\":\""+description.replace("<br>","\n").replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", "").replace("\n","<br>").replace("\\","").replaceAll(" ","&nbsp;")+"\"");
    		} 
    	inputsb.append(",\"published\":true}}");
    	String input=inputsb.toString();
    	OutputStream os = updateConnection.getOutputStream();
    	os.write(input.getBytes());   	
    	updateConnection.getInputStream();	
    	updateConnection.disconnect();
    	url=url_backup;
    }
    
    
    
    /**
     * Used only by Update/Edit button of class GUI. Update the latest information of assignment
     * 
     * the same as getAssignments of class Course
     * 
     */
    public Assignment getSingleAssignments(String courseId, String assignmentId) {
    	String responses = "";
    	String urlbackup = url;
    	url = API+"/courses/"+courseId+"/assignments/"+assignmentId;
        responses = this.buildConnection();
        Assignment returnAssignment=new Assignment("Unavailable", "Unavailable");
        if(responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
                String strID = "";
                String strName = "";
                String openDate = "";
                String closeDate = "";
                String dueDate = "";
                String descrip = "";
                String subType = "";
                String des = "";
                String allowExtention = "";
                String points="";
                for (String s : rawResp) {
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.length() > 1 && s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);
                    if (!s.startsWith("\"")) {
                        if (!des.isEmpty())
                            des += s;
                    }
                    if (s.substring(0,1).equals("\"") && !s.startsWith("\"published\"")) {
                        if (!allowExtention.isEmpty())
                            allowExtention =allowExtention + "," + s.substring(1, s.length() - 1);
                    }
                    // get useful information from responses
                    if (s.startsWith("\"id\"") && !s.substring(5, 6).equals("\"")) {
                        strID=s.substring(5);
                    }
                    if (s.startsWith("\"name\"")) {
                        strName=s.substring(8, s.length() - 1);
                    }
                    if (s.startsWith("\"unlock_at\"")) {
                        if (s.startsWith("\"unlock_at\"")) {
                            if (s.substring(12).equals("null")) {
                                openDate=s.substring(12);
                            } else {
                            openDate=s.substring(13, s.length() - 11);
                            }
                        }
                    }
                    if (s.startsWith("\"due_at\"")) {
                        if (!des.isEmpty()) {
                            descrip=des;
                            des = "";
                        }
                        if (s.substring(9).equals("null")) {
                            dueDate=s.substring(9);
                        } else {
                            dueDate=s.substring(10, s.length() - 11);
                        }
                    }

                    if (s.startsWith("\"lock_at\"")) {
                        if (s.substring(10).equals("null")) {
                            closeDate=s.substring(10);
                        } else {
                            closeDate=s.substring(11, s.length() - 11);
                        }
                    }
                    if (s.startsWith("\"description\"")) {
                    	s = s.replace("<script src=\\\"https://instructure-uploads.s3.amazonaws.com/account_10300000000000001/attachments/2602729/canvas_ga.js\\\"></script>", "");
                        if (!des.isEmpty()) {
                            des = s.substring(14);
                        } else {                   	
                            des += s.substring(14);
                        }                   	
                    }
                    if (s.startsWith("\"allowed_extensions\"")) {
                        if (!allowExtention.isEmpty()) {
                            allowExtention = "";
                                allowExtention += s.substring(23, s.length() - 1);
                        } else {
                                allowExtention += s.substring(23, s.length() - 1);
                        }
                    }
                    if (s.startsWith("\"published\"")) {
                        if (!allowExtention.isEmpty()) {
                            subType=allowExtention.substring(0, allowExtention.length() - 1);
                            allowExtention = "";
                        } else {
                            subType="null";
                        }
                    }
                    if (s.startsWith("\"points_possible\"")) {
                    	points=s.substring(18);
                    }
                }
                returnAssignment=new Assignment(strName,strID);
                if (descrip.charAt(0)=='"'&&descrip.charAt(descrip.length()-1)=='"')                    	
                	returnAssignment.setAssignmentDescription(descrip.substring(1, descrip.length()-1));
                    else
                    	returnAssignment.setAssignmentDescription(descrip);
                returnAssignment.setCloseDate(closeDate);
                returnAssignment.setDueDate(dueDate);
                returnAssignment.setOpenDate(openDate);
                returnAssignment.setSubmissionTypes(subType);
                returnAssignment.setPoints(points);
                }
     
        } 
        url=urlbackup;
        return returnAssignment;
    }

    /**
     * gradeSubmission, used by SubmissionViewer
     * 
     * 
     */
    public void gradeSubmission(String courseId,String assignmentId,String studentid, String grade, String comment) throws IOException {
    	if (!grade.isEmpty()||!comment.isEmpty()) {
    		List<String> fields = new ArrayList<>();
    		fields.add("courses/"+courseId+"/assignments/"+assignmentId+"/submissions/"+studentid);
    		this.setURL(fields);
    		updateConnection=(HttpURLConnection)((new URL(url)).openConnection());
    		updateConnection.setDoOutput(true);
    		updateConnection.setRequestMethod("PUT");
    		updateConnection.setRequestProperty("Content-Type", TYPE);
    		updateConnection.setRequestProperty("Authorization", OAUTH2);
    		StringBuilder inputsb=new StringBuilder(1024);
    		inputsb.append('{');
    		if (!grade.isEmpty())    	
    			inputsb.append("\"submission\": {\"posted_grade\":"+grade);
    		if (!comment.isEmpty()) {
    			if (inputsb.charAt(inputsb.length()-1) != '{') {
    				inputsb.append("},");
    			}
    			inputsb.append("\"comment\": {\"text_comment\":\""+comment+"\"");
    		}
    		inputsb.append("}}");
    		String input = inputsb.toString();
    		OutputStream os = updateConnection.getOutputStream();
    		os.write(input.getBytes());   	
    		updateConnection.getInputStream();	
    		updateConnection.disconnect();
    	}
    }
    
}
