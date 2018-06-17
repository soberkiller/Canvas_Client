package com.killer.connecting;

public class connectCanvas {

    public static void main(String[] args) {

        double version = 0.1;
        String url = "https://sit.instructure.com/api/v1";

        String[] fields = {
                "courses",
                "20605",    // course id
                "assignments",
                "87045",    // assignment id
//                "submissions",
//                "10300000000030943"    // user id
//                "?submission[posted_grade]=50"  // the points grader wants to submit

        };

        String[] rawResp;
        ConnectionPool connection = new ConnectionPool(fields, version);
//        System.out.println(connection.getEndpoints());
        connection.setMethod("GET");
        String response =  connection.buildConnection();
        rawResp = response.split(",");
        if(rawResp != null)
            for (String s : rawResp)
                if (s.startsWith("\"lock_at\"")) {

                    System.out.println(s.substring(11, s.length() - 1));
                }

//        appDemo app = new appDemo();
//        app.setVisible(true);
//        downloadFile dF = new downloadFile();
//        dF.setAPI("https://sit.instructure.com/files/3581818/download?download_frd=1&verifier=Td2n7Tmg3KoOtmz4TUgQWREFDJpH87Bre0NclYz2");
//        dF.setDownload("hw1d.cpp");
    }

}

