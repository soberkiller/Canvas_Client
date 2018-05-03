package canvasclient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author Fangming Zhao, Yifang Yuan
 * April. 2018
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
            	//System.out.println(url);
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

    //List Not Student role courses
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
 
    
    
}
