package com.killer.connecting;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionPool {
    private double API_VERSSION = 0;
    private String API = "";

    private String METHOD = "GET";
    private String TYPE = "application/json";
    private String USER_AGENT = "Mozilla/5.0";
    private String OAUTH2 = "Bearer 1030~8XEzXvKON2BZHsr18b9uuw7lxoohyEKnMcftyNF49mrGZs51BhIqCPmzDQCo2Jbj";  // Token
    private String data = "";
    private URL connection;
    private HttpURLConnection finalConnection;

//    private HashMap<String, String> fields = new HashMap<String, String>();
    private String fields = "";
    public ConnectionPool(String[] endpoint, String url, double version) {
        this.API_VERSSION = version;
        this.API = url;
//        fields.put("version", String.valueOf(version));

        for(int i = 0; i < endpoint.length; i++) {
            fields += '/' + endpoint[i];
        }
        API += fields;
    }

    public String buildConnection() {
        StringBuilder  content = new StringBuilder();
        if(!this.getEndpoints().equalsIgnoreCase("") && !this.getEndpoints().isEmpty()) {
            try {

                connection = new URL(API);
                BufferedReader reader = new BufferedReader(new InputStreamReader(readWithAccess(connection, data)));
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line + "\n");
                }
                reader.close();
                return content.toString();
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

    public void setSubmissionType(String type) {
        this.TYPE = type;
    }

    public void setOauth2(String oauth2) {
        this.OAUTH2 = oauth2;
    }
}
