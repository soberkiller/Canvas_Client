package com.killer.connecting;

public class connectCanvas {

    public static void main(String[] args) {

        double version = 0.1;
        String url = "https://canvas.instructure.com/api/v1";

        String[] fields = {
                "courses",
                "10300000000000133",    // course id
                "assignments",
                "10300000000089948",    // assignment id
                "submissions",
                "10300000000030943",    // user id
                "?submission[posted_grade]=50"  // the points grader wants to submit

        };

//        String[] rawResp;
//        ConnectionPool connection = new ConnectionPool(fields, url, version);
////        System.out.println(connection.getEndpoints());
//        connection.setMethod("PUT");
//        String response =  connection.buildConnection();
//        rawResp = response.split(",");
//        if(rawResp != null)
//            for (String s : rawResp)
//                System.out.println(s);

        appDemo app = new appDemo();
        app.setVisible(true);
    }

}

