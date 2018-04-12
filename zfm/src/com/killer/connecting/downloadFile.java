package com.killer.connecting;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
// https://canvas.instructure.com/courses/1030~133/assignments/1030~89948/submissions?zip=1
public class downloadFile {
    private String METHOD = "GET";
    private String API = "https://canvas.instructure.com/api/v1";
    private URL connection;
    private HttpsURLConnection downloadConnection;
    private String FILEPATH = "c:\\assignments\\";
    private FileOutputStream fileOut;
    private InputStream inputStream;
    private boolean isDown = false;

    public downloadFile() {
        File dFile = new File(FILEPATH);
        if(!dFile.exists()) {
            dFile.mkdir();
        }
    }
    public String setDownload(String filename) {
        try {
            connection = new URL(API);
            inputStream = readData(connection);
            BufferedInputStream reader = new BufferedInputStream(inputStream);
            fileOut = new FileOutputStream(FILEPATH + filename);
            BufferedOutputStream writer = new BufferedOutputStream(fileOut);
            byte[] out = new byte[4096];
            int len = reader.read(out);

            while(len != -1) {
                writer.write(out, 0, len);
                len = reader.read(out);
            }
            writer.close();
            reader.close();
            //downloadConnection.disconnect();


        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public InputStream readData(URL url) {
        try {
            downloadConnection = (HttpsURLConnection) url.openConnection();

            downloadConnection.setRequestMethod(METHOD);
            downloadConnection.setDoOutput(true);
            downloadConnection.setDoOutput(true);
            downloadConnection.setInstanceFollowRedirects(true);
            downloadConnection.setUseCaches(true);
            downloadConnection.connect();
            System.out.println(downloadConnection.getHeaderField("Location"));

            return downloadConnection.getInputStream();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }




    public void setAPI(String url) {
        this.API = url;
    }
}
