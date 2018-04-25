package canvasclient;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @author Fangming Zhao, Yifang Yuan
 * April. 2018
 */

public class ConnectionPool {
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
    
    //Convert unicode to String
    public static String unicode2String(String unicode) {  
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

    //List Not Student role courses
    public ArrayList<Course> getNotStudentCourses() throws UnsupportedEncodingException {
        String responses = "";
        List<String> fields = new ArrayList<String>();
	 	fields.add("courses");
	 	String url_backup=url;
	 	setURL(fields);
	 	//get all courses of token owner
        responses = this.buildConnection();
        url=url_backup;
        ArrayList<Course> courseList =new ArrayList<>();
        //processed string
        if (responses != null) {
            String[] rawResp = responses.split(",");
            if (rawResp != null) {
                String t_id="";
                String t_name="";
                for (String s : rawResp) {
                	//trim unused char
                    if (s.startsWith("{"))
                        s = s.substring(1);
                    if (s.charAt(s.length() - 1) == '}')
                        s = s.substring(0, s.length() - 1);
                    if (s.startsWith("[{"))
                        s = s.substring(2);
                    if (s.charAt(s.length() - 2) == ']')
                        s = s.substring(0, s.length() - 3);
                    s=s.replace("\"enrollments\":[{", "");
                    //get course id
                    if (s.startsWith("\"id\"")) {
                    	t_id=s.substring(5);
                    }
                    //get course name
                    if (s.startsWith("\"name\"")) {
                    	t_name=s.substring(8, s.length() - 1);
                    }
                    //if type !=student, add the course to arraylist
                    if (s.startsWith("\"type\"")&&!s.substring(8, s.length() - 1).equals("student")) {
                    	courseList.add(new Course(t_name,t_id));
                    }
                }
            }
        } else {
            courseList.add(new Course("Unavailable","Unavailable"));
        }
        return courseList;
    }    
    
    
}
